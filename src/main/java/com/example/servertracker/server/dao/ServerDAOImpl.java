package com.example.servertracker.server.dao;

import com.example.servertracker.server.data.*;
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
    public List<DBTableSpaceDetail> getServerTableSpaceDEtail() {
        String sql = "select a.tablespace_name \"Table_Space_Name\",a.total \"SPACE_ALLOCATED\",a.total-b.free \"SPACE_USED\",b.free \"SPACE_FREE\", \n" +
                "trunc(((a.total-b.free)/a.total)*100) \"PCT_Used\" from \n" +
                "(select tablespace_name,sum(bytes/1024/1024) Total from dba_data_files group by tablespace_name) a, \n" +
                "(select tablespace_name,sum(bytes/1024/1024) Free from dba_free_space group by tablespace_name) b \n" +
                "where a.tablespace_name=b.tablespace_name";


        List<DBTableSpaceDetail> tableSpaceList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(DBTableSpaceDetail.class));

        return tableSpaceList;
    }

    @Override
    public List<PocOffering> testDB() {
        String sql = "select flat_offering_id,name from poc_offering where flat_offering_id =75800000";
        List<PocOffering> offerings = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(PocOffering.class));
        ;
        return offerings;
    }

    @Override
    public ServerPocAmStatus getServerPocAMStatus(String serverIp) {
        String sql = "\n" +
                "select o.name \"Cache_Name\",o.object_id \"Cache_Object\" , lv.value \"cacheStatus\", to_date(d.date_value,'dd-mm-yy') \"Created_When\" from nc_objects o , nc_params p, nc_list_values " +
                "lv,nc_params d where parent_id =9158663745313788273 and o.object_id = p.object_id and p.attr_id =9158663977813788559 and p.list_value_id =lv.list_value_id   and d.object_id =o.object_id and d.attr_id = 62    order by name desc fetch first 1 rows only";
        List<ServerPocAmStatus> serverPocAmStatus = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(ServerPocAmStatus.class));
        ServerPocAmStatus s = new ServerPocAmStatus();
        List<ServerPocAmStatus> serverPocAmStatusList = new ArrayList<>();
        for (ServerPocAmStatus sv : serverPocAmStatus) {
            s.setCacheStatus(sv.getCacheStatus());
            s.setServerIp(serverIp);
            s.setCacheObject(sv.getCacheObject());
            s.setCreatedWhen(sv.getCreatedWhen());
            s.setCacheName(sv.getCacheName());
            serverPocAmStatusList.add(s);

        }
        return serverPocAmStatusList.get(0);
    }

    @Override
    public List<PocTableData> getAllPOCTableData(String serverIp) {
        String sql = "select table_name \"POC_Table_Name\", \n" +
                "       to_number(extractvalue(xmltype(dbms_xmlgen.getxml('select count(*) c from '||owner||'.'||table_name)),'/ROWSET/ROW/C'))  \"table_count\" \n" +
                "from all_tables\n" +
                "where owner = 'U32_C5_6400' and table_name like'POC_%' and table_name not like '%BACKUP' and table_name  " +
                "not like '%BACK%' and table_name not like '%DF%' and table_name not like '%BUNDLE%' and table_name not in ('POC_BUSINESS_VIEW_CONDITION','POC_BVC_CONDITION_SET','POC_BVC_SALE_TYPE','POC_CATALOG_VALIDATION_JOURNAL','POC_CATALOG_VALIDATION_JOURNAL','POC_CATALOG_VALIDATION_SCOPE','POC_BVC_TOP_OBJECT','POC_DISCOUNT_TO_USER_GROUP','POC_COMPARED_TAG_CONFIGURATION','POC_RECOMMENDATION','POC_CONFIG_TO_KEY','POC_CONFIG_TO_KEY','POC_CONFIG_TO_ENTITY','POC_TAG_GROUP','POC_DISCOUNT_TO_TAG','POC_PH2_SERVICE_UPDATE_OFFER','POC_SPEND_CALC_CHAR','POC_PRICE_KEY_TO_PRICE_VAR')";
        List<PocTableData> pocTableDataList = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(PocTableData.class));

        List<PocTableData> pocTableDatas = new ArrayList<>();
        pocTableDataList.stream().forEach(p -> System.out.print("Sever POC Table Name " + p.getPocTableName() + " count" + p.getTableCount()));
        for (PocTableData p : pocTableDataList) {
            PocTableData pocTableData = new PocTableData();
            pocTableData.setServerIp(serverIp);
            pocTableData.setPocTableName(p.getPocTableName());
            pocTableData.setTableCount(p.getTableCount());
            pocTableDatas.add(pocTableData);
        }

        return pocTableDatas;
    }

    @Override
    public ServerVersion getServerVersion(String serverIp) {
        return null;
    }

    @Override
    public List<DashbordDetailInfo> getDashbordDetailInfo(String serverIp) {

        String sql =    "select dst.server_ip,dst.pct_used \"OS_SPACE_OCCUPY_PERC\",usd.used_perc \"DB_TABLE_SPACE_OCCUPY_PERC\" from  DB_TABLE_SPACE_DETAIL dst , unix_space_detail usd where dst.server_ip = usd.server_ip and  dst.server_ip =\n" +
                "\'10.109.38.8\'";
        List<DashbordDetailInfo> dashbordDetailInfos = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(DashbordDetailInfo.class));
        System.out.println("Dashbord Detail Size list"+dashbordDetailInfos.size());
         List<DashbordDetailInfo> dashbordDetailInfoList=new ArrayList<>();
         if(!dashbordDetailInfos.isEmpty()){
             for(DashbordDetailInfo ddi: dashbordDetailInfos){
                 DashbordDetailInfo detailInfo =new DashbordDetailInfo();
                 detailInfo.setServerIp(serverIp);
                 detailInfo.setDbTableSpaceOccupyPerc(ddi.getDbTableSpaceOccupyPerc());
                 detailInfo.setOsSpaceOccupyPerc(ddi.getOsSpaceOccupyPerc());
                 dashbordDetailInfoList.add(detailInfo);
         }
             return dashbordDetailInfoList;
        }
         else {
             return dashbordDetailInfoList;
         }


    }
}
