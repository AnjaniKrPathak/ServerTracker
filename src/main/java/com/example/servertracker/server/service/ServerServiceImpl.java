package com.example.servertracker.server.service;

import com.example.servertracker.server.dao.ServerDAO;
import com.example.servertracker.server.dao.UnixServerRepo;
import com.example.servertracker.server.data.*;
import com.example.servertracker.user.entity.UserServer;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.*;

@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    ServerDAO serverDAO;
    @Autowired
    UnixServerRepo unixServerRepo;

    @Override
    public List<ServerTableSpace> getServerTableSpaceDetail() {
        return serverDAO.getServerTableSpaceDEtail();
    }

    @Override
    public List<PocOffering> testDb() {
        return serverDAO.testDB();
    }

    ArrayList<LinuxServer> list=new ArrayList<>();


    public List<UnixSpaceDetail> getOSInfo(LinuxServer linuxServer) {
        List<UnixSpaceDetail> serverSpaces = new ArrayList<>();
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

    @Override
    public HashMap<String, List<UnixSpaceDetail>> getUserServerOSInfo(List<UserServer> userServers) {
        HashMap<String,List<UnixSpaceDetail>> serverOsInfoMap=new HashMap<>();
        List<UnixSpaceDetail> serverSpaces=new ArrayList<>();
        for(UserServer u1:userServers){

            try {

                serverSpaces = puttyConnection("netcrk", u1.getServerIp(), 22, "crknet");
                serverOsInfoMap.put(u1.getServerIp(), serverSpaces);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return serverOsInfoMap;
    }

    @Override
    public ServerPocAmStatus getServerPocAMStatus(String serverIp) {
        ServerPocAmStatus serverPocAmStatus=serverDAO.getServerPocAMStatus(serverIp);
        return serverPocAmStatus;
    }

    @Override
    public List<PocTableData> getAllPOCTableData(String serverIp) {
        return serverDAO.getAllPOCTableData(serverIp);
    }

    @Override
    public void getWeblogicAccess()  {
        String hostname = "10.109.35.199";
        int port = 6400; // default port for WebLogic Server
        String username = "system";
        String password = "netcracker"; // change to your actual password
        String consoleURl="http://"+hostname+":"+port+"/console";
        // JNDI properties
        Properties jndiProps = new Properties();
        jndiProps.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        jndiProps.setProperty(Context.PROVIDER_URL, "t3://" + hostname + ":" + port);
        jndiProps.setProperty(Context.SECURITY_PRINCIPAL, username);
        jndiProps.setProperty(Context.SECURITY_CREDENTIALS, password);
        try{
            InitialContext ctx = new InitialContext(jndiProps);
            // Get MBean server connection
            MBeanServerConnection mbs = (MBeanServerConnection) ctx.lookup("java:comp/env/jmx/runtime");

            // Get server runtime MBean
            ObjectName serverRuntime = (ObjectName) mbs.queryNames(new ObjectName("com.bea:Name=*,Type=ServerRuntime"), null).iterator().next();

            // Retrieve server listen address and port
            String listenAddress = (String) mbs.getAttribute(serverRuntime, "ListenAddress");
            Integer listenPort = (Integer) mbs.getAttribute(serverRuntime, "ListenPort");

            // Construct console URL
            String consoleURL = "http://" + listenAddress + ":" + listenPort + consoleURl;

            // Print console URL
            System.out.println("WebLogic Server Console URL: " + consoleURL);

            // Close the context
            ctx.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public ServerVersion getServerVersion(String serverIp) {
        ServerVersion serverVersion=serverDAO.getServerVersion(serverIp);
        return serverVersion;
    }

    @Override
    public HashMap<String, List<UnixSpaceDetail>> saveUserServerOSInfo(String key, List<UnixSpaceDetail> value) {
        return null;
    }


    public  List<UnixSpaceDetail> puttyConnection(String userName, String hostName, int port, String password)
            throws JSchException, IOException {
        JSch jsch = new JSch();
        String command = "df -h";
        /*String command ="df -h | grep \"u02\"";*/
        List<UnixSpaceDetail> serverSpaces = new ArrayList<>();
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
            UnixSpaceDetail serverSpace=new UnixSpaceDetail();
            splitLine=line.split("\\s+");


              if(splitLine[0].substring(splitLine[0].lastIndexOf("/")+1,splitLine[0].length()).equals("vmdisk2-u02")){
                  serverSpace.setFileSystem(splitLine[0].substring(splitLine[0].lastIndexOf("/")+1,splitLine[0].length()));
                  serverSpace.setTotalSize(splitLine[1]);
                  serverSpace.setUsed(splitLine[2]);
                  serverSpace.setAvail(splitLine[3]);
                  serverSpace.setUsedPerc(splitLine[4]);
                  serverSpace.setMountedOn(splitLine[5]);
                  unixServerRepo.save(serverSpace);
                  serverSpaces.add(serverSpace);
              }




        }


        // Disconnect the channel and session when done
            channel.disconnect();
            session.disconnect();


        return serverSpaces;
    }









}
