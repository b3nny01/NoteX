package domain;

public class UserSession {
	private String username;
	private boolean authed;
	private UserType userType;
	public UserSession() {
		super();
		this.username="";
		this.authed=false;
		this.userType=UserType.USER;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAuthed() {
		return authed;
	}
	public void setAuthed(boolean authed) {
		this.authed = authed;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	

}
