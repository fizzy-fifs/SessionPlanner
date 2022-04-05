package com.example.crowdfunding.reward;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RewardService {
    @Autowired
    private RewardRepository rewardRepository;

    public Reward createReward(User user, Project project) {
        //Select a reward at random from the rewards list
        Reward reward = new Reward();
        Random random = new Random();
        int randomItem = random.nextInt(reward.getRewardsList().size());

        //Set the reward's name and associated business
        reward.setName(reward.getRewardsList().get(randomItem));
        reward.setProjectId(project.getId());

        //Save reward in db
        rewardRepository.insert(reward);

        //Add created reward to the user's earned rewards list
        user.addToRewardsList(reward);
        return reward;
    }
}
