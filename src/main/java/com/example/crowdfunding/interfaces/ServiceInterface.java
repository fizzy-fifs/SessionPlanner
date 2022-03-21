package com.example.crowdfunding.interfaces;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.maps.errors.ApiException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ServiceInterface<T> {
    ResponseEntity<Object> create(T entity) throws IOException, InterruptedException, ApiException;

    ResponseEntity<Object> getAll() throws JsonProcessingException;

    String update(String entityId, T newEntityInfo);

    String delete(String entityId);
}
