package com.example.crowdfunding.business;

import com.example.crowdfunding.interfaces.ServiceInterface;
import com.example.crowdfunding.user.User;
import com.example.crowdfunding.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessService implements ServiceInterface<Business> {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<Object> create(Business business) throws JsonProcessingException {

        Business savedBusiness = businessRepository.insert(business);

        //Retrieve user from db.
        String userId = savedBusiness.getOwner().getId();
        User user = userRepository.findById(new ObjectId(userId));

        //Return new business in json format.
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String businessJson = mapper.writeValueAsString(savedBusiness);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("business", businessJson);
        responseData.put("name", savedBusiness.getName());

        return ResponseEntity.ok(responseData);
    }

    @Override
    public ResponseEntity<Object> getAll() throws JsonProcessingException {
        List<Business> allBusinesses = businessRepository.findAll();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String allBusinessesJson = mapper.writeValueAsString(allBusinesses);
        return new ResponseEntity(allBusinessesJson, HttpStatus.OK);
    }

    @Override
    public String update(String entityId, Business newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }

    public ResponseEntity getBusinessByUserId(String userId) throws JsonProcessingException {
        User user = userRepository.findById(new ObjectId(userId));
        if (user == null){ return ResponseEntity.badRequest().body("Invalid userId"); }

        ArrayList<Business> businesses = businessRepository.findByOwner(user);

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String businessesJson = mapper.writeValueAsString(businesses);

        return ResponseEntity.ok(businessesJson);
    }
}
