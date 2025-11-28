package org.example.projetjeegroupeqspringboot.controller;

import jakarta.servlet.http.HttpSession;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String showProfile(HttpSession session, Model model) {
        Employee loggedUser = (Employee) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("loggedUser", loggedUser);
        return "Profile";
    }

    @GetMapping("/password")
    public String changePasswordForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        return "ChangePassword";
    }

    @PostMapping("/password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Employee loggedUser = (Employee) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Les nouveaux mots de passe ne correspondent pas");
            return "redirect:/profile/password";
        }

        // Vérifier l'ancien mot de passe
        if (!loggedUser.getPassword().equals(oldPassword)) {
            redirectAttributes.addFlashAttribute("error", "Ancien mot de passe incorrect");
            return "redirect:/profile/password";
        }

        // Mettre à jour le mot de passe
        loggedUser.setPassword(newPassword);
        employeeService.save(loggedUser);

        // Mettre à jour la session
        session.setAttribute("loggedUser", loggedUser);

        redirectAttributes.addFlashAttribute("success", "Mot de passe modifié avec succès");
        return "redirect:/profile";
    }
}
