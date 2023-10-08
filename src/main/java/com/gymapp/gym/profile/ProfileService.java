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
        Profile profile = repository.findByDisplayName(displayName);

        if (profile == null) {
            return new ProfileResponse("Profile not found by name: " + displayName);
        }

        return new ProfileResponse(profile);
    }

    public String getProfileName(@NonNull User user) {
        User userByEmail = userRepository.getUserByEmail(user.getEmail());

        if (userByEmail == null) {
            return "No profile created for this user.";
        }

        return userByEmail.getEmail();
    }

    public ProfileResponse profileStatus(@NonNull HttpServletRequest request) {
        final String email = request.getHeader("Email");
        Profile profile = repository.findByUserEmail(email);

        if (profile == null) {
            return new ProfileResponse("Profile not created");
        }

        return new ProfileResponse(toProfileDto(profile));
    }

    public ProfileResponse createProfile(@NonNull HttpServletRequest request, ProfileDto profileDto) {
        final String email = request.getHeader("Email");

        if (email == null) {
            return new ProfileResponse("Email not found in the request headers.");
        }

        boolean profileExists = repository.existsByUserEmail(email);
        if (profileExists) {
            // Handle the case when the profile already exists
            return new ProfileResponse("Profile already created for " + email);
        }

        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isEmpty()) {
            return new ProfileResponse("User not found for " + email);
        }

        User user = optionalUser.get();
        Profile profile = Profile.builder().user(user).displayName(profileDto.getDisplayName()).height(profileDto.getHeight()).weight(profileDto.getWeight()).language(profileDto.getLanguage()).nationality(profileDto.getNationality())
                .gender(profileDto.getGender()).dateOfBirth(profileDto.getDateOfBirth()).fitnessGoals(profileDto.getFitnessGoals()).build();

        repository.save(profile);

        return new ProfileResponse(profileDto);
    }

    public ProfileResponse updateProfileLanguageForUser(@NonNull User user, @NonNull String language) {
                Profile profile = repository.getByUserId(user.getId());
                if (profile != null) {
                    profile.setLanguage(language);
                    repository.save(profile);
                }

         return new ProfileResponse(profile);
    }

    public Profile getByUserId(Integer id) {
       return repository.getByUserId(id);
    }

    public ProfileDto toProfileDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setDisplayName(profile.getDisplayName());
        profileDto.setHeight(profile.getHeight());
        profileDto.setWeight(profile.getWeight());
        profileDto.setGender(profile.getGender());
        profileDto.setLanguage(profile.getLanguage());
        profileDto.setNationality(profile.getNationality());
        profileDto.setDateOfBirth(profile.getDateOfBirth());
        profileDto.setFitnessGoals(profile.getFitnessGoals());


        return profileDto;
    }

}
