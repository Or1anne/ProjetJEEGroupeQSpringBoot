package org.example.projetjeegroupeqspringboot.entity.embededId;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EmployeeProjectId implements Serializable {
    private Integer id_employee;
    private Integer id_project;

    public EmployeeProjectId() {}

    public EmployeeProjectId(Integer idEmployee, Integer idProject) {
        this.id_employee = idEmployee;
        this.id_project = idProject;
    }

    public Integer getIdEmployee() {
        return id_employee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.id_employee = idEmployee;
    }

    public Integer getIdProject() {
        return id_project;
    }

    public void setIdProject(Integer idProject) {
        this.id_project = idProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeProjectId that = (EmployeeProjectId) o;
        return id_employee.equals(that.id_employee) && id_project.equals(that.id_project);
    }

    @Override
    public int hashCode() {
        return 31 * id_employee.hashCode() + id_project.hashCode();
    }
}
