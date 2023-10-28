package com.gymapp.gym.checkout;

import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/stripe-webhook")
public class WebhookController {

    @Value("${spring.stripe.webhook-key}")
    private String endpointSecret;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> handleWebHook(@RequestBody String payload,
                                                @RequestHeader("Stripe-Signature") String sigHeader)
    {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                    if (paymentIntent != null) {
                        String customerId = paymentIntent.getCustomer();
                        // TODO check this
                        User user = userService.getUserById(Integer.valueOf(customerId));


                    }
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }

            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
