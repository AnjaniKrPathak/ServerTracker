package com.example.servertracker.server.data;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerVersion {
    private String serverVersion;
    private String serverIp;
}
