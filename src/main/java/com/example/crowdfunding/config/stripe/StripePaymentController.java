package com.example.crowdfunding.config.stripe;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.exception.*;

import java.math.BigDecimal;

@RestController
public class StripePaymentController {

    @PostMapping(path = "/api/v1.0/payments/create-payment-intent")
    public String createPaymentIntent(@RequestBody long amount) throws StripeException {

        Gson gson = new Gson();

        Stripe.apiKey = System.getenv("stripe.api.key.live");

        CustomerCreateParams customerParams = new CustomerCreateParams.Builder().build();
        Customer customer = Customer.create(customerParams);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCustomer(customer.getId())
                .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
                .setAmount(amount * 100L)
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
