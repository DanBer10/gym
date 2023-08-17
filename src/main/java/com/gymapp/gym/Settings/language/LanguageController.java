package com.gymapp.gym.Settings.language;

import com.gymapp.gym.Settings.SettingsResponse;
import com.gymapp.gym.Settings.SettingsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-settings/change-language")
public class LanguageController {

    private final SettingsService service;


    @PostMapping
    public ResponseEntity<SettingsResponse> changeLanguage(HttpServletRequest request, @RequestBody String language) {

        if (language.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(service.updateLanguage(request, language));
    }
}
