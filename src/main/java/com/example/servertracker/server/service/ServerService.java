package com.example.servertracker.server.service;

import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerTableSpace;

import java.util.ArrayList;
import java.util.List;

public interface ServerService {
    public List<ServerTableSpace> getServerTableSpaceDetail();
    public List<PocOffering> testDb();
}
