package com.agus.springboot.belajarspringboot.repository;

import com.agus.springboot.belajarspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, String> {

    Optional<User> findFirstByToken(String Token);
}
