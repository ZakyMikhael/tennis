package com.zaky.app.models;

import lombok.Data;

import java.util.List;

@Data
public class PlayerData {
    private int rank;
    private int points;
    private double weight;
    private double height;
    private int age;
    private List<Integer> last;
}

