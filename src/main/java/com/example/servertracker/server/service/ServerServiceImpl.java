package com.example.servertracker.server.service;

import com.example.servertracker.server.dao.ServerDAO;
import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerTableSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    ServerDAO serverDAO;
    @Override
    public List<ServerTableSpace> getServerTableSpaceDetail() {
        return serverDAO.getServerTableSpaceDEtail();
    }

    @Override
    public List<PocOffering> testDb() {
        return serverDAO.testDB();
    }


}
