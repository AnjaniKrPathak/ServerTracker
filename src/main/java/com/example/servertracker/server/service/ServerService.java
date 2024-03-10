package com.example.servertracker.server.service;

import com.example.servertracker.server.data.*;
import com.example.servertracker.user.entity.UserServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ServerService {
    public List<ServerTableSpace> getServerTableSpaceDetail();
    public List<PocOffering> testDb();
    public List<ServerSpace> getOSInfo(LinuxServer linuxServer);

    Map<String, ArrayList<LinuxServer>> addServer(LinuxServer linuxServer);

    HashMap<String, List<ServerSpace>> getUserServerOSInfo(List<UserServer> userServers);

    ServerPocAmStatus getServerPocAMStatus(String serverIp);

    List<PocTableData> getAllPOCTableData(String serverIp);

    void getWeblogicAccess();

    ServerVersion getServerVersion(String serverIp);
}
