package domain;

import java.io.Serializable;
import java.util.Optional;

public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String text;
	private ReportState state;
	private SignedUser reporterUser;
	private SignedUser reportedUser;
	private Optional<Assistant> assistant;
	
	
	
	public Report() {
		super();
		this.id = -1;
		this.text = "";
		this.state = ReportState.TO_HANDLE;
		this.reporterUser = new SignedUser();
		this.reportedUser = new SignedUser();
		this.assistant = Optional.empty();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public ReportState getState() {
		return state;
	}
	
	public void setState(ReportState state) {
		this.state = state;
	}
	
	public SignedUser getReporterUser() {
		return reporterUser;
	}
	
	public void setReporterUser(SignedUser reporterUser) {
		this.reporterUser = reporterUser;
	}
	
	public SignedUser getReportedUser() {
		return reportedUser;
	}
	
	public void setReportedUser(SignedUser reportedUser) {
		this.reportedUser = reportedUser;
	}
	
	public Optional<Assistant> getAssistant() {
		return assistant;
	}
	
	public void setAssistant(Optional<Assistant> assistant) {
		this.assistant = assistant;
	}
	
}
