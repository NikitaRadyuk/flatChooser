package com.example.reportservice.core;

public enum EssenceType {
    User("USER"),
    Report("REPORT");

    private final String typeID;

    EssenceType(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeID() {
        return typeID;
    }
}
