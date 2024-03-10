package com.example.servertracker.server.data;


import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class DBConnectionInfo {
        private String serverName;
        private String url;
        private String userName;
        private String userPass;
        private String className;



}
