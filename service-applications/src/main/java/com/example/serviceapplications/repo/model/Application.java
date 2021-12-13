package com.example.serviceapplications.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "applications")
public final class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double price;
    private String problemDescription;
    private String urgency;
    private long requesterId;
    private long selectedLawyerId;
    private String lawField;
    private long threadId;

    public Application() {
    }

    public Application(double price, String problemDescription, String urgency, long requesterId, long selectedLawyerId, String lawField, long threadId) {
        this.price = price;
        this.problemDescription = problemDescription;
        this.urgency = urgency;
        this.requesterId = requesterId;
        this.selectedLawyerId = selectedLawyerId;
        this.lawField = lawField;
        this.threadId = threadId;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }

    public long getSelectedLawyerId() {
        return selectedLawyerId;
    }

    public void setSelectedLawyerId(long selectedLawyerId) {
        this.selectedLawyerId = selectedLawyerId;
    }

    public String getLawField() {
        return lawField;
    }

    public void setLawField(String lawField) {
        this.lawField = lawField;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }
}
