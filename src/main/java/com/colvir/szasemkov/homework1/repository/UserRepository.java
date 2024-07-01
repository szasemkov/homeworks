package com.colvir.szasemkov.homework1.repository;

import com.colvir.szasemkov.homework1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
