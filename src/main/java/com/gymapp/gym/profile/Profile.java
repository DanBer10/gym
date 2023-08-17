package com.gymapp.gym.profile;

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
@Table(name = "_profile")
public class Profile {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne // Establishes Many-to-One relationship with User
    @JoinColumn(name = "user_id") // This column will store the foreign key to the User table
    private User user;

    private String displayName;
    private String weight;
    private String height;
}
