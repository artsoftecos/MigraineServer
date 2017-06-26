package co.artsoft.architecture.migraine.model.bll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	 * @return the name of the audio.
	 * @throws IOException: throws either error handling files.
	 */
	public String storageFile(MultipartFile file, Long identifierAudio) throws IOException {
		String nameAudio = identifierAudio + file.getOriginalFilename();
		String audioPath = global.getFolderAudio() + nameAudio;
		byte[] bytes = file.getBytes();
		Path path = Paths.get(audioPath);
		Files.write(path, bytes);
		return nameAudio;
	}
}
