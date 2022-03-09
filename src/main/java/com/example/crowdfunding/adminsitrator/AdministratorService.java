package com.example.crowdfunding.adminsitrator;

import com.example.crowdfunding.interfaces.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService implements ServiceInterface<Administrator> {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String create(Administrator administrator) {
        String encodedPassword = this.passwordEncoder.encode(administrator.getPassword());
        administrator.setPassword(encodedPassword);

        administratorRepository.insert(administrator);
        return "Administrator created succesfully";
    }

    @Override
    public List<Administrator> getAll() { return administratorRepository.findAll(); }

    @Override
    public String update(String entityId, Administrator newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }
}
