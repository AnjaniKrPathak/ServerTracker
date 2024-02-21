package com.example.servertracker.server.data;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ServerTableSpace {
    private String tableSpaceName;
    private double pctUsed;
    private double spaceAllocated;
    private double spaceUsed;
    private double spaceFree;

}
