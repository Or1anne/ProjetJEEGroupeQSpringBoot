package org.example.projetjeegroupeqspringboot.controller;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.service.DepartmentService;
import org.example.projetjeegroupeqspringboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static ch.qos.logback.core.util.StringUtil.capitalizeFirstLetter;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    // Liste des départements
    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "ListDepartment";
    }

    // Formulaire d'ajout
    @GetMapping("/add")
    public String addDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("isEditMode", false);
        return "FormDepartment";
    }

    // Formulaire d'édition
    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@PathVariable Integer id, Model model) {
        Department department = departmentService.findById(Long.valueOf(id));

        if (department == null) {
            return "redirect:/departments";
        }

        model.addAttribute("department", department);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("isEditMode", true);

        return "FormDepartment";
    }

    // Vue détaillée
    @GetMapping("/{id}")
    public String viewDepartment(@PathVariable Integer id, Model model) {
        Department department = departmentService.findById(Long.valueOf(id));

        if (department == null) {
            return "redirect:/departments";
        }

        model.addAttribute("department", department);
        return "ViewDepartment";
    }

    // Sauvegarde (ajout ou modification)
    @PostMapping("/save")
    public String saveDepartment(
            @RequestParam(required = false) Integer id,
            @RequestParam("departmentName") String departmentName,
            @RequestParam(value = "chefDepartmentId", required = false) Long chefDepartmentId,
            RedirectAttributes redirectAttributes) {

        Department department;

        // Si id existe, c'est une modification
        if (id != null) {
            department = departmentService.findById(Long.valueOf(id));
            if (department == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Département non trouvé");
                return "redirect:/departments";
            }
        } else {
            // Sinon, c'est une création
            department = new Department();
        }

        // Mise à jour des champs
        department.setDepartmentName(capitalizeFirstLetter(departmentName));

        // Gestion du chef de département
        if (chefDepartmentId != null) {
            Employee chef = employeeService.findById(chefDepartmentId);
            department.setChefDepartment(chef);

            // Affecter automatiquement le chef au département s'il n'est pas déjà membre
            if (chef.getDepartment() == null || chef.getDepartment().getId() != department.getId()) {
                chef.setDepartment(department);
                employeeService.save(chef);
            }
        } else {
            department.setChefDepartment(null);
        }

        departmentService.save(department);
        redirectAttributes.addFlashAttribute("success",
                id != null ? "Département modifié avec succès" : "Département ajouté avec succès");

        return "redirect:/departments";
    }

    // Suppression
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Department department = departmentService.findById(Long.valueOf(id));
            if (department != null) {
                // Vérifier s'il y a des employés dans ce département
                if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Impossible de supprimer ce département car il contient des employés");
                } else {
                    departmentService.deleteById(Long.valueOf(id));
                    redirectAttributes.addFlashAttribute("success", "Département supprimé avec succès");
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression du département");
        }
        return "redirect:/departments";
    }
}
