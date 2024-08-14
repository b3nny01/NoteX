package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Assistant extends Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Report> reports;

	public Assistant() {
		super();
		this.reports = new ArrayList<>();
		;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

}
