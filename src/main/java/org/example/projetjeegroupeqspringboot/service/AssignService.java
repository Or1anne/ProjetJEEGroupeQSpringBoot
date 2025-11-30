package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.EmployeeProject;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.embededId.EmployeeProjectId;
import org.example.projetjeegroupeqspringboot.repository.EmployeeProjectRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.example.projetjeegroupeqspringboot.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepo;

    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        Employee employee = employeeRepo.findById(employeeId).orElse(null);
        Project project = projectRepo.findById(projectId).orElse(null);

        if (employee != null && project != null) {
            // Vérifier si l'affectation existe déjà (éviter les doublons)
            EmployeeProjectId checkId = new EmployeeProjectId(employee.getId(), project.getId());
            if (employeeProjectRepo.existsById(checkId)) {
                return;
            }

            // Créer l'association et définir l'ID manuellement
            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setEmployee(employee);
            employeeProject.setProject(project);

            // Définir explicitement l'ID composite avant la sauvegarde
            EmployeeProjectId newId = new EmployeeProjectId(employee.getId(), project.getId());
            employeeProject.setId(newId);

            // Sauvegarder
            employeeProjectRepo.save(employeeProject);
        }
    }

    public void unassignEmployeeFromProject(int employeeId, Long projectId) {
        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);
        if (employeeProjectRepo.existsById(id)) {
            employeeProjectRepo.deleteById(id);
        }
    }

    /**
     * Retourne la liste des employés affectés à un projet
     */
    public List<Employee> getAssignedEmployees(Long projectId) {
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project == null) return List.of();
        List<EmployeeProject> links = employeeProjectRepo.findByProject(project);
        return links.stream().map(EmployeeProject::getEmployee).collect(Collectors.toList());
    }
}
