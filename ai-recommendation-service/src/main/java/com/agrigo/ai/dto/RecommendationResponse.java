package com.agrigo.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private String cropType;
    private FertilizerRecommendation fertilizer;
    private PesticideRecommendation pesticide;
    private List<String> additionalSupplies;
    private String aiInsight;
}
