package co.artsoft.architecture.migraine.model.bll;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import co.artsoft.architecture.migraine.GlobalProperties;

@Service
public class FileService {
	
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
	
	@Autowired
	private AwsService awsService;
	
	/**
	 * Storage the audio file.
	 * @param file: the file to be storaged.
	 * @param identifierAudio: the number of the audio.
	 * @param
	 * @return the name of the audio.
	 * @throws IOException: throws either error handling files.
	 * @throws InterruptedException 
	 * @throws AmazonClientException 
	 * @throws AmazonServiceException 
	 */
	public String storageFile(MultipartFile file, Long identifierAudio, HttpServletRequest request) throws IOException, AmazonServiceException, AmazonClientException, InterruptedException {	
		  
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String name = FilenameUtils.getBaseName(file.getOriginalFilename());
		name = identifierAudio+name; 
		byte[] bytes = file.getBytes();
	    File temp = File.createTempFile(name, "."+extension);
	    FileOutputStream fos = new FileOutputStream(temp);
	    fos.write(bytes);
	    		
		awsService.uploadFile(temp);
		
		return name;
		/*String pathAudio = request.getSession().getServletContext().getRealPath("/")+global.getFolderAudio();
		//String pathAudio = global.getFolderAudio();
		// el path es: http://34.230.130.185/audio/3bad_boys_2.mp3
		File folder = new File(pathAudio);
		if (!folder.exists()) {
			folder.mkdir(); 
		}
		String nameAudio = identifierAudio + file.getOriginalFilename();
		String audioPath = pathAudio + nameAudio;
		byte[] bytes = file.getBytes();
		Path path = Paths.get(audioPath);
		Files.write(path, bytes);
		return nameAudio;*/
	}
}
