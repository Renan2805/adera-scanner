package com.adera.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UserEntity {
    @Getter @Setter
    private UUID id;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private String role;

    private UUID establishmentId;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", idEstablishment=" + establishmentId +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(UUID establishmentId) {
        this.establishmentId = establishmentId;
    }
}
