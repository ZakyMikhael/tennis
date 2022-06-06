package com.zaky.app.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Root {
    private List<Player> players = new ArrayList<>();

}
