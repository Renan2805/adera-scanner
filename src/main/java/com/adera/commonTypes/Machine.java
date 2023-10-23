package com.adera.commonTypes;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class Machine {
    private UUID id;

    private String macAddress;

    private String os;

    private String vendor;

    private Integer architecture;

    private ArrayList<Component> components;

    private UUID establishmentId;

    public Machine() {
        this.components = new ArrayList<Component>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return Objects.equals(id, machine.id) && Objects.equals(macAddress, machine.macAddress) && Objects.equals(os, machine.os) && Objects.equals(vendor, machine.vendor) && Objects.equals(architecture, machine.architecture) && Objects.equals(components, machine.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, macAddress, os, vendor, architecture, components);
    }

    @Override
    public String toString() {
        return String.format("""
                Machine {
                    id: %s,
                    macAddress: %s,
                    os: %s,
                    vendor: %s,
                    architecture: %d,
                    components: %s,
                    establishmentId: %s
                }""", id == null ? "null" : id.toString(), macAddress, os, vendor, architecture, components, establishmentId.toString());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Integer architecture) {
        this.architecture = architecture;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public UUID getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(UUID establishmentId) {
        this.establishmentId = establishmentId;
    }
}
