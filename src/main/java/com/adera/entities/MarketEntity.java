package com.adera.entities;

import com.sun.jna.platform.win32.Guid;

import java.util.UUID;

public class MarketEntity {
    public UUID id;

    public String fantasyName;

    public String cnpj;

    @Override
    public String toString() {
        return "MarketEntity{" +
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
