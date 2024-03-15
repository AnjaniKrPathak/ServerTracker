package com.example.servertracker.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonDeserialize(as = UserServer.class)
@Table(name = "USER_SERVER")


public class UserServer {



    @Id
    @NonNull
    @SequenceGenerator(name="server_generator", sequenceName="USER_SERVER_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="server_generator")
    @Column(name = "ID")
    private Long id;
    @Column(name = "SERVER_IP")
    @NonNull
    private String serverIp;
    @Column(name = "SERVER_USER_NAME")
    private String serverUserName;
    @Column(name = "SERVER_PASSWORD")
    private String serverPassword;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
