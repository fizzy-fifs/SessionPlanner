package com.example.crowdfunding.config.stripe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.Stripe;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.exception.SignatureVerificationException;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;

@RestController
public class StripeWebHookController {

    @PostMapping("/api/v1.0/payments/stripe/events")
    public String handleStripeEvents(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        Stripe.apiKey = System.getenv("stripe_api_key_live");
        String endpointSecret = System.getenv("stripe_webhook_secret");

        if (sigHeader == null){ return "";}

        Event event;
        // Only verify the event if you have an endpoint secret defined.
        // Otherwise use the basic event deserialized with GSON.
        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
            );
        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println("⚠️  Webhook error while validating signature.");
            ResponseEntity.status(400);
            return "";
        }
            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
                    // Then define and call a method to handle the successful payment intent.
                    // handlePaymentIntentSucceeded(paymentIntent);
                    break;
                case "payment_method.attached":
                    PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                    // Then define and call a method to handle the successful attachment of a PaymentMethod.
                    // handlePaymentMethodAttached(paymentMethod);
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
                    break;
            }
            ResponseEntity.status(200);
            return "";
    }
}
