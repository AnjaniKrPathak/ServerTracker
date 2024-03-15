package com.example.servertracker.user.service;

import com.example.servertracker.user.entity.LoginDetail;
import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.entity.UserServer;
import com.example.servertracker.user.repo.LoginDetailRepo;
import com.example.servertracker.user.repo.UserRepo;
import com.example.servertracker.user.repo.UserServerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
   UserRepo userRepo;
    @Autowired
    UserServerRepo userServerRepo;
    @Autowired
    LoginDetailRepo loginDetailRepo;

    public User addUser(User user){
      return userRepo.save(user);
    }

    @Override
    public UserServer addUserServer(UserServer userServer) {
        return userServerRepo.save(userServer);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<UserServer> getAllUserServers() {
        return userServerRepo.findAll();
    }

    @Override
    public UserServer getServerBasedOnServerIP(String serverIp) {
        return userServerRepo.getUserServerByServerIp(serverIp);
    }

    @Override
    public LoginDetail createLoginDetail(LoginDetail lg) {
        return loginDetailRepo.save(lg);
    }

    @Override
    public List<UserServer> getServerBasedOnUserId(Long userId) {
        return userServerRepo.getByUser(userId);
    }

}
