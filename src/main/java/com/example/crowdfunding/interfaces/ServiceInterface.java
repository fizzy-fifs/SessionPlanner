package com.example.crowdfunding.interfaces;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ServiceInterface<T> {
    ResponseEntity<Object> create(T entity) throws JsonProcessingException;

    ResponseEntity<Object> getAll() throws JsonProcessingException;

    String update(String entityId, T newEntityInfo);

    String delete(String entityId);
}
