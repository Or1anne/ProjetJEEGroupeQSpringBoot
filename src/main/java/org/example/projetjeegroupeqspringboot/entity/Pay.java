package org.example.projetjeegroupeqspringboot.entity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "pay")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    @Column(name = "id_pay")
    private int id;

    @Nonnull
    private Date date;

    private double bonus = 0.0;
    private double deductions = 0.0;

    @Nonnull
    @Column(name = "net")
    private double salaryNet;

    @ManyToOne
    @Nonnull
    @JoinColumn(name = "id_employee")
    private Employee employee;

// verifier ou mettre le calcul

    // Constructeur par défaut, obligatoire pour Hibernate
    public Pay() {}

    // Constructeur pour ajouter des employees
    public Pay(Date date, double bonus, double deductions, double salaryNet) {

        this.date = date;
        this.bonus = bonus;
        this.deductions = deductions;
        this.salaryNet = salaryNet;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getBonus() {
        return bonus;
    }
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    public double getDeductions() {
        return deductions;
    }
    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }
    public double getSalaryNet() {
        return salaryNet;
    }
    public void setSalaryNet(double salaryNet) {
        this.salaryNet = salaryNet;
    }
    public Employee getEmployee() { return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}

}