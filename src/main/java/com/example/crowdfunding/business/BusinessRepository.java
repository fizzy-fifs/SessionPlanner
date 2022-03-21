package com.example.crowdfunding.business;

import com.example.crowdfunding.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface BusinessRepository extends MongoRepository<Business, String> {
    Business findById(ObjectId businessId);
    ArrayList<Business> findByOwner(User user);
}
