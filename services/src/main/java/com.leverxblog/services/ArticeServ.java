package com.leverxblog.services;

import java.util.List;
import java.util.UUID;

public interface ArticeServ<T> {
    String add(T t, UUID userId);

    List<T> getAll();

    T getById(UUID id) throws Exception;

    void delete(UUID id);

    List <T> getByUserId(UUID userId);

    List <T> getByPublicStatus();
}
