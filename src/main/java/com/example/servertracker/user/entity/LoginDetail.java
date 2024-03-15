package com.example.servertracker.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@Table(name = "LOGIN_DETAIL")
@AllArgsConstructor
@NoArgsConstructor(force = true)


public class LoginDetail {
    @Id
    @NonNull
    @SequenceGenerator(name="login_generator", sequenceName="LOGIN_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="login_generator")
    private Long id;
    @NonNull
    private String loginId;
    private String password;
}
