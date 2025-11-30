package org.example.projetjeegroupeqspringboot.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.Pay;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.example.projetjeegroupeqspringboot.repository.PayRepository;
import org.example.projetjeegroupeqspringboot.service.PayService;
import org.example.projetjeegroupeqspringboot.util.PayPdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.OutputStream;
import java.sql.Date;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayService payService;

    // Liste des fiches de paie (globale ou filtrée par employé)
    @GetMapping
    public String listPays(@RequestParam(required = false) Long employeeId, @RequestParam(required = false) String searchCriteria, @RequestParam(required = false) String value, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,Model model) {
        List<Pay> pays;
        Employee currentEmployee = null;

        if (employeeId != null) {
            // Mode filtré : afficher seulement les paies de cet employé
            currentEmployee = employeeRepository.findById(employeeId).orElse(null);
            if (currentEmployee != null) {
                pays = payRepository.findByEmployee(currentEmployee);
                model.addAttribute("currentEmployee", currentEmployee);
            } else {
                pays = payRepository.findAll();
            }
        } else {
            // Mode global : afficher toutes les paies
            pays = payRepository.findAll();
        }

        model.addAttribute("pays", pays);
        return "ListPay";
    }

    // Afficher le formulaire de création/modification
    @GetMapping("/add")
    public String showAddForm(@RequestParam(required = false) Long employeeId, Model model) {
        Pay pay = new Pay();

        // Si un employeeId est fourni, pré-remplir le formulaire avec les données de l'employé
        if (employeeId != null) {
            Employee employee = employeeRepository.findById(employeeId).orElse(null);
            if (employee != null) {
                model.addAttribute("prefillEmployee", employee);
            }
        }

        model.addAttribute("pay", pay);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("isEditMode", false);
        return "FormPay";
    }

    // Formulaire d'édition
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Pay pay = payRepository.findById(id).orElse(null);

        if (pay == null) {
            return "redirect:/pay";
        }

        model.addAttribute("pay", pay);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("isEditMode", true);
        return "FormPay";
    }

    // Enregistrer (créer ou modifier)
    @PostMapping
    public String savePay(@RequestParam(required = false) Long payId,
                          @RequestParam Long employeeId,
                          @RequestParam String month, // Format YYYY-MM
                          @RequestParam double baseSalary,
                          @RequestParam(defaultValue = "0") double bonus,
                          @RequestParam(defaultValue = "0") double deductions,
                          @RequestParam double salaryNet,
                          RedirectAttributes redirectAttributes) {

        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            redirectAttributes.addFlashAttribute("error", "Employé introuvable");
            return "redirect:/pay";
        }

        // Convertir le mois (YYYY-MM) en Date (dernier jour du mois)
        Date date;
        try {
            YearMonth yearMonth = YearMonth.parse(month);
            // Obtenir le dernier jour du mois
            int lastDay = yearMonth.lengthOfMonth();
            date = Date.valueOf(month + "-" + lastDay);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Format de date invalide");
            return "redirect:/pay";
        }

        Pay pay;
        if (payId != null) {
            pay = payRepository.findById(payId).orElse(new Pay());
        } else {
            pay = new Pay();
        }

        pay.setEmployee(employee);
        pay.setDate(date);
        pay.setBonus(bonus);
        pay.setDeductions(deductions);
        pay.setSalaryNet(salaryNet);

        payRepository.save(pay);

        redirectAttributes.addFlashAttribute("success",
            payId != null ? "Fiche de paie modifiée avec succès" : "Fiche de paie créée avec succès");

        // Rediriger vers la liste filtrée de l'employé si on vient d'une fiche employé
        return "redirect:/pay";
    }

    // Visualiser une fiche
    @GetMapping("/view/{id}")
    public String viewPay(@PathVariable Long id, Model model) {
        Pay pay = payRepository.findById(id).orElse(null);

        if (pay == null) {
            return "redirect:/pay";
        }

        model.addAttribute("pay", pay);
        return "ViewPay";
    }

    // Supprimer une fiche
    @GetMapping("/delete/{id}")
    public String deletePay(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Pay pay = payRepository.findById(id).orElse(null);

        if (pay != null) {
            Long employeeId = (long) pay.getEmployee().getId();
            payRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Fiche de paie supprimée avec succès");
            // Rediriger vers la liste de l'employé
            return "redirect:/pay";
        }

        redirectAttributes.addFlashAttribute("error", "Fiche de paie introuvable");
        return "redirect:/pay";
    }

    // Générer et télécharger le PDF de la fiche de paie
    @GetMapping("/pdf/{id}")
    public void generatePdf(@PathVariable Long id, HttpServletResponse response) {
        try {
            Pay pay = payRepository.findById(id).orElse(null);

            if (pay == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fiche de paie introuvable");
                return;
            }

            // Configuration de la réponse HTTP
            response.setContentType("application/pdf");

            // Nom du fichier : FichePaie_NomPrenom_MoisAnnee.pdf
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM_yyyy", java.util.Locale.FRENCH);
            String monthYear = pay.getDate().toLocalDate().format(formatter);
            String fileName = "FichePaie_" + pay.getEmployee().getLastName() + "_" +
                            pay.getEmployee().getFirstName() + "_" + monthYear + ".pdf";

            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // Générer le PDF
            OutputStream outputStream = response.getOutputStream();
            PayPdfGenerator.generatePayPdf(pay, outputStream);
            outputStream.flush();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }
}

