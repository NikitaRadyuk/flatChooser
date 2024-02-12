package by.itacademy.audit.core.enums;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER"),
    SYSTEM("SYSTEM");
    private final String roleId;

    Role(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }
}
