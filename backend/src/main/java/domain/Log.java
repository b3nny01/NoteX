package domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<LogRecord> records;
	private File logFile;
	private final String LOG_FILE_PATH="./log/log.txt";
	
	
	
	public Log() {
		super();
		this.records = new ArrayList<>();
		this.logFile = new File(LOG_FILE_PATH);
	}

	public List<LogRecord> getRecords() {
		return records;
	}
	
	public void setRecords(List<LogRecord> records) {
		this.records = records;
	}
	
	public File getLogFile() {
		return logFile;
	}
	
	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}
	
	public void addRecord(String record) {
		// supponendo che record corrisponda alla descrizione
		LogRecord lr = new LogRecord();
		lr.setDescription(record);
		lr.setTimestamp(LocalDateTime.now());
		records.add(lr);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
		try {
			FileWriter fw = new FileWriter(logFile);
			fw.append(lr.getTimestamp().format(formatter) + " " + lr.getDescription());
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
