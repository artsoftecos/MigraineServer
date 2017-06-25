package co.artsoft.architecture.migraine.services;

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
	
	private GlobalProperties global;
	 
	@Autowired
	public void setGlobal(GlobalProperties global) {
	   this.global = global;
	}
	
	public String storageFile(MultipartFile file) throws IOException {		
	        	//TODO: el nombre del archivo debe cambiar, porque se puede subir mas de una vez el archivo
	        	String audioPath = global.getFolderAudio() + file.getOriginalFilename();
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(audioPath);
	            Files.write(path, bytes);
	            
	            return audioPath;
	}
}
