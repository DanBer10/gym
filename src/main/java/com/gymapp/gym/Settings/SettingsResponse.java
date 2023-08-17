package com.gymapp.gym.Settings;

import com.gymapp.gym.profile.Profile;
import com.gymapp.gym.profile.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingsResponse {
    private String errorMessage;
    private String successMessage;

    // Constructor for error response
    public SettingsResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
