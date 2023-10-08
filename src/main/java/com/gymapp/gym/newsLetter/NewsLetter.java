package com.gymapp.gym.newsLetter;

import com.gymapp.gym.user.User;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name = "news_letter")
public class NewsLetter {
    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    @ManyToOne // Establishes Many-to-One relationship with User
    @JoinColumn(name = "user_id") // This column will store the foreign key to the User table
    private User user;
}
