package com.gymapp.gym.auth;

import com.gymapp.gym.JWT.JwtService;
import com.gymapp.gym.user.Role;
import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public void register(RegisterRequest request) {
        var user = User.builder().displayName(request.getDisplayName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
        repository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getDisplayName(),
                            request.getPassword()
                    )
            );
            var user = repository.findByDisplayName(request.getDisplayName()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).displayName(user.getDisplayName()).build();
        } catch (AuthenticationException ex) {
            return AuthenticationResponse.builder().errorMessage("Authentication failed, please try again").build();
        }
    }

}
