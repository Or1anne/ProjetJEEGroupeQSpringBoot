package org.example.projetjeegroupeqspringboot.entity.embededId;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeRoleId implements Serializable {
    private Integer id_employee;
    private Integer id_role;

    public EmployeeRoleId() {}

    public EmployeeRoleId(Integer id_employee, Integer id_role) {
        this.id_employee = id_employee;
        this.id_role = id_role;
    }

    public Integer getIdEmployee() {
        return id_employee;
    }

    public void setIdEmployee(Integer id_employee) {
        this.id_employee = id_employee;
    }

    public Integer getIdRole() {
        return id_role;
    }

    public void setIdRole(Integer id_role) {
        this.id_role = id_role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRoleId that = (EmployeeRoleId) o;
        return Objects.equals(id_employee, that.id_employee) &&
               Objects.equals(id_role, that.id_role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_employee, id_role);
    }
}
