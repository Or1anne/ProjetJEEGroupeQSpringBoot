package org.example.projetjeegroupeqspringboot.entity;

import org.example.projetjeegroupeqspringboot.entity.embededId.EmployeeProjectId;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_project")
public class EmployeeProject {
    @EmbeddedId
    private EmployeeProjectId id;

    @ManyToOne
    @MapsId("id_employee")
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @ManyToOne
    @MapsId("id_project")
    @JoinColumn(name = "id_project")
    private Project project;

    public EmployeeProject() {}

    public EmployeeProjectId getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
