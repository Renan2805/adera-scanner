package com.adera.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class MetricEntity {

    public UUID id;

    public Date date;

    public String measurement;

    public UUID fkComponent;
}
