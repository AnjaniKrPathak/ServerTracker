package com.example.servertracker.server.service;

import com.ctc.wstx.util.StringUtil;
import com.example.servertracker.server.dao.ServerDAO;
import com.example.servertracker.server.data.LinuxServer;
import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerSpace;
import com.example.servertracker.server.data.ServerTableSpace;
import com.jcraft.jsch.*;
import org.jfree.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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


    public List<ServerSpace> getOSInfo(LinuxServer linuxServer) {
        List<ServerSpace> serverSpaces = null;
        try {

            serverSpaces = puttyConnection(linuxServer.getUsername(), linuxServer.getHost(), linuxServer.getPort(), linuxServer.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return serverSpaces;
    }

    public static List<ServerSpace> puttyConnection(String userName, String hostName, int port, String password)
            throws JSchException, IOException {
        JSch jsch = new JSch();
        String command = "df -h";
        List<ServerSpace> serverSpaces = new ArrayList<>();
        Session session = jsch.getSession(userName, hostName, port);

        // Set the password for authentication (you can use other methods for authentication)
        session.setPassword(password);

        // Avoid checking host key
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // Establish the connection
        session.connect();

        // Open a channel
        Channel channel = session.openChannel("exec");

        // Set the command to be executed
        ((ChannelExec) channel).setCommand(command);

        // Connect the channel
        channel.connect();

        // Read the output of the command
        InputStream inputStream = channel.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String[] st=new String[reader.readLine().length()];
        String line;
        int i=0;
        while ((line = reader.readLine()) != null) {
            st[i]=line;
            i++;
        }
        String[][] space=new String[9][6];

        for(int j=0;j<st.length;j++){
            if(st[j]!=null){
               String value=new String();
               value=st[j];
               value.trim();
               for(int k=0;k<6;k++){
                   space[j][k]= Arrays.toString(value.split("//s"));





                }

            }

           }

        for(int m=0;m<space.length;m++){

            serverSpaces.add(new ServerSpace(space[m][0],space[m][1],space[m][2],space[m][3],space[m][4],space[m][5]));
        }












            // Disconnect the channel and session when done
            channel.disconnect();
            session.disconnect();


        return serverSpaces;
    }









}
