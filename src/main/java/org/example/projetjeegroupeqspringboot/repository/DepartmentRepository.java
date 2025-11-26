package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
