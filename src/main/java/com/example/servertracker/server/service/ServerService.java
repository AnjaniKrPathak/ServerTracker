package com.example.servertracker.server.service;

import com.example.servertracker.server.data.*;
import com.example.servertracker.user.entity.UserServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ServerService {
    public List<DBTableSpaceDetail> getServerTableSpaceDetail();
    public List<PocOffering> testDb();
    public List<UnixSpaceDetail> getOSInfo(LinuxServer linuxServer);

    Map<String, ArrayList<LinuxServer>> addServer(LinuxServer linuxServer);

    HashMap<String, List<UnixSpaceDetail>> getUserServerOSInfo(List<UserServer> userServers);

    ServerPocAmStatus getServerPocAMStatus(String serverIp);

    List<PocTableData> getAllPOCTableData(String serverIp);

    void getWeblogicAccess();

    ServerVersion getServerVersion(String serverIp);

    HashMap<String, List<UnixSpaceDetail>> saveUserServerOSInfo(String key, List<UnixSpaceDetail> value);

    List<DashbordDetailInfo> getDashbordDetailInfo(String serverIp);
}
