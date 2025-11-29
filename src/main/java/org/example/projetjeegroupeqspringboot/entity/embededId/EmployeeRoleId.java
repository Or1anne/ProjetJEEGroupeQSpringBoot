package org.example.projetjeegroupeqspringboot.entity.embededId;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EmployeeRoleId implements Serializable {
    private Integer id_employee;
    private Integer id_role;

    public EmployeeRoleId() {}

    public Integer getIdEmployee() {
        return id_employee;
    }

    public Integer getIdRole() {
        return id_role;
    }
}
