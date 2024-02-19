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
        String sql=    "SELECT * FROM dba_tablespaces";


        List<ServerTableSpace> tableSpaceList =  jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ServerTableSpace.class));

        return tableSpaceList;
    }

    @Override
    public List<PocOffering> testDB() {
        String sql="select flat_offering_id,name from poc_offering where flat_offering_id =1153";
        List<PocOffering> offerings = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(PocOffering.class));;
        return offerings;
    }
}
