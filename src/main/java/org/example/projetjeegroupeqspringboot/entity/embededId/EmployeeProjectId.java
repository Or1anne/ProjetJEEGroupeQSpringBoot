package org.example.projetjeegroupeqspringboot.entity.embededId;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EmployeeProjectId implements Serializable {
    private Integer id_employee;
    private Integer id_project;

    public EmployeeProjectId() {}

    public Integer getIdEmployee() {
        return id_employee;
    }

    public Integer getIdProject() {
        return id_project;
    }
}
