package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.entity.UserServer;
import com.example.servertracker.user.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @PostMapping(value = "/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user){
          User u1 =userService.addUser(user);
         return ResponseEntity.ok(u1.getEmail() +" :"+"User Added ");
    }
    @PostMapping("/addServer")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> addUserServer(@RequestBody UserServer userServer){
       UserServer server =userService.addUserServer(userServer);
        return ResponseEntity.ok(server.getServerIp()+" "+"Server Added");
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAllServer")
    public ResponseEntity<List<UserServer>> getAllUserServer(){
        List<UserServer> userServers=userService.getAllUserServers();
        return ResponseEntity.ok(userServers);
    }
    @PostMapping("/createUserWithCSV")
    public ResponseEntity<?> createUserWithCSV(@RequestParam ("file") MultipartFile file){
        LOGGER.info("File uploaded: {} ",file.getOriginalFilename());
        List<String> response = new ArrayList<>();
        try {
            BufferedReader fileReader= new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord : csvRecords){
                User user = new User();
                user.setName(csvRecord.get("name"));
                user.setEmail(csvRecord.get("email"));
                user.setProject(csvRecord.get("project"));
                user.setStatus(1);

                try{
                    User user1= userService.addUser(user);
                    response.add("Created User "+user1.getName()+"with id:"+user1.getEmail());
                }
                catch (Exception e){
                    response.add("Unable to created User "+user.getName()+" msg:"+e.getMessage());
                }
            }
        }catch (IOException e){

        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/createServerWithCSV")
    public ResponseEntity<?> createServerWithCSV(@RequestParam ("file") MultipartFile file) throws IOException {
        LOGGER.info("File uploaded: {} ", file.getOriginalFilename());
        List<String> response = new ArrayList<>();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        for (CSVRecord csvRecord : csvRecords) {
            UserServer userServer = new UserServer();
            userServer.setServerIp(csvRecord.get("ServerIp"));
            userServer.setServerPassword(csvRecord.get("ServerPassword"));
            userServer.setServerUserName(csvRecord.get("ServerUserName"));


            try {
                UserServer server = userService.addUserServer(userServer);
                response.add("Created Server  " + server + "with id:" + server.getServerIp());
            } catch (Exception e) {
                response.add("Unable to created Server  " + userServer.getServerIp() + " msg:" + e.getMessage());
            }




        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("/getServer/{serverIp}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserServer> getServerDetailBasedOnIp(@PathVariable String serverIp){
        UserServer userServer=userService.getUserServer(serverIp);
        return new ResponseEntity<>(userServer,HttpStatus.OK);
    }

}
