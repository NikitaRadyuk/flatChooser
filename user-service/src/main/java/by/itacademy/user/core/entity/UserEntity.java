package by.itacademy.user.core.entity;


import by.itacademy.user.core.dto.interfaces.Userable;
import jakarta.persistence.*;
import by.itacademy.user.core.enums.UserStatus;
import by.itacademy.user.core.enums.UserRole;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "flats_chooser", name="user")
@Accessors(chain = true)
public class UserEntity implements Userable {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name="mail")
    private String mail;
    @Column(name = "fio")
    private String fio;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole UserRole;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus UserStatus;
    @Column(name = "password")
    private String password;

    public UserEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getRole() {
        return UserRole;
    }

    public void setUserRole(UserRole userRole) {
        UserRole = userRole;
    }

    public UserStatus getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        UserStatus = userStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(mail, that.mail) && Objects.equals(fio, that.fio) && UserRole == that.UserRole && UserStatus == that.UserStatus && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, fio, UserRole, UserStatus, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", UserRole=" + UserRole +
                ", UserStatus=" + UserStatus +
                ", password='" + password + '\'' +
                '}';
    }
}
