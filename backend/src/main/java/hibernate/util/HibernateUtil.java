package hibernate.util;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import domain.Tag;

public class HibernateUtil {

	public static class QueryRepo {
		
		public final static String RETRIEVE_SIGNED_USER_BY_USERNAME = "SELECT * FROM SIGNED_USERS SU WHERE SU.Username = ? AND SU.UserState != 'SHADOWED' ";
		
		public final static String RETRIEVE_SIGNED_USER_BY_STR = "SELECT * FROM SIGNED_USERS SU WHERE SU.Username LIKE ? ";
		public final static String RETRIEVE_NOTEBOOK_BY_STR = "SELECT * FROM NOTEBOOKS NB WHERE NB.Name LIKE ? ";
		public final static String RETRIEVE_NOTE_BY_STR = "SELECT * FROM NOTES N WHERE N.Name LIKE ? ";

		public static String buildRetrieveNotebookString(List<Tag> tags) {
			String result = RETRIEVE_NOTEBOOK_BY_STR;

			for (Tag t : tags) {
				result += " AND EXISTS (SELECT * FROM NOTEBOOK_TAGS T WHERE T.NotebookId = NB.Id AND T.Name = '"
						+ t.getName() + "' AND T.Value = '" + t.getValue() + "') ";
			}
			return result;

		}
		
		public static String buildRetrieveNoteStr(List<Tag> tags) {
			String result = HibernateUtil.QueryRepo.RETRIEVE_NOTE_BY_STR;

			for (Tag t : tags) {
				result += " AND EXISTS (SELECT * FROM NOTE_TAGS T WHERE T.NoteId = N.Id AND T.Name = '"
						+ t.getName() + "' AND T.Value = '" + t.getValue() + "' ) ";
			}
			return result;
		}
		
	}

	private static SessionFactory sessionFactory = initHibernateUtil();

	private static SessionFactory initHibernateUtil() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
