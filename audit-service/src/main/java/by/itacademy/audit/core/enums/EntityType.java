package by.itacademy.audit.core.enums;

public enum EntityType {
    USER("USER"),
    REPORT("REPORT");

    private final String typeID;

    EntityType(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeID() {
        return typeID;
    }
}
