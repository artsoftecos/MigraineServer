package co.artsoft.architecture.migraine.model.bll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	/**
	 * Storage the audio file.
	 * @param file: the file to be storaged.
	 * @param identifierAudio: the number of the audio.
	 * @param
	 * @return the name of the audio.
	 * @throws IOException: throws either error handling files.
	 */
	public String storageFile(MultipartFile file, Long identifierAudio, HttpServletRequest request) throws IOException {		
		//String pathAudio = request.getSession().getServletContext().getRealPath("/")+global.getFolderAudio();
		String pathAudio = global.getFolderAudio();
		File folder = new File(pathAudio);
		if (!folder.exists()) {
			folder.mkdir(); 
		}
		String nameAudio = identifierAudio + file.getOriginalFilename();
		String audioPath = pathAudio + nameAudio;
		byte[] bytes = file.getBytes();
		Path path = Paths.get(audioPath);
		Files.write(path, bytes);
		return nameAudio;
	}
}
