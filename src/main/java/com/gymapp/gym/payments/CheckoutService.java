package com.gymapp.gym.payments;

import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    @Value("${spring.stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }
    public String getStripeKey() {
        return stripeSecretKey;
    }

    public ResponseEntity<String> charge(ChargeRequest chargeRequest)
            throws AuthenticationException, InvalidRequestException,
            ApiConnectionException, CardException, ApiException, com.stripe.exception.AuthenticationException {

        try {
            Customer customer = Customer.create(Map.of(
                    "name", chargeRequest.getCustomerDisplayName(),
                    "email", chargeRequest.getStripeEmail(),
                    "source", chargeRequest.getStripeToken()
            ));

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", chargeRequest.getAmount());
            chargeParams.put("currency", chargeRequest.getCurrency());
            chargeParams.put("description", chargeRequest.getDescription());
            chargeParams.put("customer", customer.getId());
            // Handle the charge response as needed
            Charge charge = Charge.create(chargeParams);

            return ResponseEntity.ok(charge.getId());
        } catch (StripeException e) {
            // Handle the StripeException
            throw new RuntimeException("Failed to create charge");

        }
    }
}