package com.gymapp.gym.Settings;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class SettingsController {

    @GetMapping("/account-settings")
    public ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok().build();
    }
}
