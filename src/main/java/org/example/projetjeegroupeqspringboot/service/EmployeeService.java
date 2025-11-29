package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.EmployeeRole;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.Role;
import org.example.projetjeegroupeqspringboot.entity.embededId.EmployeeRoleId;
import org.example.projetjeegroupeqspringboot.repository.DepartmentRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeProjectRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRoleRepository;
import org.example.projetjeegroupeqspringboot.repository.PayRepository;
import org.example.projetjeegroupeqspringboot.repository.ProjectRepository;
import org.example.projetjeegroupeqspringboot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll(Sort.by("lastName"));
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    /**
     * Sauvegarde un employé
     * Si c'est un nouvel employé (id == 0), lui attribue automatiquement le rôle "EMPLOYE"
     */
    @Transactional
    public void save(Employee employee) {
        boolean isNewEmployee = (employee.getId() == 0);

        // Sauvegarder l'employé d'abord
        Employee savedEmployee = employeeRepository.save(employee);

        // Si c'est un nouvel employé, lui attribuer le rôle EMPLOYE par défaut
        if (isNewEmployee) {
            assignDefaultRole(savedEmployee);
        }
    }

    /**
     * Attribue le rôle "EMPLOYE" par défaut à un employé
     */
    private void assignDefaultRole(Employee employee) {
        Role employeeRole = roleRepository.findByRoleName("EMPLOYE");

        if (employeeRole != null) {
            EmployeeRole employeeRoleAssociation = new EmployeeRole();

            // Créer l'ID composite
            EmployeeRoleId employeeRoleId = new EmployeeRoleId(employee.getId(), employeeRole.getIdRole());

            // Définir l'ID composite et les entités
            employeeRoleAssociation.setId(employeeRoleId);
            employeeRoleAssociation.setEmployee(employee);
            employeeRoleAssociation.setRole(employeeRole);

            employeeRoleRepository.save(employeeRoleAssociation);
        }
    }

    /**
     * Supprime un employé et toutes ses références dans la base de données
     * - Retire l'employé comme chef de département (met à null)
     * - Retire l'employé comme chef de projet (met à null)
     * - Supprime tous les rôles de l'employé (table employee_role)
     * - Supprime toutes les fiches de paie de l'employé
     * - Retire l'employé de tous les projets (table employee_project)
     * - Supprime l'employé
     */
    @Transactional
    public void deleteById(Long id) {
        if (!employeeRepository.existsById(id)) {
            return;
        }

        // 1. Supprimer toutes les affectations de l'employé aux projets (table employee_project) EN PREMIER
        employeeProjectRepository.deleteByEmployeeId(id);
        employeeProjectRepository.flush();

        // 2. Supprimer tous les rôles de l'employé (table employee_role)
        employeeRoleRepository.deleteByEmployeeId(id);
        employeeRoleRepository.flush();

        // 3. Supprimer toutes les fiches de paie de l'employé
        payRepository.deleteByEmployeeId(id);
        payRepository.flush();

        // 4. Retirer l'employé comme chef de département (met à null)
        List<Department> departmentsManaged = departmentRepository.findByChefDepartmentId(id);
        for (Department dept : departmentsManaged) {
            dept.setChefDepartment(null);
            departmentRepository.save(dept);
        }
        departmentRepository.flush();

        // 5. Retirer l'employé comme chef de projet (met à null)
        List<Project> projectsManaged = projectRepository.findByChefProjId(id);
        for (Project project : projectsManaged) {
            project.setChefProj(null);
            projectRepository.save(project);
        }
        projectRepository.flush();

        // 6. Supprimer l'employé
        employeeRepository.deleteById(id);
        employeeRepository.flush();
    }

    /**
     * Génère un username unique au format : première lettre du prénom + nom en minuscules et sans accents
     * Si le username existe déjà, ajoute un chiffre à la fin
     *
     * @param firstName Le prénom de l'employé
     * @param lastName Le nom de l'employé
     * @return Un username unique
     */
    public String generateUniqueUsername(String firstName, String lastName) {
        String cleanFirstName = removeAccents(firstName.trim().toLowerCase());
        String cleanLastName = removeAccents(lastName.trim().toLowerCase());

        String baseUsername = cleanFirstName.charAt(0) + cleanLastName;

        if (!employeeRepository.existsByUsername(baseUsername)) {
            return baseUsername;
        }

        List<Employee> similarEmployees = employeeRepository.findByUsernameStartingWith(baseUsername);

        int counter = 2;
        String newUsername;

        while (true) {
            newUsername = baseUsername + counter;

            boolean exists = false;
            for (Employee emp : similarEmployees) {
                if (emp.getUsername().equals(newUsername)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                return newUsername;
            }

            counter++;
        }
    }

    /**
     * Retourne le mot de passe par défaut
     * @return Le mot de passe par défaut "departement"
     */
    public String getDefaultPassword() {
        return "departement";
    }

    /**
     * Enlève les accents d'une chaîne de caractères
     */
    private String removeAccents(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
