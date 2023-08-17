package com.gymapp.gym.Settings;


import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserResponse;
import com.gymapp.gym.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SettingsService {

    private final UserService userService;

    public SettingsResponse updateEmail(HttpServletRequest request, @NonNull String newEmail) {
        final String displayName = request.getHeader("displayName");
        User user = userService.getUserByDisplayName(displayName);

        if (user == null) {
            return new SettingsResponse("No user found");
        }

        userService.updateUserEmail(user, newEmail);

        return new SettingsResponse.SettingsResponseBuilder().successMessage("Updated email successfully").build();
    }


    public SettingsResponse updatePassword(HttpServletRequest request, @NonNull String newPassword) {
        final String displayName = request.getHeader("displayName");
        User user = userService.getUserByDisplayName(displayName);

        if (user == null) {
            return new SettingsResponse("No user found");
        }

        userService.updateUserPassword(user, newPassword);

        return new SettingsResponse.SettingsResponseBuilder().successMessage("Updated user password successfully").build();
    }


    public SettingsResponse updateLanguage(HttpServletRequest request, @NonNull String language) {
        final String displayName = request.getHeader("displayName");
        User user = userService.getUserByDisplayName(displayName);

        if (user == null) {
            return new SettingsResponse("No user found");
        }

        userService.updateUserLanguage(user, language);

        return new SettingsResponse.SettingsResponseBuilder().successMessage("Updated user language successfully").build();
    }
}


