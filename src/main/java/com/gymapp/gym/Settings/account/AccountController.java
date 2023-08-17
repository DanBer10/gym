package com.gymapp.gym.Settings.account;

import com.gymapp.gym.Settings.SettingsResponse;
import com.gymapp.gym.Settings.SettingsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-settings/change-email")
public class AccountController {

    private final SettingsService service;

    @PostMapping()
    public ResponseEntity<SettingsResponse> updateEmailAddress(HttpServletRequest request, @RequestBody String newEmail) {

        if (newEmail == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(service.updateEmail(request, newEmail));
    }


}
