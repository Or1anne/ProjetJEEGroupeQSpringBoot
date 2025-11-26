package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
