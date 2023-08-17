package com.gymapp.gym.payments;

import com.gymapp.gym.user.User;
import com.stripe.model.Customer;
import lombok.Data;

@Data
public class ChargeRequest {
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
    private String customerDisplayName;

    public enum Currency {
        EUR, USD, SEK;
    }
}
