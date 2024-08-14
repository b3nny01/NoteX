package signedUserHandling;

import java.util.Optional;

import domain.SignedUser;
import exceptions.OperationFailedException;

public interface ISignedUserController {
	public void changePassword(String username,String oldPassword, String newPassword) throws OperationFailedException;
	public Optional<SignedUser> retrieveSignedUser(String username);
}
