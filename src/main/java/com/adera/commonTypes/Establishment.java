package com.adera.commonTypes;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class Establishment {
    private UUID id;

    private String name;

    private String cnpj;

    @Override
    public String toString() {
        return "Establishment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
