package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.EmployeeProject;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.enumeration.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByUsername(String username);
    Employee findFirstByUsername(String username);
    List<Employee> findByGrade(Grade grade);
    List<Employee> findByPost(String post);
    List<Employee> findByProjects(List<EmployeeProject> projects);
    List<Employee> findByDepartment(Department department);
    boolean existsByUsername(String username);
    List<Employee> findByUsernameStartingWith(String usernamePrefix);
}
