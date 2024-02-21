package com.example.servertracker.report.service;

import com.example.servertracker.report.data.Item;
import com.example.servertracker.server.data.ServerTableSpace;

import java.util.List;

public interface ReportService {
    public void genReport();
    public byte[] getItemReport(List<ServerTableSpace> items, String format);
}
