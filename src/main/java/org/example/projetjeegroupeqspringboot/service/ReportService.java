package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.enumeration.Grade;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service pour les rapports/statistiques.
 * Fournit des méthodes de lecture agrégée (statistiques) sur les entités.
 */
@Service
public class ReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Nombre d'employés par département.
     *
     * @return une map Department -> nombre d'employés
     */
    public Map<Department, Long> getEmployeeCountByDepartment() {
        return employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getDepartment() != null)
                .collect(Collectors.groupingBy(
                        emp -> emp.getDepartment(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

    /**
     * Nombre d'employés par projet.
     *
     * @return une map Project -> nombre d'employés affectés
     */
    public Map<Project, Long> getEmployeeCountByProject() {
        return employeeRepository.findAll()
                .stream()
                .flatMap(emp -> emp.getProjects().stream())
                .map(empProject -> empProject.getProject())
                .collect(Collectors.groupingBy(
                        project -> project,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

    /**
     * Nombre d'employés par grade.
     *
     * @return une map Grade -> nombre d'employés
     */
    public Map<Grade, Long> getEmployeeCountByGrade() {
        return employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getGrade() != null)
                .collect(Collectors.groupingBy(
                        emp -> emp.getGrade(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }
}

