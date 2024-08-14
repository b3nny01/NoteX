package domain.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Note;

public class NoteFrontend {
	private String username;
	private String notebook;
	private String name;
	private double score;
	private List<TagFrontend> tags;
	private String serverContextFile;
	private String filePath;

	public NoteFrontend(Note nota) {
		super();
		this.username = nota.getNotebook().getSignedUser().getUsername();
		this.notebook = nota.getNotebook().getName();
		this.name = nota.getName();
		this.score = nota.getScore();
		this.tags = nota.getTags().stream().map(e -> {
			return new TagFrontend(e);
		}).collect(Collectors.toList());
		this.serverContextFile = nota.getServerContextFile();
		this.filePath = nota.getFilePath();
	}
	
	

	public NoteFrontend() {
		super();
		this.username = "";
		this.notebook = "";
		this.name = "";
		this.score = -1;
		this.tags = new ArrayList<>();
		this.serverContextFile = "";
		this.filePath = "";
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNotebook() {
		return notebook;
	}

	public void setNotebook(String notebook) {
		this.notebook = notebook;
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

	public String getServerContextFile() {
		return serverContextFile;
	}

	public void setServerContextFile(String serverContextFile) {
		this.serverContextFile = serverContextFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

}
