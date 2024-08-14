package loginAndAuth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.UserType;
import domain.SignedUser;
import hibernate.util.HibernateUtil;

public class LoginController implements ILoginController {

	@Override
	public boolean isAuthed(UserType userType, String username, String password) {
		boolean result = false;

		// input validation
		if (username.length() > 32 || password.length() < 8 || password.length() > 32 || userType == null) {
			return false;
		}

		// variables
		Session session = null;
		Transaction tx = null;
		List<SignedUser> retrieved = new ArrayList<>();

		// RDBMS query
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		if (userType.equals(UserType.SIGNED_USER)) {
			Query query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);
			retrieved = query.list();
			if (retrieved.size() == 1) {
				SignedUser su = retrieved.get(0);
				byte[] passwordBytes = password.getBytes(StandardCharsets.ISO_8859_1);
				byte[] saltBytes = su.getSalt().getBytes(StandardCharsets.ISO_8859_1);
				byte[] passwordHashBytes = hashOf(passwordBytes, saltBytes, su.getHashAlgorithm());
				String passwordHash = new String(passwordHashBytes, StandardCharsets.ISO_8859_1);
				if (passwordHash.equals(su.getPasswordHash())) {
					result = true;
				}
			}
		}

		tx.commit();
		session.close();
		return result;
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
