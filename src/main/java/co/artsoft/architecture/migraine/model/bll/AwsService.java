package co.artsoft.architecture.migraine.model.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import co.artsoft.architecture.migraine.model.bll.LoggerService.TYPE;

@Service
public class AwsService {
	
	@Autowired
	private LoggerService LOGGER;
	
	private static TransferManager tx;

	@Value("${amazon.s3.default-bucket}")
	private String bucketName;

	@Value("${application.FileStorage.Async}")
	private boolean isFileStorageAsync;	
	
	/**
	 * Ctor. to handle interactions with AWS
	 */
	private AwsService() {
		// AWSCredentials credentials = new BasicAWSCredentials("KEY_ID",
		// "SECRET");
		// AmazonS3 s3Client =
		// AmazonS3ClientBuilder.standard().withCredentials(new
		// AWSStaticCredentialsProvider(credentials)).build();
		try {
			// credentials = new ProfileCredentialsProvider().getCredentials();
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			tx = TransferManagerBuilder.standard().withS3Client(s3Client).build();
		} catch (Exception e) {
			LOGGER.setLog("-- ERROR -- Not possible communication with AWS "+e.getMessage(), TYPE.ERROR);
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}
	}

	/**
	 * Upload a file to Cloud S3 Amazon
	 * 
	 * @param file
	 *            : the file to be uploaded.
	 * @throws AmazonServiceException:
	 *             exception if service is unabailable
	 * @throws AmazonClientException:
	 *             Exception if the connection is unabailable.
	 * @throws InterruptedException:
	 *             exception ig the uploaded file is not successful.
	 */
	public void uploadFile(File file) throws AmazonServiceException, AmazonClientException, InterruptedException {		
		LOGGER.setLog("	Initialized upload of document " + file.getName() +  " to aws", TYPE.INFO);
		PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
		Upload upload = tx.upload(request);
		if (!isFileStorageAsync) {
			upload.waitForUploadResult();
			LOGGER.setLog("	Finished upload Sync of document " + file.getName() +  " to aws", TYPE.INFO);
		} else {
			LOGGER.setLog("	Finished upload Async of document " + file.getName() +  " to aws", TYPE.INFO);
		}		
	}

	/**
	 * Download a file from Cloud S3 Amazon.
	 * 
	 * @param nameFile:
	 *            the file to be downloaded.
	 * @return the downloaded file.
	 * @throws Exception
	 */
	public File downloadFile(String nameFile, String extension) throws Exception {
		// AmazonS3 s3Client =
		// AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		File file = null;
		try {
			// TransferManager tx =
			// TransferManagerBuilder.standard().withS3Client(s3Client).build();
			LOGGER.setLog("	Initialized create temp file download of document " + nameFile +  " from aws", TYPE.INFO);
			file = File.createTempFile(nameFile, extension);
			LOGGER.setLog("	Initialized download of document " + nameFile +  " from aws", TYPE.INFO);
			GetObjectRequest request = new GetObjectRequest(bucketName, nameFile+extension);
			Download download = tx.download(request, file);
			download.waitForCompletion();
			LOGGER.setLog("	Finished download of document " + nameFile + " from aws", TYPE.INFO);
			// tx.shutdownNow();
			boolean success = file.exists() && file.canRead();
			if (!success) {
				LOGGER.setLog("	ERROR: Not Possible getting file " + nameFile + " from aws", TYPE.ERROR);
				throw new Exception("It was not possible to find the requested file, exists: " + file.exists()
						+ ", possible to read: " + file.canRead());
			}
			return file;

			// http://docs.aws.amazon.com/sdk-for-net/v2/developer-guide/net-dg-hosm.html
			/*
			 * S3Object o = s3Client.getObject(bucketName, nameFile);
			 * S3ObjectInputStream s3is = o.getObjectContent(); file = new
			 * File(nameFile); FileOutputStream fos = new
			 * FileOutputStream(file); byte[] read_buf = new byte[1024]; int
			 * read_len = 0; while ((read_len = s3is.read(read_buf)) > 0) {
			 * fos.write(read_buf, 0, read_len); } s3is.close(); fos.close();
			 * return file;
			 */
		} catch (AmazonServiceException e) {
			LOGGER.setLog("	ERROR: AmazonServiceException - file " + nameFile + " from aws, "+e.getMessage(), TYPE.ERROR);
			throw new AmazonServiceException("error: " + e.getErrorMessage() + ", " + e.getMessage(), e);
		} catch (FileNotFoundException e) {
			LOGGER.setLog("	ERROR: FileNotFoundException - file " + nameFile + " from aws, "+e.getMessage(), TYPE.ERROR);
			throw new FileNotFoundException("error: " + e.getMessage() + e.toString());
		} catch (IOException e) {
			LOGGER.setLog("	ERROR: IOException - file " + nameFile + " from aws, "+e.getMessage(), TYPE.ERROR);
			throw new IOException("error: " + e.getMessage() + e.toString());
		}
	}

	public void createAmazonS3Bucket() {
		if (tx.getAmazonS3Client().doesBucketExist(bucketName) == false) {
			tx.getAmazonS3Client().createBucket(bucketName);
		}
	}

	public ArrayList<String> GetBuckets() throws Exception {
		ArrayList<String> bucketsAndFiles = new ArrayList<String>();
		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			for (Bucket bucket : s3Client.listBuckets()) {
				bucketsAndFiles.add(bucket.getName());
			}

			ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucketName));
			for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
				bucketsAndFiles.add(objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
			}
		} catch (AmazonServiceException e) {
			throw new AmazonServiceException("error :" + e.getErrorMessage() + ", " + e.getMessage(), e);
		} catch (Exception e) {
			throw new Exception("error : " + e.getMessage(), e);
		}
		return bucketsAndFiles;
	}

	//////////////// Alternative code to download files from S3
	//////////////// /////////////////////////////////////////
	/*
	 * public File GetFile() throws Exception { File f = null; StringBuilder h =
	 * new StringBuilder(); try { AmazonS3 s3Client =
	 * AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	 * h.append("Listing buckets "); for (Bucket bucket :
	 * s3Client.listBuckets()) { h.append(" - " + bucket.getName()); }
	 * 
	 * ObjectListing objectListing = s3Client.listObjects(new
	 * ListObjectsRequest() .withBucketName(bucketName)); for (S3ObjectSummary
	 * objectSummary : objectListing.getObjectSummaries()) { h.append(" - " +
	 * objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() +
	 * ")"); }
	 * 
	 * 
	 * 
	 * String j = "bad_boys_27456726351288563715.mp3";
	 * h.append(" -- Downloading an object ---"); S3Object object =
	 * s3Client.getObject(new GetObjectRequest(bucketName, j));
	 * h.append("Content-Type: " + object.getObjectMetadata().getContentType());
	 * h.append(" -- objectdata..1.. ---"); InputStream objectData =
	 * object.getObjectContent(); h.append(" -- s3is..2.. ---");
	 * S3ObjectInputStream s3is = object.getObjectContent();
	 * h.append(" -- ..3.. ---"); File temp =
	 * File.createTempFile("bad_boys_27456726351288563715", "."+"mp3");
	 * h.append(" -- ..4.. ---"); FileOutputStream fos = new
	 * FileOutputStream(temp); h.append(" -- ..5.. ---"); byte[] read_buf = new
	 * byte[1024]; h.append(" -- ..6.. ---"); int read_len = 0;
	 * h.append(" -- ..7.. ---"); while ((read_len = s3is.read(read_buf)) > 0) {
	 * fos.write(read_buf, 0, read_len); } h.append(" -- ..8.. ---");
	 * s3is.close(); h.append(" -- ..9.. ---"); fos.close();
	 * h.append(" -- ..10.. ---"); return temp;
	 * 
	 * 
	 * // Process the objectData stream. //objectData.close();
	 * 
	 * //displayTextInputStream(object.getObjectContent());
	 * 
	 * } catch (AmazonServiceException e) { throw new
	 * AmazonServiceException("error" + e.getErrorMessage()+
	 * ", "+e.getMessage()+", "+ h.toString(), e);
	 * 
	 * } catch (Exception e) { throw new
	 * Exception("error, "+e.getMessage()+", "+ h.toString(), e); } //return
	 * null;
	 * 
	 * TransferManager tx =
	 * TransferManagerBuilder.standard().withS3Client(s3Client).build();
	 * 
	 * String text = ""; File localFile = new File(nameFile); text = text +
	 * "crea namefile"; //GetObjectRequest request = new
	 * GetObjectRequest(bucketName, nameFile); //text = text +
	 * ", crea el request" + request.getKey(); //Download download =
	 * tx.download(request, localFile);
	 * 
	 * 
	 * 
	 * Download download2 = tx.download(bucketName, nameFile, localFile); text =
	 * text + ", crea el download" + download2.getDescription();
	 * download2.waitForCompletion(); tx.shutdownNow(); text = text +
	 * ", crea completion" + download2.getObjectMetadata(); boolean success =
	 * localFile.exists() && localFile.canRead(); text = text + ", success="+
	 * success; if (!success) { throw new
	 * AmazonS3Exception("It was not possible to find the requested file, exists: "
	 * + localFile.exists()+ ", possible to read: "+ localFile.canRead()+ text);
	 * } return localFile; }
	 */

	/*
	 * public File GetFileFormUno() throws Exception { File f = null;
	 * StringBuilder h = new StringBuilder(); try { AmazonS3 s3Client =
	 * AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	 * h.append("Listing buckets "); for (Bucket bucket :
	 * s3Client.listBuckets()) { h.append(" - " + bucket.getName()); }
	 * 
	 * h.append("listar lo quie hay "); ObjectListing objectListing =
	 * s3Client.listObjects(new ListObjectsRequest()
	 * .withBucketName(bucketName)); for (S3ObjectSummary objectSummary :
	 * objectListing.getObjectSummaries()) { h.append(" - " +
	 * objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() +
	 * ")"); }
	 * 
	 * 
	 * String j = "bad_boys_27456726351288563715.mp3";
	 * h.append(" -- Downloading an object ---");
	 * 
	 * 
	 * S3Object object = s3Client.getObject(new GetObjectRequest(bucketName,
	 * j)); h.append("Content-Type: " +
	 * object.getObjectMetadata().getContentType());
	 * h.append(" -- objectdata..1.. ---"); InputStream objectData =
	 * object.getObjectContent(); h.append(" -- s3is..2.. ---");
	 * S3ObjectInputStream s3is = object.getObjectContent();
	 * h.append(" -- ..3.. ---"); f = new File(j); h.append(" -- ..4.. ---");
	 * FileOutputStream fos = new FileOutputStream(f);
	 * h.append(" -- ..5.. ---"); byte[] read_buf = new byte[1024];
	 * h.append(" -- ..6.. ---"); int read_len = 0; h.append(" -- ..7.. ---");
	 * while ((read_len = s3is.read(read_buf)) > 0) { fos.write(read_buf, 0,
	 * read_len); } h.append(" -- ..8.. ---"); s3is.close();
	 * h.append(" -- ..9.. ---"); fos.close(); h.append(" -- ..10.. ---");
	 * return f;
	 * 
	 * 
	 * // Process the objectData stream. //objectData.close();
	 * 
	 * //displayTextInputStream(object.getObjectContent());
	 * 
	 * File file = null; try {
	 * //http://docs.aws.amazon.com/sdk-for-net/v2/developer-guide/net-dg-hosm.
	 * html S3Object o = s3Client.getObject(bucketName, nameFile);
	 * S3ObjectInputStream s3is = o.getObjectContent(); file = new
	 * File(nameFile); FileOutputStream fos = new FileOutputStream(file); byte[]
	 * read_buf = new byte[1024]; int read_len = 0; while ((read_len =
	 * s3is.read(read_buf)) > 0) { fos.write(read_buf, 0, read_len); }
	 * s3is.close(); fos.close(); return file; } catch (AmazonServiceException
	 * e) { throw new AmazonServiceException("error" + e.getErrorMessage()+
	 * ", "+e.getMessage()+", "+ h.toString(), e); } catch (Exception e) { throw
	 * new Exception("error, "+e.getMessage()+", "+ h.toString(), e); }
	 * 
	 * //return null;
	 * 
	 * h.append(" -- tx..1.. ---"); TransferManager tx =
	 * TransferManagerBuilder.standard().withS3Client(s3Client).build();
	 * h.append(" -- tx..2.. ---"); String text = ""; h.append(" -- ..3.. ---");
	 * File localFile = File.createTempFile("bad_boys_27456726351288563715",
	 * "."+"mp3"); //File localFile = new File(j); h.append(" -- ..4.. ---");
	 * text = text + "crea namefile"; h.append(" -- ..5.. ---");
	 * //GetObjectRequest request = new GetObjectRequest(bucketName, nameFile);
	 * //text = text + ", crea el request" + request.getKey(); //Download
	 * download = tx.download(request, localFile);
	 * 
	 * Download download2 = tx.download(bucketName, j, localFile);
	 * h.append(" -- ..6.. ---"); text = text + ", crea el download" +
	 * download2.getDescription(); h.append(" -- ..7.. ---");
	 * download2.waitForCompletion(); h.append(" -- ..8.. ---");
	 * tx.shutdownNow(); h.append(" -- ..9.. ---"); text = text +
	 * ", crea completion" + download2.getObjectMetadata();
	 * h.append(" -- ..10.. ---"); boolean success = localFile.exists() &&
	 * localFile.canRead(); h.append(" -- ..11.. ---"); text = text +
	 * ", success="+ success; if (!success) { throw new
	 * AmazonS3Exception("It was not possible to find the requested file, exists: "
	 * + localFile.exists()+ ", possible to read: "+ localFile.canRead()+ text +
	 * "," + h.toString()); } return localFile; } catch (AmazonServiceException
	 * e) { throw new AmazonServiceException("error" + e.getErrorMessage()+
	 * ", "+e.getMessage()+", "+ h.toString(), e); } catch (Exception e) { throw
	 * new Exception("error, "+e.getMessage()+", "+ h.toString(), e); } }
	 */
}
