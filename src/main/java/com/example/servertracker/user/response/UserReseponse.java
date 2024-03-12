package com.example.servertracker.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReseponse {
    private String userId;
    private String message;
}
