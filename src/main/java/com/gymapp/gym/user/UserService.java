package com.gymapp.gym.user;

import com.gymapp.gym.JWT.JwtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;


    public User getUserByEmail(@NonNull String email) {
        return repo.getUserByEmail(email);
    }


    public String updateUserEmail (@NonNull User user, @NonNull String newEmail) {
        user.setEmail(newEmail);
        repo.save(user);

        return jwtService.generateToken(user);
    }


    public void updateUserPassword(@NonNull User user, @NonNull String newPassword) {
        String newEncryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newEncryptedPassword);
        repo.save(user);
    }

    public User getUserById(@NonNull Integer userId) {
        return repo.getReferenceById(userId);
    }


}
