package com.example.crowdfunding.config.stripe;

import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.project.ProjectRepository;
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

    @GetMapping(path = "/api/v1.0/payments/{projectId}&{amount}")
    public String home(Model model, @PathVariable("projectId")String projectId, @PathVariable("amount")BigDecimal amount) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("projectId", projectId);
        model.addAttribute("amount", amount.longValue());
        return "CheckoutForm";
    }

    @GetMapping(path = "/api/v1.0/payments/success/{projectId}/{amount}")
    public void successfulPayment(@RequestBody String projectId, @RequestBody long amount) {

        // Add donated amount to project and save in DB
        Project project = projectRepository.findById(new ObjectId(projectId));
        project.addDonationToAmountRaised(BigDecimal.valueOf(amount));
        projectRepository.save(project);
    }



}
