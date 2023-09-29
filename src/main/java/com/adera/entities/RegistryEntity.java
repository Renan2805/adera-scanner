package com.adera.entities;

import java.sql.Date;
import java.util.UUID;

public class RegistryEntity {
    public Date date;
    public Integer cpuUsage;
    public Integer ramUsage;

    /**
     * The foreign key of a system in the database
     */
    public UUID systemId;
}
