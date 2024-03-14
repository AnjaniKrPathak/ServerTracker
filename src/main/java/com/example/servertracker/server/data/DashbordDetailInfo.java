package com.example.servertracker.server.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashbordDetailInfo {

    private String serverIp;
    private String dbTableSpaceOccupyPerc;
    private String osSpaceOccupyPerc;


}
