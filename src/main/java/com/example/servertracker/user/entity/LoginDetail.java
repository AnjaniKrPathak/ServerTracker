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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String loginId;
    private String password;
}
