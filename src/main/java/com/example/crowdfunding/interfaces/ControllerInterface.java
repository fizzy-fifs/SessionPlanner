package com.example.crowdfunding.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.List;

public interface ControllerInterface<T> {

    String create(T entity, Errors errors);

    ResponseEntity<List<T>> getAll();

    String update(String id, T newInfo);

    String delete( String id);
}
