package org.example.projetjeegroupeqspringboot.config;

import jakarta.servlet.http.HttpSession;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpSession session) {
        Employee loggedUser = (Employee) session.getAttribute("loggedUser");
        model.addAttribute("loggedUser", loggedUser);
    }
}

