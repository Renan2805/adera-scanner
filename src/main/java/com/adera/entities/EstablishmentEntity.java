package com.adera.entities;

import java.util.UUID;

public class EstablishmentEntity {
    public UUID id;

    public String fantasyName;

    public String cnpj;

    @Override
    public String toString() {
        return "EstablishmentEntity{" +
                "id=" + id +
                ", fantasyName='" + fantasyName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
