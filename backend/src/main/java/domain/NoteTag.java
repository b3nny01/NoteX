package domain;

import java.io.Serializable;

public class NoteTag extends Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Note note;
	public NoteTag() {
		super();
		this.note=new Note();
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	
	
	

}
