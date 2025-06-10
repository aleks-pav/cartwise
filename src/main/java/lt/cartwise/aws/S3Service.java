package lt.cartwise.aws;

import java.io.IOException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {

	private final S3Client s3;
	private String bucketName;
    private String cloudFrontBaseUrl;
    
	public S3Service(@Value("${aws.accessKeyId:FAKE_ACCESS_KEY}") String accessKey
			, @Value("${aws.secretAccessKey:FAKE_SECRET_KEY}") String secretKey
			, @Value("${aws.region:us-east-1}") String region
			, @Value("${aws.bucketName:files}") String bucketName
			, @Value("${aws.cloudfrontBaseUrl:https://fallback-url/}") String cloudFrontBaseUrl) {
		
		this.bucketName = bucketName;
		this.cloudFrontBaseUrl = cloudFrontBaseUrl;
		
		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
		
		this.s3 = S3Client.builder()
				.region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
	}
    
    
	public String uploadFile(MultipartFile file) {
        String key = generateKey(file.getOriginalFilename());

        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return getFileUrl(key);

        } catch (IOException | S3Exception e) {
            throw new RuntimeException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }


	private String generateKey(String originalFilename) {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        return "uploads/" + timestamp + "-" + originalFilename;
    }

    private String getFileUrl(String key) {
    	return cloudFrontBaseUrl + "/" + key;
    }
}
