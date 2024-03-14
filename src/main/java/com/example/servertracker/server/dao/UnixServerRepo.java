package com.example.servertracker.server.dao;

import com.example.servertracker.server.data.UnixSpaceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnixServerRepo extends JpaRepository<UnixSpaceDetail,Long> {


    List<UnixSpaceDetail> findByServerIp(String serverIp);
}
