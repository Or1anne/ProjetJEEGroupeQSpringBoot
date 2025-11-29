package org.example.projetjeegroupeqspringboot.entity.enumeration;

public enum ProjectStatus {
    WORKED_ON("En cours"),
    FINISHED("Terminé"),
    CANCELLED("Annulé");

    private final String label;

    ProjectStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
