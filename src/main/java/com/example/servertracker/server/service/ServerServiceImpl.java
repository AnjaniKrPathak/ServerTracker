package com.example.servertracker.server.service;

import com.example.servertracker.server.dao.ServerDAO;
import com.example.servertracker.server.data.LinuxServer;
import com.example.servertracker.server.data.PocOffering;
import com.example.servertracker.server.data.ServerSpace;
import com.example.servertracker.server.data.ServerTableSpace;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.*;

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

    ArrayList<LinuxServer> list=new ArrayList<>();


    public List<ServerSpace> getOSInfo(LinuxServer linuxServer) {
        List<ServerSpace> serverSpaces = new ArrayList<>();
        try {

            serverSpaces = puttyConnection(linuxServer.getUsername(), linuxServer.getHost(), linuxServer.getPort(), linuxServer.getPassword());
            System.out.println("In Get OS Info : "+serverSpaces.stream().count());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return serverSpaces;
    }

    @Override
    public Map<String, ArrayList<LinuxServer>> addServer(LinuxServer linuxServer) {
        Map<String,ArrayList<LinuxServer>> serverMap=new HashMap<>();
        //serverMap.put(linuxServer.getHost(),new LinuxServer(linuxServer.getHost(),linuxServer.getPort(),linuxServer.getUsername(),linuxServer.getPassword()));

        list.add(linuxServer);
        for(Map.Entry entry: serverMap.entrySet()){

            serverMap.put(linuxServer.getHost(),list);

        }
        return serverMap;

    }

    public static List<ServerSpace> puttyConnection(String userName, String hostName, int port, String password)
            throws JSchException, IOException {
        JSch jsch = new JSch();
        String command = "df -h";
        /*String command ="df -h | grep \"u02\"";*/
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
        HashMap<String, String> hm= new HashMap<String, String>();
        String splitLine[];
        String line;
        System.out.println(reader.readLine());
        int i=0;
        while ((line = reader.readLine()) != null) {
            ServerSpace serverSpace=new ServerSpace();
            splitLine=line.split("\\s+");

            /*hm.put("Filesystem", splitLine[0].substring(splitLine[0].lastIndexOf("/")+1,splitLine[0].length()));
            hm.put("Size", splitLine[1]);
            hm.put("Used",splitLine[2]);
            hm.put("Avail", splitLine[3]);
            hm.put("Use%", splitLine[4]);
            hm.put("Mouneted_On", splitLine[5]);*/


                serverSpace.setFileSystem(splitLine[0].substring(splitLine[0].lastIndexOf("/")+1,splitLine[0].length()));
                serverSpace.setSize(splitLine[1]);
                serverSpace.setUsed(splitLine[2]);
                serverSpace.setAvail(splitLine[3]);
                serverSpace.setUse(splitLine[4]);
                serverSpace.setMountedOn(splitLine[5]);
            serverSpaces.add(serverSpace);



        }
      /*  for(Map.Entry<String,String> entry:hm.entrySet()){
            System.out.println("Key "+entry.getKey()+" Value"+entry.getValue());
        }
       for(Map.Entry<String,String> entry:hm.entrySet()){
           ServerSpace serverSpace=new ServerSpace();
           if(entry.getKey()=="Filesystem"){
               serverSpace.setFileSystem(entry.getValue());
           } else if (entry.getKey()=="Size") {
               serverSpace.setSize(entry.getValue());

           } else if (entry.getKey()=="Avail") {
               serverSpace.setAvail(entry.getValue());

           } else if (entry.getKey()=="Used_per") {
               serverSpace.setAvail(entry.getValue());

           } else if (entry.getKey()=="Mounted_on") {
               serverSpace.setMountedOn(entry.getValue());

           }
           serverSpaces.add(serverSpace);

       }*/


        // Disconnect the channel and session when done
            channel.disconnect();
            session.disconnect();


        return serverSpaces;
    }









}
