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
    private String nameProject;

    @Enumerated(EnumType.STRING)
    @Nonnull
    private ProjectStatus status = ProjectStatus.WORKED_ON;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "id_chef_pro")
    private Employee chefProj;

    @OneToMany(mappedBy = "project")
    private List<EmployeeProject> employees;

    public  Project() {}

    public Project(String nameProject, ProjectStatus status, Employee chefProj) {
        this.nameProject = nameProject;
        this.status = status;
        this.chefProj = chefProj;
    }

    public int getId() {
        return id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Employee getChefProj() {
        return chefProj;
    }

    public void setChefProj(Employee chefProj) {
        this.chefProj = chefProj;
    }

    public List<EmployeeProject> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeProject> employees) {
        this.employees = employees;
    }
}
