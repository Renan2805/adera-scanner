package com.adera.mappers;

import com.adera.commonTypes.Establishment;
import com.adera.entities.EstablishmentEntity;

public abstract class EstablishmentMapper {
    public static Establishment toEstablishment(EstablishmentEntity establishment) {
        return new Establishment(
                establishment.getId(),
                establishment.getFantasyName(),
                establishment.getCnpj()
        );
    }

    public EstablishmentEntity toEstablishmentEntity(Establishment establishment) {
        return new EstablishmentEntity(
                establishment.getId(),
                establishment.getName(),
                establishment.getCnpj()
        );
    }
}
