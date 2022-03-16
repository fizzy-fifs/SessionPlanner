package com.example.crowdfunding.business;

import com.example.crowdfunding.interfaces.ServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService implements ServiceInterface<Business> {

    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public ResponseEntity<Object> create(Business business) throws JsonProcessingException {

        Business savedBusiness = businessRepository.insert(business);
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String businessJson = mapper.writeValueAsString(savedBusiness);
        return new ResponseEntity<>(businessJson, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Business>> getAll() {
        return new ResponseEntity<>(businessRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public String update(String entityId, Business newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }
}
