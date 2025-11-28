package org.example.projetjeegroupeqspringboot.controller;

import jakarta.servlet.http.HttpSession;
import org.example.projetjeegroupeqspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session) {
        // Si déjà connecté, rediriger vers l'accueil
        if (session.getAttribute("loggedUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("loggedUser", null);
        return "Login";
    }

    @PostMapping("/login")
    public String loginSubmit(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        var user = userService.login(username, password);

        if (user == null) {
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");
            model.addAttribute("loggedUser", null);
            return "Login";
        }

        session.setAttribute("loggedUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
