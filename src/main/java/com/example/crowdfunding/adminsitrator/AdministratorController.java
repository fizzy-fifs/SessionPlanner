package com.example.crowdfunding.adminsitrator;

import com.example.crowdfunding.interfaces.ControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/administrators")
public class AdministratorController implements ControllerInterface<Administrator> {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Override
    @PostMapping(path = "/newadministrator")
    public String create(Administrator administrator, Errors errors) {
        return administratorService.create(administrator);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Administrator>> getAll() {
        return administratorService.getAll();
    }

    @Override
    public String update(String id, Administrator newInfo) {
        return null;
    }

    @Override
    public String delete(String id) {
        return null;
    }

    public String setPercentageCut(Double percentageCut) {
        return null;
    }
}
