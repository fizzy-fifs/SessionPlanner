package com.example.crowdfunding.business;

import com.example.crowdfunding.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessRepository extends MongoRepository<Business, String> {
    Business findById(ObjectId businessId);
}
