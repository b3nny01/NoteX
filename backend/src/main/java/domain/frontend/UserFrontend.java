package domain.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.SignedUser;

public class UserFrontend {
	private String username;
	private List<NotebookFrontend> notebooks;
	
	public UserFrontend(SignedUser u)
	{
		this.username=u.getUsername();
		this.notebooks=u.getNotebooks().stream().map(b->{return new NotebookFrontend(b);}).collect(Collectors.toList());
	}
	
	

	public UserFrontend() {
		super();
		this.username = "";
		this.notebooks = new ArrayList<>();
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<NotebookFrontend> getNotebooks() {
		return notebooks;
	}

	public void setNotebooks(List<NotebookFrontend> notebooks) {
		this.notebooks = notebooks;
	}
	
	
	
	

}
