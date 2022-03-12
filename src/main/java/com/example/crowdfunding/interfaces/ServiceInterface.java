package com.example.crowdfunding.interfaces;


import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceInterface<T> {
    ResponseEntity<Object> create(T entity);

    ResponseEntity<List<T>> getAll();

    String update(String entityId, T newEntityInfo);

    String delete(String entityId);
}
