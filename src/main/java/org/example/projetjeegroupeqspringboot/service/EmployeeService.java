package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll(Sort.by("lastName"));
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        }
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

        String baseUsername = cleanFirstName.substring(0, 1) + cleanLastName;

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
