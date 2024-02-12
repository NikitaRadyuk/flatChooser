package by.itacademy.user.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "flats_chooser", name = "token")
public class VerificationEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "token")
    private String token;
    @Column(name = "mail")
    private String mail;
    @Column(name = "send_code")
    private boolean sendCode;
    public VerificationEntity() {
    }

    public VerificationEntity(String token, String mail) {
        this.uuid = UUID.randomUUID();
        this.token = token;
        this.mail = mail;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isSendCode() {
        return sendCode;
    }

    public void setSendCode(boolean sendCode) {
        this.sendCode = sendCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationEntity that = (VerificationEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(token, that.token) && Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, token, mail);
    }

    @Override
    public String toString() {
        return "VerificationEntity{" +
                "uuid=" + uuid +
                ", token='" + token + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }


}
