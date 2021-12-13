package com.example.serviceapplications.api.dto;

public record Thread(long id, long requesterId, long selectedLawyerId, String history) {

}
