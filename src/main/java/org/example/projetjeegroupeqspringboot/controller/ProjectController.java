package org.example.projetjeegroupeqspringboot.controller;

import jakarta.servlet.http.HttpSession;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.projetjeegroupeqspringboot.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String listProjects(Model model, HttpSession session) {
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        model.addAttribute("projects", projectService.findAll());
        return "ListProject";
    }

    @GetMapping("/{id}")
    public String projectDetails(@PathVariable Long id, Model model, HttpSession session) {
        Project project = projectService.findById(id);

        if (project == null) {
            return "redirect:/projects";
        }

        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        model.addAttribute("project", project);
        return "ViewProject";
    }

}

