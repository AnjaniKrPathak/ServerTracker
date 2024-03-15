package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.LoginDetail;
import com.example.servertracker.user.entity.User;
import com.example.servertracker.user.entity.UserServer;
import com.example.servertracker.user.response.UserReseponse;
import com.example.servertracker.user.response.UserServerReponse;
import com.example.servertracker.user.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @PostMapping(value = "/addUser")
    public ResponseEntity<?>  addUser(@RequestBody User user){
          User u1 =userService.addUser(user);
          LoginDetail lg=new LoginDetail();
          lg.setLoginId(u1.getEmail());
          lg.setPassword(u1.getPassword());
        //LoginDetail loginDetail=userService.createLoginDetail(lg);
        UserReseponse ur=new UserReseponse();
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(user.getEmail()!=null){


            ur.setUserId(u1.getEmail());
            ur.setMessage("User Added Succfully");
            map.put("statusCode", HttpStatus.OK.value());
            map.put("data",ur);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/addServer")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addUserServer(@RequestBody UserServer userServer){
       UserServer server =userService.addUserServer(userServer);
        UserServerReponse u1=new UserServerReponse();
        u1.setServerIp(server.getServerIp());

        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(userServer.getServerIp()!=null){

            u1.setServerIp(server.getServerIp());
            u1.setMessage("server added Successfully");
            map.put("statusCode", HttpStatus.OK.value());
            map.put("data",u1);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
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
    @GetMapping("/getServerBasedOnServerIP/{serverIp}")

    public ResponseEntity<UserServer> getServerBasedOnServerIP(@PathVariable String serverIp){
        UserServer userServer=userService.getServerBasedOnServerIP(serverIp);
        return new ResponseEntity<>(userServer,HttpStatus.OK);
    }
    @GetMapping("/getServerBasedOnUserId")
    public ResponseEntity<?> getServerBasedonUserId(@PathVariable Long userId){
        List<UserServer> userServerList=userService.getServerBasedOnUserId(userId);
        return new ResponseEntity<>(userServerList,HttpStatus.OK);
    }

}
