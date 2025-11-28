package org.example.projetjeegroupeqspringboot.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String showProfile(HttpSession session, Model model) {
        Object loggedUser = session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("loggedUser", loggedUser);
        return "Profile";
    }

    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "editProfile";
    }

    @GetMapping("/password")
    public String changePassword(HttpSession session, Model model) {
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "changePassword";
    }
}
