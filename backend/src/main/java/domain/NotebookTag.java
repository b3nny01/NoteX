package domain;

import java.io.Serializable;
import java.util.Objects;

public class NotebookTag extends Tag implements Serializable  {

	/**
	 * 
	 */
	private Notebook notebook;
	private static final long serialVersionUID = 1L;
	public NotebookTag() {
		super();
		this.notebook=new Notebook();
	}
	public Notebook getNotebook() {
		return notebook;
	}
	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}	
	
}
