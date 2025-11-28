package org.example.projetjeegroupeqspringboot.entity;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.example.projetjeegroupeqspringboot.entity.enumeration.Grade;
import org.example.projetjeegroupeqspringboot.entity.enumeration.GradeConverter;

import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private int id;

    @Nonnull
    @Column(name = "last_name")
    private String lastName;

    @Nonnull
    @Column(name = "first_name")
    private String firstName;

    @Nonnull
    @Column(name = "grade")
    @Convert(converter = GradeConverter.class)
    private Grade grade;

    private String post;

    @Nonnull
    private Double salary = 0.0;

    @Nonnull
    @Column(nullable = false, unique = true)
    private String username;

    @Nonnull
    private String password;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "id_department")
    private Department department;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<EmployeeProject> projects;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<EmployeeRole> roles;

    // Constructeur par défaut, obligatoire pour Hibernate
    public Employee() {}

    // Constructeur pour ajouter des employees
    public  Employee(String lastName, String firstName, Grade grade, String post) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.grade = grade;
        this.post = post;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUsername() {return this.username;}

    public void setUsername(String username) {this.username = username;}

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<EmployeeProject> getProjects() {
        return projects;
    }

    public void setProjects(List<EmployeeProject> projects) {
        this.projects = projects;
    }

    public List<EmployeeRole> getRoles() {
        return roles;
    }

    public void setRoles(List<EmployeeRole> roles) {
        this.roles = roles;
    }
}