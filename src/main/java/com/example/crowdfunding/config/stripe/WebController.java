package com.example.crowdfunding.config.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @GetMapping(path = "/api/v1.0/payments/{amount}")
    public String home(Model model, @PathVariable("amount")Long amount) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("amount", amount);
        return "CheckoutForm";
    }



}
