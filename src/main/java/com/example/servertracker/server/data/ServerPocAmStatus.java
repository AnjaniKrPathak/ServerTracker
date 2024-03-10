package com.example.servertracker.server.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerPocAmStatus {
    private  String serverIp;
    private String cacheObject;
    private String cacheName;
    private String cacheStatus;
    private String createdWhen;
}
