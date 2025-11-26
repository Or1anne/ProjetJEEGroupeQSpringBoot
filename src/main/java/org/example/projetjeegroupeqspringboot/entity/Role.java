package org.example.projetjeegroupeqspringboot.entity;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;

    @Nonnull
    @Column(name = "role_name", unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<EmployeeRole> employeeRoles;

    public Role() {}
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(List<EmployeeRole> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }
}
