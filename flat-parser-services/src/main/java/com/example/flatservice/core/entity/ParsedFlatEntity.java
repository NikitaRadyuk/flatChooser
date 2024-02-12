package com.example.flatservice.core.entity;

import java.util.HashMap;

public class ParsedFlatEntity {
    public String name;
    public String cost;
    public HashMap<String, String> properties;

    public ParsedFlatEntity() {
        name = "";
        cost = "";
        properties = new HashMap<>();
    }

    public ParsedFlatEntity(String name, String cost, HashMap<String, String> properties) {
        this.name = name;
        this.cost = cost;
        this.properties = properties;
    }
}
