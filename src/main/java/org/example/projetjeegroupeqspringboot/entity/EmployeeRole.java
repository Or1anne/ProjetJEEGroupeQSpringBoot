package org.example.projetjeegroupeqspringboot.entity;

import org.example.projetjeegroupeqspringboot.entity.embededId.EmployeeRoleId;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_role")
public class EmployeeRole {
    @EmbeddedId
    private EmployeeRoleId id;

    @ManyToOne
    @MapsId("id_employee")
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @ManyToOne
    @MapsId("id_role")
    @JoinColumn(name = "id_role")
    private Role role;

    public EmployeeRole() {}

    public EmployeeRoleId getId() {
        return id;
    }

    public void setId(EmployeeRoleId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Role getRole() {
        return role;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
