package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.Pay;
import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.example.projetjeegroupeqspringboot.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PayService {
    @Autowired
    private PayRepository payRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> employeeAssociateIdLastName(String name){
        return employeeRepository.findByLastNameContaining(name);
    }

    private List<Employee> employeeAssociateIdFirstName(String name){
        return employeeRepository.findByFirstNameContaining(name);
    }

    public List<Pay> search(String searchCriteria, String value) {

        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }

        return switch (searchCriteria) {
            case "lastName" -> {
                List<Pay> resultLast = new ArrayList<>();
                for (Employee employee : employeeAssociateIdLastName(value)) {
                    resultLast.addAll(payRepository.findByEmployee(employee));
                }
                yield resultLast;
            }
            case "firstName" -> {
                List<Pay> result = new ArrayList<>();
                for (Employee employee : employeeAssociateIdFirstName(value)) {
                    result.addAll(payRepository.findByEmployee(employee));
                }
                yield result;
            }
            default -> throw new IllegalArgumentException("Champ de recherche invalide : " + searchCriteria);
        };
    }
}
