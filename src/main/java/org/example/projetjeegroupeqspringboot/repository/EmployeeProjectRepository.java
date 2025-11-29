package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
}

