package com.example.crowdfunding.config.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;


    @GetMapping(path = "/api/v1.0/payments/{projectId}&{amount}&{userId}")
    public String home(Model model, @PathVariable("projectId") String projectId,
                       @PathVariable("amount") double amount, @PathVariable("userId") String userId) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("projectId", projectId);
        model.addAttribute("amount", ((long) amount));
        model.addAttribute(userId, userId);
        return "CheckoutForm";
    }

}
