package com.gymapp.gym.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{
    Profile findByUserDisplayName(String displayName);

    boolean existsByUserDisplayName(String displayName);
}
