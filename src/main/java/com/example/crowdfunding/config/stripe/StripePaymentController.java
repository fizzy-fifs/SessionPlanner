package com.example.crowdfunding.config.stripe;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1.0/payments")
public class StripePaymentController {

    @PostMapping(path = "/create-payment-intent")
    public String createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

        Gson gson = new Gson();

        Stripe.apiKey = "sk_test_51KeDy4FkYRYTO3iFNb2qqLkowbG3kchP8NnHiJxsiJxlqXgnA2417cqAOgSgjygFjjvjyxqrlT336iH9WUI2tfaj00ALSQuIdp";

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(createPayment.getAmount().longValue())
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
        return gson.toJson(paymentResponse);
    }
}
