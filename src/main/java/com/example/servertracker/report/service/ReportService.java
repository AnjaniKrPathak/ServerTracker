package com.example.servertracker.report.service;

import com.example.servertracker.server.data.UnixSpaceDetail;
import com.example.servertracker.server.data.DBTableSpaceDetail;

import java.util.List;

public interface ReportService {
    public void genReport();

    public byte[] getItemReport(List<DBTableSpaceDetail> items, String format);

    public void genOSSpaceReport();

    public byte[] getServerSpaceReport(List<UnixSpaceDetail> items, String format);
}
