package com.gymapp.gym.profile;

import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;
    private final UserRepository userRepository;

    public ProfileResponse getProfileByDisplayName(@NonNull String displayName) {
        Profile profile = repository.findByUserDisplayName(displayName);

        if (profile == null) {
            return new ProfileResponse("Profile not found by name: " + displayName);
        }

        return new ProfileResponse(profile);
    }

    public ProfileResponse profileStatus(@NonNull HttpServletRequest request) {
        final String displayName = request.getHeader("Displayname");
        Profile profile = repository.findByUserDisplayName(displayName);
        if (profile.getDisplayName() == null) {
            return new ProfileResponse("Profile not created");
        }

        ProfileDto profileDto = ProfileDto.builder().height(profile.getHeight()).weight(profile.getWeight()).build();

        return new ProfileResponse(profileDto);
    }

    public ProfileResponse createProfile(@NonNull HttpServletRequest request, ProfileDto profileDto) {
        final String displayName = request.getHeader("Displayname");

        if (displayName == null) {
            return new ProfileResponse("Display name not found in the request headers.");
        }

        boolean profileExists = repository.existsByUserDisplayName(displayName);
        if (profileExists) {
            // Handle the case when the profile already exists
            return new ProfileResponse("Profile already created for " + displayName);
        }

        Optional<User> optionalUser = userRepository.findByDisplayName(displayName);
        if(optionalUser.isEmpty()) {
            // Handle the case when the user is not found
            return new ProfileResponse("User not found for " + displayName);
        }
        // Unwrap the Optional to get the actual User object
        User user = optionalUser.get();
        Profile profile = Profile.builder().user(user).height(profileDto.getHeight()).weight(profileDto.getWeight()).displayName(displayName).build();

        repository.save(profile);

        return ProfileResponse.builder().build();
    }

}
