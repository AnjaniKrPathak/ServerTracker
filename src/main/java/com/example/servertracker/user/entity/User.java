package com.example.servertracker.user.entity;




import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TEST")
@JsonDeserialize(as = User.class)
public class User {
  @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String name;
    private String project;
    private Integer status;


}
