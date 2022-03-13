package com.example.crowdfunding.business;

import com.example.crowdfunding.interfaces.ControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/businesses")
public class BusinessController implements ControllerInterface<Business> {

    @Autowired
    private BusinessService businessService;

    @Override
    @PostMapping(path = "/newbusiness")
    public ResponseEntity<Object> create(@RequestBody @Valid Business business, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return businessService.create(business);
    }

    @Override
    public ResponseEntity<List<Business>> getAll() {
        return businessService.getAll();
    }

    @Override
    public String update(String id, Business newInfo) {
        return null;
    }

    @Override
    public String delete(String id) {
        return null;
    }
}
