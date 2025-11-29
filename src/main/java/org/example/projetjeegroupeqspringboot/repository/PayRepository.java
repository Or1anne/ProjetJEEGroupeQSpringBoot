package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Employee;
import org.example.projetjeegroupeqspringboot.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;

@Repository
public interface PayRepository extends JpaRepository<Pay,Long> {
    List<Pay> findByBonus (double minBonus, double maxBonus);
    List<Pay> findByDeductions (double minDeduction, double maxDeduction);
    List<Pay> findBySalaryNet(double minNet, double maxNet);
    List<Pay> findByDate (Date minDate, Date maxDate);
    List<Pay> findByEmployee (Employee employee);

    /**
     * Supprime toutes les fiches de paie d'un employé
     */
    @Modifying
    @Query("DELETE FROM Pay p WHERE p.employee.id = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
