package com.example.servertracker.user.service;

import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
   UserRepo userRepo;

    public User addUser(User user){
      return userRepo.save(user);
    }

}
