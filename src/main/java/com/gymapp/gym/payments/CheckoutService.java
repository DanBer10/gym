package com.gymapp.gym.payments;

import com.gymapp.gym.profile.ProfileService;
import com.gymapp.gym.subscription.SubscriptionService;
import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private static final String EMAIL_HEADER = "Email";
    private static final String DISPLAY_NAME_NULL_ERROR = "Display name is null";
    private static final String USER_NULL_ERROR = "User is null";
    private static final String PAYMENT_INITIALIZED_MESSAGE = "Payment initialized";
    @Autowired
    private final SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    @Value("${spring.stripe.secret-key}")
    private String stripeSecretKey;



    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String getStripeKey() {
        return stripeSecretKey;
    }

    public CheckoutResponse createPaymentIntent(String stripeToken, HttpServletRequest request) throws StripeException {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);
        String displayName = profileService.getProfileName(user);
        // Create a customer using the provided Stripe token
        Customer customer = Customer.create(new CustomerCreateParams.Builder()
                .setEmail(email)
                .setName(displayName)
                .setSource(stripeToken)
                .build());

        long amount = 15000;
        String currency = "SEK";

        PaymentIntentCreateParams paymentIntentParams = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setReceiptEmail(email)
                .setCustomer(customer.getId())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

        return CheckoutResponse.builder().clientSecret(paymentIntent.getClientSecret()).build();
    }


}
