package com.example.servertracker.aws.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketObject {
    private String bucketName;
    private String path;

}
