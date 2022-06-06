package com.zaky.app.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stats {
    private String pays;
    private double imcMoyen;
    private double tailleMediane;
}
