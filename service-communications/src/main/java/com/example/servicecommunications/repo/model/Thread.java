package com.example.servicecommunications.repo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "threads")
public final class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String history;
    private long requesterId;
    private long selectedLawyerId;

    public Thread() {
    }

    public Thread( long requesterId, long selectedLawyerId, String history) {
        this.history  = history;
        this.requesterId = requesterId;
        this.selectedLawyerId = selectedLawyerId;
    }

    public void addMessage(String message) {
        this.history = this.history + message + "\n";
    }

    public long getId() {
        return id;
    }

    public String getHistory() {return this.history;}

    public void setHistory(String history) {
        this.history = history;
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
}
