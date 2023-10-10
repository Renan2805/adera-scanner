package com.adera.repositories;

import com.adera.database.ConnectionMySQL;

import java.sql.Connection;

public class UnitOfWork {
    public ComponentRepository componentRepository;
    public EstablishmentRepository establishmentRepository;
    public MachineRepository machineRepository;
    public UserRepository userRepository;

    public UnitOfWork() {
        Connection conn = ConnectionMySQL.getConnection();
        this.componentRepository = new ComponentRepository(conn);
        this.establishmentRepository = new EstablishmentRepository(conn);
        this.machineRepository = new MachineRepository(conn);
        this.userRepository = new UserRepository(conn);
    }
}
