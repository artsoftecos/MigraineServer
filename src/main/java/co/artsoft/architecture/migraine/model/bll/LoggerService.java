package co.artsoft.architecture.migraine.model.bll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	@Value("${application.EnableLog}")
	private boolean isEnableLog;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public enum TYPE {
		INFO, ERROR, WARNING, DEBUG
	}

	/**
	 * Init the logger
	 * @param identifier: name of the init of set of logs.
	 */
	public void initLogger(String identifier) {
		if (isEnableLog) {
			LOGGER.info("--------------------------- "+identifier+" ---------------------------");
		}
	}

	/**
	 * Set message to log
	 * @param log: message
	 * @param type: the type of log
	 */
	public void setLog(String log, TYPE type) {
		if (isEnableLog) {
			switch (type) {
			case ERROR:
				LOGGER.error(log);
				break;
			case WARNING:
				LOGGER.warn(log);
				break;
			case DEBUG:
				LOGGER.warn(log);
				break;
			case INFO:
				LOGGER.info(log);
				break;
			}
		}
	}

	/**
	 * Finish logger
	 */
	public void finishLogger() {
		if (isEnableLog) {
			LOGGER.info("----------------------------------------------------------------------------");
		}
	}
}
