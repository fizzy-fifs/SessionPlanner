package com.example.crowdfunding.adminsitrator;

import com.example.crowdfunding.interfaces.AbstractController;
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
public class AdministratorController extends AbstractController<Administrator> {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PostMapping(path = "/newadministrator")
    public ResponseEntity<Object> create(Administrator administrator, Errors errors) {
        return administratorService.create(administrator);
    }

    @GetMapping
    public ResponseEntity<List<Administrator>> getAll() {
        return administratorService.getAll();
    }

    public String update(String id, Administrator newInfo) {
        return null;
    }

    public String delete(String id) {
        return null;
    }

    public String setPercentageCut(Double percentageCut) {
        return null;
    }
}
