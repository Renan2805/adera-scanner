package com.adera.entities;

import com.adera.enums.ComponentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ComponentEntity {
    private UUID id;
    private String model;
    private String description;
    private Double capacity;
    private UUID idMachine;
    private ComponentTypeEnum type;
}
