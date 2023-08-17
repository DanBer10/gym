package com.gymapp.gym.payments;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CheckoutController {

    private final CheckoutService service;

    @GetMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession() {
        return ResponseEntity.ok(service.getStripeKey());
    }

    @PostMapping("/charge")
    public ResponseEntity<?> charge(@RequestBody ChargeRequest chargeRequest) {
        try {
            return ResponseEntity.ok(service.charge(chargeRequest));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
