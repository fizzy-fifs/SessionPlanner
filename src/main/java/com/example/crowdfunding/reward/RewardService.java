package com.example.crowdfunding.reward;

import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.user.User;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RewardService {
    @Autowired
    private RewardRepository rewardRepository;

    public Pair addToUser(User user, Project project, double amount) {

        var amountisAboveMinThreshold = amount >= project.getReward().getMinimumThreshold() && amount < project.getReward().getHigherThreshold();
        var amountIsAboveHigherThreshold = amount >= project.getReward().getHigherThreshold();

        if (amountisAboveMinThreshold) {

            var reward = project.getReward().getMinimumThresholdReward();

            Map<String, String> rewardMap = new HashMap<String, String>() {{
                put("reward", reward);
                put("projectId", project.getId());
            }};
            user.addToRewardsList(rewardMap);
            return Pair.with(reward, project.getId());
        } else if (amountIsAboveHigherThreshold) {

            var reward = project.getReward().getHigherThresholdReward();

            Map<String, String> rewardMap = new HashMap<String, String>() {{
                put("reward", reward);
                put("projectId", project.getId());
            }};
            user.addToRewardsList(rewardMap);
            return Pair.with(reward, project.getId());
        }

        return null;
    }
}
