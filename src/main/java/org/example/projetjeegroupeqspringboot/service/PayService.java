package org.example.projetjeegroupeqspringboot.service;

import org.example.projetjeegroupeqspringboot.repository.EmployeeRepository;
import org.example.projetjeegroupeqspringboot.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {
    @Autowired
    private PayRepository payRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
}
