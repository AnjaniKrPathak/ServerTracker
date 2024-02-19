package com.example.servertracker.server.dao;

import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerTableSpace;

import java.util.ArrayList;
import java.util.List;

public interface ServerDAO  {
    public List<ServerTableSpace> getServerTableSpaceDEtail();
    public List<PocOffering> testDB();
}
