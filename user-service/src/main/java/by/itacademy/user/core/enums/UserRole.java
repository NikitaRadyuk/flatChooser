package by.itacademy.user.core.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
