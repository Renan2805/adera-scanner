package com.adera.repositories;

import com.adera.database.*;

public interface IUnitOfWork<T> {

    String INSERT = "INSERT";

    String MODIFY = "MODIFY";

    String DELETE = "DELETE";

    void registerNew(T entity);

    void registerModified(T entity);

    void registerDeleted(T entity);

    void commit();
}
