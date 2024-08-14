package domain;

import java.io.Serializable;
import java.util.Optional;

public class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int score;
	private String text;
	private SignedUser reviewerUser;
	private Note reviewedNote;
	
	
	
	public Review() {
		super();
		this.id = -1;
		this.score = -1;
		this.text = "";
		this.reviewerUser = new SignedUser();
		this.reviewedNote = new Note();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int punteggio) {
		this.score = punteggio;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String commento) {
		this.text = commento;
	}
	
	public SignedUser getReviewerUser() {
		return reviewerUser;
	}
	
	public void setReviewerUser(SignedUser recensore) {
		this.reviewerUser = recensore;
	}
	
	public Note getReviewedNote() {
		return reviewedNote;
	}
	
	public void setReviewedNote(Note notaRecensita) {
		this.reviewedNote = notaRecensita;
	}
	
}
