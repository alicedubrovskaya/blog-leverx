package com.leverxblog.services;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {
    String add(T t);

    List<T> getAll();

    T getById(UUID id) throws Exception;

    void delete(UUID id);
}
