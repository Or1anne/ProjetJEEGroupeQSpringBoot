package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Authentifie un utilisateur avec son username et password
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe
     * @return L'employé si authentification réussie, null sinon
     */
    public Employee login(String username, String password) {
        Employee employee = employeeRepository.findFirstByUsername(username);

        if (employee != null && employee.getPassword() != null
            && employee.getPassword().equals(password)) {
            return employee;
        }

        return null;
    }
}

