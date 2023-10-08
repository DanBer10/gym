package com.gymapp.gym.profile;

import com.gymapp.gym.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne // Establishes Many-to-One relationship with User
    @JoinColumn(name = "user_id") // This column will store the foreign key to the User table
    private User user;

    private String displayName;
    private String height;
    private String weight;
    private String language;
    private String nationality;
    private String gender;
    private LocalDate dateOfBirth;
    private String fitnessGoals;
}
