package com.example.servertracker.server.controller;

import com.example.servertracker.server.data.LinuxServer;
import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerSpace;
import com.example.servertracker.server.data.ServerTableSpace;
import com.example.servertracker.server.service.ServerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// @CrossOrigin(origins = "http://localhost:8084")
@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    ServerService serverService;

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
}
