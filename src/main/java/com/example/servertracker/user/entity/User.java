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

@SequenceGenerator(name = "USER_TEST_SEQ", allocationSize = 1)
public class User implements Serializable {

    @Id
    @NonNull
    private String email;
    private String name;
    private String project;
    private Integer status;




}
