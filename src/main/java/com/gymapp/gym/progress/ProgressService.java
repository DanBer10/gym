package com.gymapp.gym.progress;

import com.gymapp.gym.exerciseType.ExerciseType;
import com.gymapp.gym.exerciseType.ExerciseTypeService;
import com.gymapp.gym.notifications.NotificationsCategory;
import com.gymapp.gym.notifications.NotificationsService;
import com.gymapp.gym.profile.Profile;
import com.gymapp.gym.profile.ProfileService;
import com.gymapp.gym.subscription.Subscription;
import com.gymapp.gym.subscription.SubscriptionService;
import com.gymapp.gym.subscription.SubscriptionType;
import com.gymapp.gym.user.User;
import com.gymapp.gym.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProgressService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProgressRepository repository;
    @Autowired
    private ExerciseTypeService exerciseTypeService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private NotificationsService notificationsService;

    public ResponseEntity<List<ProgressDto>> getByProfile(HttpServletRequest request) throws IllegalAccessException {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new IllegalAccessException("User doesn't exist");
        }

        Profile profile = profileService.getByUserId(user.getId());

        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }

        List<Progress> progressList = repository.findByProfileId(profile.getId());
        List<ProgressDto> progressDtoList = new ArrayList<>();

        for (Progress progress: progressList) {
            ProgressDto progressDto = new ProgressDto();
            progressDto.setExerciseType(progress.getExerciseType());
            progressDto.setSets(progress.getSets());
            progressDto.setReps(progress.getReps());
            progressDto.setWeight(progress.getWeight());
            progressDto.setDistance(progress.getDistance());
            progressDto.setTime(progress.getTime());
            progressDto.setId(progress.getId());
            progressDtoList.add(progressDto);
        }

        return ResponseEntity.ok(progressDtoList);
    }

    public ResponseEntity<ProgressDto> addProgressToProfile(HttpServletRequest request, @RequestBody ProgressFormData formData) throws IllegalAccessException {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new IllegalAccessException("User doesn't exist");
        }

        Profile profile = profileService.getByUserId(user.getId());

        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        if (formData == null) {
            return ResponseEntity.badRequest().build();
        }

        Subscription userSubscription = subscriptionService.getByUserId(user.getId());
        List<Progress> allByProfile =  repository.getAllByProfileId(profile.getId());

        for (Progress pr : allByProfile) {
            if (pr.getExerciseType().getName().equals(formData.getExerciseType())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }

        if (userSubscription.getSubscriptionType().equals(SubscriptionType.BASIC) && allByProfile.size() >= 9) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Progress progress = mapToProgress(formData);
        progress.setProfile(profile);
        repository.save(progress);

        ProgressDto progressDto = new ProgressDto();
        progressDto.setId(progress.getId());
        progressDto.setDistance(progress.getDistance());
        progressDto.setWeight(progress.getWeight());
        progressDto.setSets(progress.getSets());
        progressDto.setReps(progress.getReps());
        progressDto.setExerciseType(progress.getExerciseType());

        notificationsService.addNotificationsToFriendlySendOutQueue(user, progress.getExerciseType().getName(), NotificationsCategory.PROGRESSION, Date.from(Instant.now()));

        return ResponseEntity.ok().body(progressDto);
    }

    public ResponseEntity<String> deleteProgressById(HttpServletRequest request, @PathVariable UUID exerciseId) throws IllegalAccessException {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new IllegalAccessException("User doesn't exist");
        }

        Progress progress = repository.getById(exerciseId);

        if (progress == null) {
            return ResponseEntity.badRequest().build();
        }

        repository.delete(progress);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> editProgressById(HttpServletRequest request, UUID exerciseId, ProgressDto data) throws IllegalAccessException {
        final String email = request.getHeader("Email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new IllegalAccessException("User doesn't exist");
        }

        Progress progress = repository.getById(exerciseId);

        if (progress == null) {
            return ResponseEntity.badRequest().build();
        }

        if (data == null) {
            return ResponseEntity.badRequest().build();
        }

        progress.setSets(data.getSets());
        progress.setReps(data.getReps());
        progress.setWeight(data.getWeight());
        progress.setDistance(data.getDistance());
        progress.setTime(data.getTime());

        repository.save(progress);

        return ResponseEntity.ok().build();
    }

    public List<Progress> getAllProgressByProfileId(int id) {
      return repository.getAllByProfileId(id);
    }

    private Progress mapToProgress(ProgressFormData formData) {
        Progress progress = new Progress();
        ExerciseType exerciseType = exerciseTypeService.getOrCreateExerciseType(formData.getExerciseType());
        progress.setExerciseType(exerciseType);
        progress.setSets((int) formData.getSets());
        progress.setReps((int) formData.getReps());
        progress.setWeight(formData.getWeight());
        progress.setDistance(formData.getDistance());
        progress.setTime(formData.getTime());
        return progress;
    }


}
