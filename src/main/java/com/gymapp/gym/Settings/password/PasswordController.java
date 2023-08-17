package com.gymapp.gym.Settings.password;

import com.gymapp.gym.Settings.SettingsResponse;
import com.gymapp.gym.Settings.SettingsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-settings/change-password")
public class PasswordController {

    private final SettingsService service;

    @PostMapping()
    public ResponseEntity<SettingsResponse> updatePassword(HttpServletRequest request, @RequestBody String newPassword) {

        if (newPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(service.updatePassword(request, newPassword));
    }




}


