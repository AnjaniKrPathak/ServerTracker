package com.example.servertracker.server.data;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class LinuxServer {

    private String host;

    private Integer port;


    private String username;


    private String password;





}
