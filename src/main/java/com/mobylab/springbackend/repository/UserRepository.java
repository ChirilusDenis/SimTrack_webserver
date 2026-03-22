package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    Boolean existsUserByEmail(String email);
    Boolean existsByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
