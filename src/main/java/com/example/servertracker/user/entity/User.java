package com.example.servertracker.user.entity;




import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TEST")
@JsonDeserialize(as = User.class)


public class User implements Serializable {

    @Id
    @NonNull
    @SequenceGenerator(name="user_generator", sequenceName="USER_TEST_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
    private Long id;
    @NonNull
    private String email;
    private String name;
    private String project;
    private String password;
    private Integer status;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserServer> userServerList;





}
