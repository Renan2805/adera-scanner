package com.adera.entities;

import java.util.UUID;

public class ComponentEntity {
    private UUID id;
    private String model;
    private String description;
    private UUID idMachine;
    private Integer type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(UUID idMachine) {
        this.idMachine = idMachine;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
