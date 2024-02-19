package com.example.servertracker.server.controller;

import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerTableSpace;
import com.example.servertracker.server.service.ServerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
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
    @GetMapping("/test")
    public ResponseEntity<?> testDB(){
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
}
