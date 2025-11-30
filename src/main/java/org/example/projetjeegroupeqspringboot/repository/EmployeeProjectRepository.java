package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.EmployeeProject;
import org.example.projetjeegroupeqspringboot.entity.Project;
import org.example.projetjeegroupeqspringboot.entity.embededId.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {

    /**
     * Trouve toutes les affectations d'un employé
     */
    List<EmployeeProject> findByEmployee(Employee employee);

    /**
     * Trouve toutes les affectations pour un projet donné
     */
    List<EmployeeProject> findByProject(Project project);

    /**
     * Supprime toutes les affectations d'un employé aux projets
     */
    @Modifying
    @Query("DELETE FROM EmployeeProject ep WHERE ep.employee.id = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
