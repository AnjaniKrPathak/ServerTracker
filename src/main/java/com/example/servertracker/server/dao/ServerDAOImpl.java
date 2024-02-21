package com.example.servertracker.server.dao;

import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerTableSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerDAOImpl implements ServerDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<ServerTableSpace> getServerTableSpaceDEtail() {
        String sql=    "select a.tablespace_name \"Table_Space_Name\",a.total \"SPACE_ALLOCATED\",a.total-b.free \"SPACE_USED\",b.free \"SPACE_FREE\", \n" +
                "trunc(((a.total-b.free)/a.total)*100) \"PCT_Used\" from \n" +
                "(select tablespace_name,sum(bytes/1024/1024) Total from dba_data_files group by tablespace_name) a, \n" +
                "(select tablespace_name,sum(bytes/1024/1024) Free from dba_free_space group by tablespace_name) b \n" +
                "where a.tablespace_name=b.tablespace_name";


        List<ServerTableSpace> tableSpaceList =  jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ServerTableSpace.class));

        return tableSpaceList;
    }

    @Override
    public List<PocOffering> testDB() {
        String sql="select flat_offering_id,name from poc_offering";
        List<PocOffering> offerings = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(PocOffering.class));;
        return offerings;
    }
}
