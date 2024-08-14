package domain.frontend;

import domain.Review;

public class ReviewFrontend {

	private String reviewerUsername;
	private String reviewedUsername;
	private String reviewedNotebookname;
	private String reviewedNoteName;
	private int score;
	private String text;

	public ReviewFrontend(Review recensione) {
		this.reviewerUsername = recensione.getReviewerUser().getUsername();
		this.reviewedNoteName = recensione.getReviewedNote().getName();
		this.reviewedNotebookname = recensione.getReviewedNote().getNotebook().getName();
		this.reviewedUsername = recensione.getReviewedNote().getNotebook().getSignedUser()
				.getUsername();
		this.score = recensione.getScore();
		this.text = recensione.getText();
	}
	
	

	public ReviewFrontend() {
		super();
		this.reviewerUsername = "";
		this.reviewedUsername = "";
		this.reviewedNotebookname = "";
		this.reviewedNoteName = "";
		this.score = -1;
		this.text = null;
	}



	public String getReviewerUsername() {
		return reviewerUsername;
	}

	public void setReviewerUsername(String reviewerUsername) {
		this.reviewerUsername = reviewerUsername;
	}

	public String getReviewedUsername() {
		return reviewedUsername;
	}

	public void setReviewedUsername(String reviewedUsername) {
		this.reviewedUsername = reviewedUsername;
	}

	public String getReviewedNotebookname() {
		return reviewedNotebookname;
	}

	public void setReviewedNotebookname(String reviewedNotebookname) {
		this.reviewedNotebookname = reviewedNotebookname;
	}

	public String getReviewedNoteName() {
		return reviewedNoteName;
	}

	public void setReviewedNoteName(String reviewedNoteName) {
		this.reviewedNoteName = reviewedNoteName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

}
