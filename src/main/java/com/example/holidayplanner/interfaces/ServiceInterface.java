package com.example.holidayplanner.interfaces;


import java.util.List;

public interface ServiceInterface<T> {
    String create(T entity);

    List<T> getAll();

    String update(String entityId, T newEntityInfo);

    String delete(String entityId);
}
