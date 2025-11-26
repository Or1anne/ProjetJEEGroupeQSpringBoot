package org.example.projetjeegroupeqspringboot.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.example.projetjeegroupeqspringboot.entity.enumeration.ProjectStatus;

import java.util.List;


@Entity
@Table(name = "project")
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    @Column(name = "id_project")
    private int id;

    @Column(name = "name")
    @Nonnull
    private String name_project;

    @Enumerated(EnumType.STRING)
    @Nonnull
    private ProjectStatus status = ProjectStatus.WORKED_ON;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "id_chef_pro")
    private Employee ChefProj;

    @OneToMany(mappedBy = "project")
    private List<EmployeeProject> employees;

    public  Project() {}

    public Project(String name_project, ProjectStatus status, Employee chefProj) {
        this.name_project = name_project;
        this.status = status;
        this.ChefProj = chefProj;

    }

    public int getId()
    {
        return id;
    }

    public String getName_project() {
        return name_project;
    }

    public void setName_project(String name) {
        this.name_project = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Employee getChefProj() {
        return ChefProj;
    }

    public void setChefProj(Employee ChefProj) {
        this.ChefProj = ChefProj;
    }

    public List<EmployeeProject> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeProject> employees) {
        this.employees = employees;
    }
}
