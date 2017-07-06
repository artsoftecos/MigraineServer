package co.artsoft.architecture.migraine.model.bll;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import co.artsoft.architecture.migraine.GlobalProperties;

@Service
public class FileService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	/**
	 * global properties of the application.
	 */
	private GlobalProperties global;
	 
	/**
	 * Set the global properties file.
	 * @param global: the global property file.
	 */
	@Autowired
	public void setGlobal(GlobalProperties global) {
	   this.global = global;
	}
	
	@Value("${application.FileStorage}")
	private String fileStorage;
	
	@Autowired
	private AwsService awsService;
	
	/**
	 * Storage the audio file.
	 * @param file: the file to be storaged.
	 * @param identifierAudio: the number of the audio.
	 * @param request: Servlet to save the local file
	 * @return the name of the audio.
	 * @throws IOException: throws either error handling files.
	 * @throws InterruptedException 
	 * @throws AmazonClientException 
	 * @throws AmazonServiceException 
	 */
	public String storageFile(MultipartFile file, Long identifierAudio, HttpServletRequest request) throws IOException, AmazonServiceException, AmazonClientException, InterruptedException {		
		String name = "";
		if (fileStorage.equals("Amazon")) {
			LOGGER.info("	AWS: Start transform the temp file.");
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			name = FilenameUtils.getBaseName(file.getOriginalFilename());
			byte[] bytes = file.getBytes();
		    File temp = File.createTempFile(name, "."+extension);
		    FileOutputStream fos = new FileOutputStream(temp);
		    fos.write(bytes);
		    fos.close();
		    LOGGER.info("	AWS: Finish transform the temp file.");
			awsService.uploadFile(temp);
			name = temp.getName();
		} else {
			//Local
			LOGGER.info("	local: Start Save local file.");
			String pathAudio = request.getSession().getServletContext().getRealPath("/")+global.getFolderAudio();
			File folder = new File(pathAudio);
			if (!folder.exists()) {
				folder.mkdir(); 
			}
			name = identifierAudio + file.getOriginalFilename();
			String audioPath = pathAudio + name;
			byte[] bytes = file.getBytes();
			Path path = Paths.get(audioPath);
			Files.write(path, bytes);	
			LOGGER.info("	local: Finish Save local file.");
		}
		return name;
	}
	
	/**
	 * Get the file
	 * @param nameAudioFile: the name of the file to be searched.
	 * @param request : the Servlet of local context, it is used to get the local file.
	 * @return the file
	 * @throws Exception: if some error in S3 is gotten.
	 */
	public File getFile(String nameAudioFile, HttpServletRequest request) throws Exception {
		File audioFile = null;
		if (fileStorage.equals("Amazon")) {
			String extension = FilenameUtils.getExtension(nameAudioFile);
			String name = FilenameUtils.getBaseName(nameAudioFile);
			audioFile = awsService.downloadFile(name, "."+extension);		
		}
		else {
			String pathAudio = request.getSession().getServletContext().getRealPath("/")+global.getFolderAudio();		
			audioFile = new File(pathAudio + nameAudioFile);
		}
		return audioFile;
	}
	
	public ArrayList<String> GetInfoBuckets() throws Exception {
		return awsService.GetBuckets();
	}	
}
