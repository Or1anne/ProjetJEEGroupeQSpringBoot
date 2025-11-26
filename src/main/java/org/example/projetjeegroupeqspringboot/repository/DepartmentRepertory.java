package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepertory extends JpaRepository<Department,Long> {
}
