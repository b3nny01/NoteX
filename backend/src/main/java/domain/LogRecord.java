package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LogRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime timestamp;
	private String description;
	
	
	
	public LogRecord() {
		super();
		this.timestamp = LocalDateTime.now();
		this.description = "";
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
