package com.gymapp.gym.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

}
