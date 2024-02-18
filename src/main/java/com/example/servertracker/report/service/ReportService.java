package com.example.servertracker.report.service;

import com.example.servertracker.report.data.Item;

import java.util.List;

public interface ReportService {
    public void genReport();
    public byte[] getItemReport(List<Item> items, String format);
}
