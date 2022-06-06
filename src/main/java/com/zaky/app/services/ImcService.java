package com.zaky.app.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Service
public class ImcService {

    public static double getIMC(double weight, double height) {
        double poids = weight > 1000 ? weight / 1000 : weight;
        double taille = height > 100 ? height / 100 : height;
        return Math.round((poids) / Math.pow(taille, 2));
    }

    public static String getIMCInterpretation(double imc) {
        String status = "";
        if (imc < 18.5) {
            status = "Underweight";
        } else if (imc >= 18.5 && imc < 25) {
            status = "Normal";
        } else if (imc >= 25 && imc < 30) {
            status = "Overweight";
        } else if (imc == 30 || imc > 30) {
            status = "Obese";
        }
        return status;
    }
}
