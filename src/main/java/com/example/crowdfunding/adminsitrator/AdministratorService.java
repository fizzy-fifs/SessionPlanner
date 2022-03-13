package com.example.crowdfunding.adminsitrator;

import com.example.crowdfunding.interfaces.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService implements ServiceInterface<Administrator> {

    @Autowired
    private final AdministratorRepository administratorRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<Object> create(Administrator administrator) {
        String encodedPassword = this.passwordEncoder.encode(administrator.getPassword());
        administrator.setPassword(encodedPassword);

        administratorRepository.insert(administrator);
        return ResponseEntity.ok(administrator);
    }

    @Override
    public ResponseEntity<List<Administrator>> getAll() {
        List<Administrator> allAdmin = administratorRepository.findAll();
        return ResponseEntity.ok(allAdmin);
    }

    @Override
    public String update(String entityId, Administrator newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }
}
