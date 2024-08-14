package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SignedUser extends Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserState state;
	private List<Report> reports;
	private List<Notebook> notebooks;
	private double score;

	public SignedUser() {
		super();
		this.state = UserState.NOT_REPORTED;
		this.reports = new ArrayList<>();
		this.notebooks = new ArrayList<>();
		this.score = 0;
	}

	public UserState getState() {
		return state;
	}

	public List<Report> getReports() {
		return reports;
	}

	public List<Notebook> getNotebooks() {
		return notebooks;
	}

	public double getScore() {
		return score;
	}

	public Optional<Notebook> getNotebook(String notebookName) {
		return this.notebooks.stream().filter(b -> {
			return b.getName().equals(notebookName);
		}).findFirst();
	}

	public void createNotebook(String notebookName, List<NotebookTag> tags) throws IllegalArgumentException {
		// parameters check
		Optional<Notebook> optNb = this.getNotebook(notebookName);
		if (optNb.isPresent())
			throw new IllegalArgumentException("notebook " + notebookName + " already exists");

		// method logic
		Notebook nb = new Notebook(notebookName, this, tags);
		this.notebooks.add(nb);
	}

	public void updateNotebook(String oldName, String newName, List<NotebookTag> newTags) throws IllegalArgumentException{
		// parameters check
		Optional<Notebook> optNb = this.getNotebook(oldName);
		if (optNb.isEmpty())
			throw new IllegalArgumentException("notebook " + oldName + " does not exist");

		Notebook nb = optNb.get();
		nb.updateName(newName);
		nb.updateTags(newTags);
	}

	public void deleteNotebook(String notebookName) throws IllegalArgumentException {
		// parameters check
		Optional<Notebook> optNb = this.getNotebook(notebookName);
		if (optNb.isEmpty())
			throw new IllegalArgumentException("notebook " + notebookName + " does not exist");

		// method logic
		Notebook nb = optNb.get();
		this.notebooks.remove(nb);
	}

	public List<Report> getActiveReports() {
		List<Report> result = new ArrayList<Report>();
		for (Report s : reports) {
			if (s.getState().equals(ReportState.TO_HANDLE)) {
				result.add(s);
			}
		}
		return result;
	}
	
	public void updateScore() {
		this.score=this.notebooks.stream().map(Notebook::getScore).filter(d->{return d>=0;}).mapToDouble(Double::doubleValue).average().orElse(0);
	}

	// ---------------------------------------------------------------------------
	// setters should only be used by Hibernate to populate the bean
	// ---------------------------------------------------------------------------

	public void setState(UserState state) {
		this.state = state;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public void setNotebooks(List<Notebook> notebooks) {
		this.notebooks = notebooks;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
