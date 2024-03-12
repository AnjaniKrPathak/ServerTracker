package com.example.servertracker.server.controller;

import com.example.servertracker.server.data.*;
import com.example.servertracker.server.service.ServerService;

import com.example.servertracker.user.entity.UserServer;
import com.example.servertracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.servertracker.server.dao.ConfigDataSource.getDBFlatOfferingDetails;


// @CrossOrigin(origins = "http://localhost:8084")
@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    ServerService serverService;

    @Autowired

    UserService userService;


    @GetMapping("/dbinfo")
    public ResponseEntity<?> getDBInfo(){
        List<ServerTableSpace> tableSpacesList=serverService.getServerTableSpaceDetail();
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(!tableSpacesList.isEmpty()){
            map.put("status", 1);
            map.put("data",tableSpacesList);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","No Data Found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }





    }
    @GetMapping
    public ResponseEntity<?> getServers(){
        List<PocOffering> offerings= serverService.testDb();
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        for(PocOffering offering:offerings){
            System.out.println(offering.getFlatOfferingId()+"Name "+offering.getName());
        }
        if(!offerings.isEmpty()){
            map.put("status", 1);
            map.put("data",offerings);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","No Data Found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }



    }
    @PostMapping("/osSpaceInfo")
    public ResponseEntity<?> getOSInfo(@RequestBody LinuxServer linuxServer){
        List<ServerSpace> serverSpaceList= serverService.getOSInfo(linuxServer);
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(!serverSpaceList.isEmpty()){
            map.put("status", 1);
            map.put("data",serverSpaceList);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","No Data Found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/addserver")
    public ResponseEntity<Map<String,ArrayList<LinuxServer>>> addServer(@RequestBody LinuxServer linuxServer){
        Map<String, ArrayList<LinuxServer>> map=serverService.addServer(linuxServer);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/serverOsSpaceInfo")
    public ResponseEntity<?> getOSInfo(){
        List<UserServer> userServers=userService.getAllUserServers();
        HashMap<String, List<ServerSpace>> serverOSInfo= serverService.getUserServerOSInfo(userServers);
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(!serverOSInfo.isEmpty()){
            map.put("status", 1);
            map.put("data",serverOSInfo);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","No Data Found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }


    }
    @GetMapping("/pocamstatus")
    public ResponseEntity<ServerPocAmStatus> getServerPocAMStatus(@RequestParam String serverIp){
        ServerPocAmStatus serverPocAmStatus=serverService.getServerPocAMStatus(serverIp);
        return ResponseEntity.ok(serverPocAmStatus);
    }
    @GetMapping("/poctablecount")
    public ResponseEntity<List<PocTableData>> getAllPOCTableData(@RequestParam String serverIp){
       List<PocTableData> pocTableDataList=serverService.getAllPOCTableData(serverIp);
        return ResponseEntity.ok(pocTableDataList);
    }
    @GetMapping("/getWeblogicAccess")
    public ResponseEntity<String> getWeblogicAccess(){
        serverService.getWeblogicAccess();
        return  ResponseEntity.ok("Got Acces");

    }
    @GetMapping("/sereverbuildVersion")
    public ResponseEntity<?> getServerBuildVersion(){
        Map<String, DBConnectionInfo> hashMap=getDBDetailsMap();
        StringBuilder  result=new StringBuilder();

        // Get the set of entries from the HashMap
        Set<Map.Entry<String, DBConnectionInfo>> entrySet = hashMap.entrySet();

        // Create an iterator for the entry set
        Iterator iterator = entrySet.iterator();

        // Iterate through the entries using a while loop
        System.out.println("Iterating HashMap without functional programming:");
        result=new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, DBConnectionInfo> entry = (Map.Entry<String, DBConnectionInfo>) iterator.next();
            String key = entry.getKey();
            DBConnectionInfo value = entry.getValue();
            result=result.append("Server Name: ")
                    .append(value.getServerName())
                    .append(serverService.getServerVersion(value.getServerName()));

        }
        System.out.println(" Final Result: "+result);
        return new ResponseEntity<>(result, HttpStatus.OK);


    }
    @GetMapping("/multipleDBinfo")
    public ResponseEntity<?> getMultipleDBInfo() throws Exception {
        //Calling Properties/Db to get all DB information.
        Map<String, DBConnectionInfo> hashMap=getDBDetailsMap();
        StringBuilder  result=new StringBuilder();

        // Get the set of entries from the HashMap
        Set<Map.Entry<String, DBConnectionInfo>> entrySet = hashMap.entrySet();

        // Create an iterator for the entry set
        Iterator iterator = entrySet.iterator();

        // Iterate through the entries using a while loop
        System.out.println("Iterating HashMap without functional programming:");
        result=new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, DBConnectionInfo> entry = (Map.Entry<String, DBConnectionInfo>) iterator.next();
            String key = entry.getKey();
            DBConnectionInfo value = entry.getValue();
            result=result.append("Server Name: ")
                    .append(value.getServerName())
                    .append(getDBFlatOfferingDetails(value.getUrl(),value.getUserName()));

        }
        System.out.println(" Final Result: "+result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    Setting Differnt DB infromation data.
     */
    private  Map getDBDetailsMap() {
        Map<String, DBConnectionInfo> dbDetailsMap=new HashMap<String, DBConnectionInfo>();
//		String server_name="jdbc:oracle:thin:@10.109.38.8:1524/DBG195";
//        String user_name="U32_C5_6400";
        List<UserServer> userServers=userService.getAllUserServers();
        for(UserServer userServer:userServers){
            DBConnectionInfo dbConnectionInfo;
            dbConnectionInfo=new DBConnectionInfo();
            dbConnectionInfo.setServerName(userServer.getServerUserName());
            dbConnectionInfo.setUrl("jdbc:oracle:thin:@"+userServer.getServerIp()+":1524/DBG195");
            dbConnectionInfo.setUserName(userServer.getServerUserName());
            dbConnectionInfo.setUserPass(userServer.getServerPassword());
            dbConnectionInfo.setClassName("oracle.jdbc.OracleDriver");
            dbDetailsMap.put(dbConnectionInfo.getServerName(), dbConnectionInfo);

        }




        /*dbConnectionInfo=new DBConnectionInfo();
        dbConnectionInfo.setServerName("10.109.38.8");
        dbConnectionInfo.setUrl("jdbc:oracle:thin:@10.109.38.8:1524/DBG195");
        dbConnectionInfo.setUserName("U32_C5_6400");
        dbConnectionInfo.setUserPass("U32_C5_6400");
        dbConnectionInfo.setClassName("oracle.jdbc.OracleDriver");
        dbDetailsMap.put(dbConnectionInfo.getServerName(), dbConnectionInfo);


        dbConnectionInfo=new DBConnectionInfo();
        dbConnectionInfo.setServerName("10.109.68.122");
        dbConnectionInfo.setUrl("jdbc:oracle:thin:@10.109.68.122:1524/DBG195");
        dbConnectionInfo.setUserName("U32_C5_6400");
        dbConnectionInfo.setUserPass("U32_C5_6400");
        dbConnectionInfo.setClassName("oracle.jdbc.OracleDriver");
        dbDetailsMap.put(dbConnectionInfo.getServerName(), dbConnectionInfo);

        dbConnectionInfo=new DBConnectionInfo();
        dbConnectionInfo.setServerName("10.109.68.95");
        dbConnectionInfo.setUrl("jdbc:oracle:thin:@10.109.68.95:1524/DBG195");
        dbConnectionInfo.setUserName("U32_C5_6400");
        dbConnectionInfo.setUserPass("U32_C5_6400");
        dbConnectionInfo.setClassName("oracle.jdbc.OracleDriver");
        dbDetailsMap.put(dbConnectionInfo.getServerName(), dbConnectionInfo);*/

        return dbDetailsMap;
    }

}
