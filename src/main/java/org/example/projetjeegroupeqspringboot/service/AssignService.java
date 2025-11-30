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
}

