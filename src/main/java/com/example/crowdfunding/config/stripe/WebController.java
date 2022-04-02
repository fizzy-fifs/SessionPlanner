package com.example.crowdfunding.config.stripe;

import com.example.crowdfunding.donor.Donor;
import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.project.ProjectRepository;
import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.user.User;
import com.example.crowdfunding.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@Controller
public class WebController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/api/v1.0/payments/{projectId}&{amount}&{userId}")
    public String home(Model model, @PathVariable("projectId")String projectId,
                       @PathVariable("amount")BigDecimal amount,  @PathVariable("userid")String userId) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("projectId", projectId);
        model.addAttribute("amount", amount.longValue());
        model.addAttribute(userId, userId);
        return "CheckoutForm";
    }

    @GetMapping(path = "/api/v1.0/payments/success")
    public void successfulPayment(@RequestBody String projectId, @RequestBody long amount, @RequestBody String userId) {

        //Find donor in user repo and generate user's reward
        User user = userRepository.findById(new ObjectId(userId));
        Reward reward = user.addReward();
        userRepository.save(user);

        // Add donated amount and donor to project
        Project project = projectRepository.findById(new ObjectId(projectId));
        project.addDonationToAmountRaised(BigDecimal.valueOf(amount));
        project.addToDonorsList(
                new Donor(user, BigDecimal.valueOf(amount), reward )
        );
        projectRepository.save(project);
    }



}
