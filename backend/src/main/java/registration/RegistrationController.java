package registration;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.UserState;
import exceptions.OperationFailedException;
import domain.SignedUser;
import hibernate.util.HibernateUtil;

public class RegistrationController implements IRegistrationController {

	@Override
	public void registerUser(String username, String password) throws OperationFailedException {
		if (username == null)
			throw new NullPointerException("username is null");
		if (password == null)
			throw new NullPointerException("password is null");

		// variabili
		Session session = null;
		Transaction tx = null;
		List<SignedUser> retrieved = null;

		try {
			// validazione input
			if (username.length() > 32 || password.length() < 8 || password.length() > 32) {
				throw new OperationFailedException("invalid username or password");
			}

			// interrogazioni all'RDBMS
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			// verifico che l'utente non sia presente nella tabella UTENTI_REGISTRATI
			Query query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);
			retrieved = query.list();
			if (retrieved.size() == 0) {

				SignedUser su = new SignedUser();
				su.setUsername(username);
				su.setHashAlgorithm("SHA-256");
				su.setState(UserState.NOT_REPORTED);
				setPasswordHashAndSalt(su, password, su.getHashAlgorithm());

				session.persist(su);
			} else {
				throw new OperationFailedException("user " + username + " already exists");
			}
			tx.commit();
		} catch (Exception e) {
			// transactioln rollback
			if (tx != null)
				tx.rollback();
			throw new OperationFailedException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private void setPasswordHashAndSalt(SignedUser user, String password, String hashAlgorithm) {
		SecureRandom random = new SecureRandom();

		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);
		System.out.println("salt " + saltBytes);

		byte[] passwordBytes = password.getBytes(StandardCharsets.ISO_8859_1);

		byte[] passwordHashBytes = hashOf(passwordBytes, saltBytes, hashAlgorithm);

		String passwordHash = new String(passwordHashBytes, StandardCharsets.ISO_8859_1);
		String salt = new String(saltBytes, StandardCharsets.ISO_8859_1);
		user.setPasswordHash(passwordHash);
		user.setSalt(salt);
	}

	private byte[] hashOf(byte[] passwordBytes, byte[] saltBytes, String hashAlgorithm) {

		byte[] saltedPasswordBytes = new byte[saltBytes.length + passwordBytes.length];
		System.arraycopy(saltBytes, 0, saltedPasswordBytes, 0, saltBytes.length);
		System.arraycopy(passwordBytes, 0, saltedPasswordBytes, saltBytes.length, passwordBytes.length);

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(hashAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] passwordHashBytes = digest.digest(saltedPasswordBytes);
		return passwordHashBytes;
	}

}
