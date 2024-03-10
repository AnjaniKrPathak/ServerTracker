package com.example.servertracker.user.service;

import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.entity.UserServer;

import java.util.List;


public interface UserService {
    public User addUser(User user);

    UserServer addUserServer(UserServer userServer);

    List<User> getAllUsers();

    List<UserServer> getAllUserServers();
}
