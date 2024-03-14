package com.example.servertracker.server.dao;

import com.example.servertracker.server.data.DBTableSpaceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DBTableSpaceRepo extends JpaRepository<DBTableSpaceDetail,Long> {

}
