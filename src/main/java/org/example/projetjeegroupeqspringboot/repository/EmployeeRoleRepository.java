package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.EmployeeRole;
import org.example.projetjeegroupeqspringboot.entity.embededId.EmployeeRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, EmployeeRoleId> {

    /**
     * Trouve tous les rôles d'un employé
     */
    List<EmployeeRole> findByEmployee(Employee employee);

    /**
     * Supprime tous les rôles d'un employé
     */
    @Modifying
    @Query("DELETE FROM EmployeeRole er WHERE er.employee.id = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}

