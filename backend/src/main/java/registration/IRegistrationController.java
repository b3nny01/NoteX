package registration;

import exceptions.OperationFailedException;

public interface IRegistrationController {

	public void registerUser(String username, String password) throws OperationFailedException;
	
}
