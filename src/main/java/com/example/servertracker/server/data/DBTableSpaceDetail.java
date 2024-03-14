package com.example.servertracker.server.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DB_TABLE_SPACE_DETAIL")
public class DBTableSpaceDetail {
    @Id
    @SequenceGenerator(name="table_space_generator", sequenceName="DB_TABLE_SPACE_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="table_space_generator")
    private Long id;
    @Column(name = "TABLE_SPACE_NAME")
    private String tableSpaceName;
    @Column(name ="SERVER_IP")
    private String serverIp;
    @Column(name = "PCT_USED")
    private double pctUsed;
    @Column(name = "SPACE_ALLOCATED")
    private double spaceAllocated;
    @Column(name = "SPACE_USED")
    private double spaceUsed;
    @Column(name = "SPACE_FREE")
    private double spaceFree;

}
