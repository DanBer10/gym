package com.gymapp.gym.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;


    public User getUserByDisplayName (@NonNull String displayName) {
        return repo.getUserByDisplayName(displayName);
    }


    public ResponseEntity<User> updateUserEmail (@NonNull User user, @NonNull String newEmail) {

        user.setEmail(newEmail);
        repo.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> updateUserPassword(@NonNull User user, @NonNull String newPassword) {
        String newEncryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newEncryptedPassword);
        repo.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> updateUserLanguage(@NonNull User user, @NonNull String language) {
        user.setLanguage(language);
        repo.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> getUserById(@NonNull Integer userId) {
        repo.getReferenceById(userId);

        return ResponseEntity.ok().build();
    }


}
