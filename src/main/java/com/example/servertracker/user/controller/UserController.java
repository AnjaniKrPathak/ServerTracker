package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.entity.UserServer;
import com.example.servertracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user){
          User u1 =userService.addUser(user);
         return ResponseEntity.ok(u1.getEmail() +" :"+"User Added ");
    }
    @PostMapping("/addServer")
    public ResponseEntity<String> addUserServer(@RequestBody UserServer userServer){
       UserServer server =userService.addUserServer(userServer);
        return ResponseEntity.ok(server.getServerIp()+" "+"Server Added");
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/getAllServer")
    public ResponseEntity<List<UserServer>> getAllUserServer(){
        List<UserServer> userServers=userService.getAllUserServers();
        return ResponseEntity.ok(userServers);
    }


}
