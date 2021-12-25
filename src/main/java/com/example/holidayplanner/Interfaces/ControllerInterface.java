package com.example.holidayplanner.Interfaces;

import com.example.holidayplanner.user.User;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ControllerInterface<T> {

    String create(T entity, Errors errors);

    List<T> getAll();

    String update(String id, T newInfo);

    String delete( String id);
}
