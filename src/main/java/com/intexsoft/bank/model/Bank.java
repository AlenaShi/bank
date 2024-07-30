package com.intexsoft.bank.model;

import java.sql.Timestamp;

public class Bank {
    private Long id;
    private String name;
    private int commissionIndividual;
    private int commissionLegal;

    private Timestamp creationDate;
    private Timestamp modificationDate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommissionIndividual() {
        return commissionIndividual;
    }

    public void setCommissionIndividual(int commissionIndividual) {
        this.commissionIndividual = commissionIndividual;
    }

    public int getCommissionLegal() {
        return commissionLegal;
    }

    public void setCommissionLegal(int commissionLegal) {
        this.commissionLegal = commissionLegal;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commissionIndividual=" + commissionIndividual +
                ", commissionLegal=" + commissionLegal +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
