package com.adera.repositories;

import com.adera.entities.UserEntity;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;

public class UserRepository implements IUnitOfWork<UserEntity> {
    @SneakyThrows
    @Override
    public void registerNew(UserEntity entity) {
        throw new ExecutionControl.NotImplementedException("");
    }

    @SneakyThrows
    @Override
    public void registerModified(UserEntity entity) {
        throw new ExecutionControl.NotImplementedException("");
    }

    @SneakyThrows
    @Override
    public void registerDeleted(UserEntity entity) {
        throw new ExecutionControl.NotImplementedException("");

    }

    @SneakyThrows
    @Override
    public void commit() {
        throw new ExecutionControl.NotImplementedException("");
    }
}
