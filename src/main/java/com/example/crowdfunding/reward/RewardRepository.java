package com.example.crowdfunding.reward;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RewardRepository extends MongoRepository<Reward, String> {
}
