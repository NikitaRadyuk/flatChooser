package by.itacademy.user.core.dto.interfaces;

import by.itacademy.user.core.enums.UserRole;

import java.util.UUID;

public interface Userable extends Identifiable {
    UUID getUuid();
    String getMail();
    String getFio();
    UserRole getRole();
}
