package by.itacademy.user.core.enums;

public enum EntityForAudit {
    USER("user"),
    ADMIN("admin");

    private final String entity;

    EntityForAudit(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }
}
