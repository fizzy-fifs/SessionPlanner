package com.example.crowdfunding.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.List;

public abstract class AbstractController<T> {

    ResponseEntity<Object> create(T entity, Errors errors) {
        return null;
    }

    ResponseEntity<Object> create(T entity, Object object, Errors errors) {
        return null;
    }

    ResponseEntity<List<T>> getAll() {
        return null;
    }

    String update(String id, T newInfo) {
        return null;
    }

    String delete(String id) {
        return null;
    }
}
