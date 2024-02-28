package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user){
          User u1 =userService.addUser(user);
         return ResponseEntity.ok(u1.getEmail() +" :"+"User Added ");
    }
}
