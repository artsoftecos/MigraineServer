package co.artsoft.architecture.migraine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Global properties of the application.
 * @author ArtSoft
 *
 */
@Component
@PropertySource("classpath:global.properties")
public class GlobalProperties {

    @Value("${folderAudio}")
    private String folderAudio;

	public String getFolderAudio() {
		return folderAudio;
	}

	public void setFolderAudio(String folderAudio) {
		this.folderAudio = folderAudio;
	}

}
