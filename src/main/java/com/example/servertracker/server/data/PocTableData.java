package com.example.servertracker.server.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PocTableData {
    private String serverIp;
    private String pocTableName;
    private String tableCount;
}
