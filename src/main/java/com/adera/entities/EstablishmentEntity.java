package com.adera.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
