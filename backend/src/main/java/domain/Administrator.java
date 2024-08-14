package domain;

import java.io.Serializable;

public class Administrator extends Account implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	
	

	public Administrator() {
		super();
		this.email = "";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
