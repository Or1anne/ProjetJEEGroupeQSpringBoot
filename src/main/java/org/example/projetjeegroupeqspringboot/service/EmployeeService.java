package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.repository.DepartmentRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeProjectRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRoleRepository;
import org.example.projetjeegroupeqspringboot.repository.PayRepository;
import org.example.projetjeegroupeqspringboot.repository.ProjectRepository;
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

    public List<Employee> findAll() {
        return employeeRepository.findAll(Sort.by("lastName"));
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
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

        // 1. Retirer l'employé comme chef de département (avant toute suppression)
        List<Department> departmentsManaged = departmentRepository.findByChefDepartmentId(id);
        for (Department dept : departmentsManaged) {
            dept.setChefDepartment(null);
            departmentRepository.save(dept);
        }

        // 2. Retirer l'employé comme chef de projet (avant toute suppression)
        List<Project> projectsManaged = projectRepository.findByChefProjId(id);
        for (Project project : projectsManaged) {
            project.setChefProj(null);
            projectRepository.save(project);
        }

        // 3. Supprimer tous les rôles de l'employé (table employee_role)
        employeeRoleRepository.deleteByEmployeeId(id);

        // 4. Supprimer toutes les fiches de paie de l'employé
        payRepository.deleteByEmployeeId(id);

        // 5. Supprimer toutes les affectations de l'employé aux projets (table employee_project)
        employeeProjectRepository.deleteByEmployeeId(id);

        // 6. Forcer le flush pour s'assurer que toutes les suppressions sont effectuées
        employeeRepository.flush();

        // 7. Supprimer l'employé
        employeeRepository.deleteById(id);
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
