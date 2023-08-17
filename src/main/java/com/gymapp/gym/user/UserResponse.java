package com.gymapp.gym.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class UserResponse {
    private String errorMessage;
    private String displayName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    UserResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
