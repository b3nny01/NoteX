package domain.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Notebook;
import domain.Note;

public class NotebookFrontend {
	private String username;
	private String name;
	private double score;
	private List<TagFrontend> tags;
	private List<NoteFrontend> notes;
	
	public NotebookFrontend(Notebook blocco) {
		super();
		this.username=blocco.getSignedUser().getUsername();
		this.name = blocco.getName();
		this.score = blocco.getScore();
		this.tags = blocco.getTags().stream().map(e -> {
			return new TagFrontend(e);
		}).collect(Collectors.toList());
		this.notes=blocco.getNotes().stream().map(n->{return new NoteFrontend(n);}).collect(Collectors.toList());
	}

	
	
	public NotebookFrontend() {
		super();
		this.username = "";
		this.name = "";
		this.score = -1;
		this.tags = new ArrayList<>();
		this.notes = new ArrayList<>();
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public List<TagFrontend> getTags() {
		return tags;
	}

	public void setTags(List<TagFrontend> tags) {
		this.tags = tags;
	}

	public List<NoteFrontend> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteFrontend> notes) {
		this.notes = notes;
	}
	
	

}
