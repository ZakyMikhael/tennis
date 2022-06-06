package com.zaky.app.models;

import lombok.Data;

@Data
public class Player {
    private String id;
    private String firstname;
    private String lastname;
    private String shortname;
    private String sex;
    private Country country;
    private String picture;
    private PlayerData data;

}


