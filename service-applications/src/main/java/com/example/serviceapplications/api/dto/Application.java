package com.example.serviceapplications.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Application {
    private double price;
    private String problemDescription;
    private String urgency;
    private long requesterId;
    private long selectedLawyerId;
    private String lawField;
    private long threadId;
}
