package org.example.projetjeegroupeqspringboot.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Department")
    private int id;

    @Nonnull
    @Column(name = "department_name", nullable = false, unique = true)
    private String departmentName;

    // TODO transformer en @JoinColomn clée étrangere
    @ManyToOne
    @Nullable
    @JoinColumn(name = "id_chef_dep")
    private Employee chefDepartment;

    public Department() {}
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public void setChefDepartment(Employee chefDepartment) {
        this.chefDepartment = chefDepartment;
    }
    public Employee getChefDepartment() {
        return chefDepartment;
    }
}
