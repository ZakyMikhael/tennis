package com.zaky.app.models;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Stats {
    private Map<String, Double> ratioPays;
    private double imcMoyen;
    private double tailleMediane;
}
