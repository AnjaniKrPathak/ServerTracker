package com.example.servertracker.aws;

import com.example.servertracker.aws.service.BucketService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Random;

@Service
public class BucketServiceImpl implements BucketService {



    @Override
    public void createBucket() {
        Region region=Region.US_WEST_1;
        S3Client s3=S3Client.builder().region(region).build();
        String bucket="bucket"+System.currentTimeMillis();

        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(region.id())
                        .build())
                .build();

        s3.createBucket(createBucketRequest);
    }

    @Override
    public void deleteBucket(String bucket) {
        Region region= Region.US_WEST_1;
        S3Client s3Client=S3Client.builder().region(region).build();
        DeleteBucketRequest deleteBucketRequest=DeleteBucketRequest.builder().bucket(bucket).build();
        s3Client.deleteBucket(deleteBucketRequest);

    }

    @Override
    public String uploadObject(String bucket,String fileName) {
        Region region=Region.US_WEST_1;
        S3Client s3=S3Client.builder().region(region).build();

       PutObjectRequest request= PutObjectRequest.builder().bucket(bucket).key(fileName).build();
        s3.putObject(request,RequestBody.fromFile(new File(fileName)));

        GetUrlRequest urlRequestrequest = GetUrlRequest.builder().bucket(bucket ).key(fileName).build();
        String url = s3.utilities().getUrl(urlRequestrequest).toExternalForm();
        return url;

    }


}
