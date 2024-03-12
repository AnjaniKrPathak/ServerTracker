package com.example.servertracker.user.repo;

import com.example.servertracker.user.entity.UserServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServerRepo extends JpaRepository<UserServer,Long> {
    UserServer getUserServerByServerIp( String serverIp);
}
