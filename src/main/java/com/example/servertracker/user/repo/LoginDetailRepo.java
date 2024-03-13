package com.example.servertracker.user.repo;

import com.example.servertracker.user.entity.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginDetailRepo extends JpaRepository<LoginDetail,Long> {
}
