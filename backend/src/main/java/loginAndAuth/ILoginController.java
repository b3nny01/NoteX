package loginAndAuth;

import domain.UserType;

public interface ILoginController {

	public boolean isAuthed(UserType userType, String username, String password);
	
}
