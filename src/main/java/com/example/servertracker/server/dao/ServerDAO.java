package com.example.servertracker.server.dao;

import com.example.servertracker.server.data.*;

import java.util.List;

public interface ServerDAO  {
    public List<ServerTableSpace> getServerTableSpaceDEtail();
    public List<PocOffering> testDB();

    ServerPocAmStatus getServerPocAMStatus(String serverIp);

    List<PocTableData> getAllPOCTableData(String serverIp);

    ServerVersion getServerVersion(String serverIp);
}
