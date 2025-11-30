package org.example.projetjeegroupeqspringboot.controller;

import jakarta.servlet.http.HttpSession;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.enumeration.ProjectStatus;
import org.example.projetjeegroupeqspringboot.service.AssignService;
import org.example.projetjeegroupeqspringboot.service.EmployeeService;
import org.example.projetjeegroupeqspringboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static ch.qos.logback.core.util.StringUtil.capitalizeFirstLetter;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AssignService assignService;

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

    @GetMapping("/add")
    public String showCreateProjectForm(Model model, HttpSession session) {
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        model.addAttribute("project", new Project());
        model.addAttribute("employees", employeeService.findAll());
        return "FormProject";
    }

    @PostMapping("/add")
    public String addProject(
            @RequestParam String nameProject,
            @RequestParam String status,
            @RequestParam(required = false) Long chefProjId,
            RedirectAttributes redirectAttributes) {

        Project project = new Project();
        project.setNameProject(capitalizeFirstLetter(nameProject));
        project.setStatus(ProjectStatus.valueOf(status));

        if (chefProjId != null) {
            Employee chef = employeeService.findById(chefProjId);
            project.setChefProj(chef);
        }

        projectService.save(project);
        redirectAttributes.addFlashAttribute("success", "Projet créé avec succès");
        return "redirect:/projects";
    }


    @GetMapping("/edit/{id}")
    public String showEditProjectForm(@PathVariable Long id, Model model, HttpSession session) {
        Project project = projectService.findById(id);

        if (project == null) {
            return "redirect:/projects";
        }

        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        model.addAttribute("project", project);
        model.addAttribute("employees", employeeService.findAll());
        return "TrackProject";
    }

    @PostMapping("/edit/{id}")
    public String editProject(
            @PathVariable Long id, @RequestParam String nameProject,
            @RequestParam String status, @RequestParam(required = false) Long chefProjId, RedirectAttributes redirectAttributes) {

        Project project = projectService.findById(id);

        if (project == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Projet non trouvé");
            return "redirect:/projects";
        }

        project.setNameProject(capitalizeFirstLetter(nameProject));
        project.setStatus(ProjectStatus.valueOf(status));

        if (chefProjId != null) {
            Employee chef = employeeService.findById(chefProjId);
            project.setChefProj(chef);
        } else {
            project.setChefProj(null);
        }

        projectService.save(project);
        redirectAttributes.addFlashAttribute("success", "Projet modifié avec succès");
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Projet supprimé avec succès");
        return "redirect:/projects";
    }

    // Page d'affectation
    @GetMapping("/assign")
    public String showAssignForm(@RequestParam(required = false) Long projectId, Model model, HttpSession session) {
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("projects", projectService.findAll());

        if (projectId != null) {
            model.addAttribute("selectedProjectId", projectId);
        }

        return "AssignEmployeeProject";
    }

    // Soumission du formulaire d'affectation
    @PostMapping("/assign")
    public String assignEmployeesToProject(
            @RequestParam Long employeeId,
            @RequestParam Long projectId,
            RedirectAttributes redirectAttributes) {

        assignService.assignEmployeeToProject(employeeId, projectId);
        redirectAttributes.addFlashAttribute("success", "Employé affecté au projet avec succès");
        return "redirect:/projects";
    }

}

