package com.example.servertracker.user.repo;

import com.example.servertracker.user.entity.User;

import com.example.servertracker.user.entity.UserServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepo extends JpaRepository<User,Long> {

}
