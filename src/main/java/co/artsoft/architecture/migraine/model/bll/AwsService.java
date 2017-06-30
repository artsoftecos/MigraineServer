package co.artsoft.architecture.migraine.model.bll;

import java.io.File;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

@Service
public class AwsService {

	// private static AWSCredentials credentials = null;
	private static TransferManager tx;
	private static String bucketName = "artsoft_audiofiles";

	private AwsService() {
		// AWSCredentials credentials = new
		// BasicAWSCredentials("AKIAJTJG5ZULXA6SORSA",
		// "Jl9nBO4zRCymz9cYydEE5OIY5UFAPCJ2WPUqhTf7");
		// AmazonS3 s3Client =
		// AmazonS3ClientBuilder.standard().withCredentials(new
		// AWSStaticCredentialsProvider(credentials)).build();

		try {
			// credentials = new ProfileCredentialsProvider().getCredentials();
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			tx = TransferManagerBuilder.standard().withS3Client(s3Client).build();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}
	}

	public void uploadFile(File file) throws AmazonServiceException, AmazonClientException, InterruptedException {
		// createAmazonS3Bucket();
		PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
		Upload upload = tx.upload(request);
		upload.waitForUploadResult();
	}

	/*
	 * private void createAmazonS3Bucket() { if
	 * (tx.getAmazonS3Client().doesBucketExist(bucketName) == false) {
	 * tx.getAmazonS3Client().createBucket(bucketName); } }
	 */
}
