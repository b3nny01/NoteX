package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Notebook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<NotebookTag> tags;
	private SignedUser signedUser;
	private List<Note> notes;
	private double score;

	public Notebook() {
		super();
		this.id = -1;
		this.name = "";
		this.tags = new ArrayList<>();
		this.signedUser = new SignedUser();
		this.notes = new ArrayList<>();
		this.score = 0;
	}

	public Notebook(String name, SignedUser signedUser, List<NotebookTag> tags) {
		this();
		this.name = name;
		this.signedUser = signedUser;
		this.updateTags(tags);

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<NotebookTag> getTags() {
		return tags;
	}

	public SignedUser getSignedUser() {
		return signedUser;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public double getScore() {
		return this.score;
	}

	public void createNote(String noteName, String serverContextFile, String filePath, List<NoteTag> tags)
			throws IllegalArgumentException {
		// parameters check
		Optional<Note> optN = this.getNote(noteName);
		if (optN.isPresent())
			throw new IllegalArgumentException("note" + noteName + " already exists");

		// method logic
		Note nota = new Note(noteName, this, serverContextFile, filePath, tags);
		this.notes.add(nota);
	}

	public Optional<Note> getNote(String noteName) {
		return this.notes.stream().filter(n -> {
			return n.getName().equals(noteName);
		}).findFirst();
	}

	public void updateNote(String oldName, String newName, List<NoteTag> newTags) throws IllegalArgumentException {
		// parameters check
		Optional<Note> optN = this.getNote(oldName);
		if (optN.isEmpty())
			throw new IllegalArgumentException("note" + oldName + " does not exist");

		// method logic
		Note n = optN.get();
		n.updateName(newName);
		n.updateTags(newTags);
	}
	
	public void updateNote(String oldName, String newName, String newServerContextFile, String newFilePath, List<NoteTag> newTags) throws IllegalArgumentException {
		// parameters check
		Optional<Note> optN = this.getNote(oldName);
		if (optN.isEmpty())
			throw new IllegalArgumentException("note" + oldName + " does not exist");

		// method logic
		Note n = optN.get();
		n.updateName(newName);
		n.updateServerContextFile(newServerContextFile);
		n.updateFilePath(newFilePath);
		n.updateTags(newTags);
	}

	public void deleteNote(String noteName) throws IllegalArgumentException {
		// parameters check
		Optional<Note> optN = this.getNote(noteName);
		if (optN.isEmpty())
			throw new IllegalArgumentException("note " + noteName + " does not exists");

		// method logic
		Note n = optN.get();
		this.notes.remove(n);
	}

	public void updateName(String newName) throws IllegalArgumentException {
		// parameters check
		Optional<Notebook> optNb = this.signedUser.getNotebook(newName);
		if (optNb.isPresent() && !newName.equals(name))
			throw new IllegalArgumentException("notebook " + newName + " already exists");

		// method logic
		this.name = newName;
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

	public void updateTags(List<NotebookTag> tags) {

		// method logic
		this.tags.removeIf((NotebookTag t) -> {
			return tags.stream().noneMatch((NotebookTag t1) -> {
				return t1.getName().equals(t.getName());
			});
		});
		tags.forEach((NotebookTag t1) -> {
			int index = this.indexOfTag(t1.getName());
			if (index != -1) {
				this.tags.get(index).setValue(t1.getValue());
			} else {
				this.tags.add(t1);
				t1.setNotebook(this);
			}
		});

	}

	public void updateScore() {
		this.score=this.notes.stream().map(Note::getScore).filter(d->{return d>=0;}).mapToDouble(Double::doubleValue).average().orElse(0);
		this.signedUser.updateScore();
	}
	
	@Override
	public String toString() {
		return "Notebook [name=" + name + "]";
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

	public void setTags(List<NotebookTag> tags) {
		this.tags = tags;
	}

	public void setSignedUser(SignedUser signedUser) {
		this.signedUser = signedUser;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
