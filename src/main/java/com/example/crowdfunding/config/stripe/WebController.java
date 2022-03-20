package com.example.crowdfunding.config.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Value("${stripe.public.key}")
    private String stripePublicKey = System.getenv("stripe_live_publishable_key");

    @GetMapping(path = "/api/v1.0/payments")
    public String home(Model model) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "CheckoutForm";
    }



}
