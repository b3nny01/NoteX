package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class Note implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<NoteTag> tags;
	private Notebook notebook;
	private String serverContextFile;
	private String filePath;
	private List<Review> reviews;
	private double score;

	public Note() {
		super();
		this.id = -1;
		this.name = "";
		this.tags = new ArrayList<>();
		this.notebook = new Notebook();
		this.serverContextFile = "";
		this.filePath = "";
		this.reviews = new ArrayList<>();
		this.score = 0;
	}

	public Note(String name, Notebook notebook, String serverContextFile, String filePath, List<NoteTag> tags) {
		this();
		this.name = name;
		this.notebook = notebook;
		this.serverContextFile = serverContextFile;
		this.filePath = filePath;

		for (NoteTag t : tags) {
			this.tags.add(t);
			t.setNote(this);
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<NoteTag> getTags() {
		return tags;
	}

	public Notebook getNotebook() {
		return notebook;
	}

	public String getServerContextFile() {
		return serverContextFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public double getScore() {
		return this.score;
	}

	public void addReview(Review review) {
		if (review.getReviewerUser().getUsername().equals(this.getNotebook().getSignedUser().getUsername()))
			throw new IllegalArgumentException("a user cannot review his own notes");

		this.reviews.stream().filter((Review r) -> {
			return r.getReviewerUser().getUsername().equals(review.getReviewerUser().getUsername());
		}).findFirst().ifPresent((Review r) -> {
			this.reviews.remove(r);
		});

		this.reviews.add(review);
		this.updateScore();
	}

	public void updateName(String newName) throws IllegalArgumentException {
		// parameters check
		Optional<Note> optN = this.notebook.getNote(newName);
		if (optN.isPresent() && !newName.equals(name))
			throw new IllegalArgumentException("note" + newName + " already exists");

		// method logic
		this.name = newName;
	}

	public void updateServerContextFile(String serverContextFile) {
		this.serverContextFile = serverContextFile;
	}

	public void updateFilePath(String filePath) {
		this.filePath = filePath;
	}

	private int indexOfTag(String tagName) {
		int index = -1;
		for (int i = 0; i < this.tags.size() && index == -1; i++) {
			if (this.tags.get(i).getName().equals(tagName)) {
				index = i;
			}
		}
		return index;
	}

	public void updateTags(List<NoteTag> tags) {

		// method logic
		this.tags.removeIf((NoteTag t) -> {
			return tags.stream().noneMatch((NoteTag t1) -> {
				return t1.getName().equals(t.getName());
			});
		});
		tags.forEach((NoteTag t1) -> {
			int index = this.indexOfTag(t1.getName());
			if (index != -1) {
				this.tags.get(index).setValue(t1.getValue());
			} else {
				this.tags.add(t1);
				t1.setNote(this);
			}
		});

	}

	public void updateScore() {
		this.score = this.reviews.stream().map(Review::getScore).mapToDouble(Integer::doubleValue).average()
				.getAsDouble();
		this.notebook.updateScore();
	}

	// ---------------------------------------------------------------------------
	// setters should only be used by Hibernate to populate the bean
	// ---------------------------------------------------------------------------

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTags(List<NoteTag> tags) {
		this.tags = tags;
		for (NoteTag t : this.tags) {
			t.setNote(this);
		}
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	public void setServerContextFile(String serverContextFile) {
		this.serverContextFile = serverContextFile;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
