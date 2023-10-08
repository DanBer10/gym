package com.gymapp.gym.subscription;

import com.gymapp.gym.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Subscription {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING) // Specify EnumType.STRING to store enum values as strings
    @Column(name = "subscription_type") // Name of the database column
    private SubscriptionType subscriptionType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean verified_email;
    private boolean recurring_payment;
    private boolean one_time_payment;
}
