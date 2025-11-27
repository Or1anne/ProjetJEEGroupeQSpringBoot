package org.example.projetjeegroupeqspringboot.entity.enumeration;

public enum Grade {

    EXECUTIVE_MANAGEMENT("Direction exécutive"),
    SENIOR_MANAGEMENT("Cadre supérieur"),
    MIDDLE_MANAGEMENT("Cadre"),
    SKILLED_EMPLOYEES("Employé qualifié"),
    EMPLOYEES("Employé"),
    INTERNS("Stagiaire");

    private final String label;

    Grade(String label) { this.label = label; }

    public String getLabel() { return label; }
}

