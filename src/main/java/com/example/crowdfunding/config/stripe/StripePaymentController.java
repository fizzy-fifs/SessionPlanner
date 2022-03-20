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

@RestController
public class StripePaymentController {

    static int calculateOrderAmount(Object[] items) {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // people from directly manipulating the amount on the client
        return 1;
    }

    // Call this function with the ID of the Customer you want to charge
    static void chargeCustomer(String customerId) throws StripeException {
        // Lookup the payment methods available for the customer
        PaymentMethodListParams listParams = new PaymentMethodListParams.Builder().setCustomer(customerId)
                .setType(PaymentMethodListParams.Type.CARD).build();
        PaymentMethodCollection paymentMethods = PaymentMethod.list(listParams);
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder().setCurrency("eur")
                .setAmount( (long)(1099))
                .setPaymentMethod(paymentMethods.getData().get(0).getId())
                .setCustomer(customerId)
                .setConfirm(true)
                .setOffSession(true)
                .build();
        try {
            // Charge the customer and payment method immediately
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
        } catch (CardException err) {
            // Error code will be authentication_required if authentication is needed
            System.out.println("Error code is : " + err.getCode());
            String paymentIntentId = err.getStripeError().getPaymentIntent().getId();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            System.out.println(paymentIntent.getId());
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(path = "/api/v1.0/payments/create-payment-intent")
    public String createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

        Gson gson = new Gson();

        Stripe.apiKey = System.getenv("stripe.api.key.test");

        CustomerCreateParams customerParams = new CustomerCreateParams.Builder().build();
        Customer customer = Customer.create(customerParams);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCustomer(customer.getId())
                .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
                .setAmount((long) calculateOrderAmount(createPayment.getItems()))
                .setCurrency("eur")
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
