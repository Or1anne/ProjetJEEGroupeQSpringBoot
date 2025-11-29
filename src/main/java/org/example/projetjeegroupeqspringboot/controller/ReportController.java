package org.example.projetjeegroupeqspringboot.controller;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.enumeration.Grade;
import org.example.projetjeegroupeqspringboot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Contrôleur pour la page de rapports et statistiques.
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Affiche la page de statistiques avec les données agrégées.
     */
    @GetMapping
    public String showReportPage(Model model) {
        // Récupération des statistiques
        Map<Department, Long> employeesByDept = reportService.getEmployeeCountByDepartment();
        Map<Project, Long> employeesByProject = reportService.getEmployeeCountByProject();
        Map<Grade, Long> employeesByGrade = reportService.getEmployeeCountByGrade();

        // Ajout au modèle
        model.addAttribute("employeesByDept", employeesByDept);
        model.addAttribute("employeesByProject", employeesByProject);
        model.addAttribute("employeesByGrade", employeesByGrade);

        return "Report";
    }
}

