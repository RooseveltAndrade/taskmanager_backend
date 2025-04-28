package com.taskmanager_backend.repository;

import com.taskmanager_backend.model.User; // Import correto da classe User
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}