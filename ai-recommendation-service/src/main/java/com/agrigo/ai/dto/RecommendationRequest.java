package com.agrigo.ai.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String cropType;
    private String soilType;
    private String climateZone;
    private String farmSize;
    private String currentSeason;
}
