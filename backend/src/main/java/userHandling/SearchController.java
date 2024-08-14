package userHandling;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import domain.Notebook;
import domain.Tag;
import domain.Note;
import domain.SignedUser;
import filesystem.util.FileController;
import filesystem.util.FileSystemUtil;
import hibernate.util.HibernateUtil;

public class SearchController implements ISearchController {

	private SessionFactory sessionFactory;

	public SearchController() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public List<SignedUser> signedUserSearch(String searchText) {
		if (searchText == null)
			throw new NullPointerException("searchText is null");

		// declaration
		List<SignedUser> result = new ArrayList<>();
		Session session = null;
		Transaction transaction = null;
		Query query = null;

		try {
			// session initialization
			session = sessionFactory.openSession();

			// transaction initialization
			transaction = session.beginTransaction();

			// query initialization
			searchText += "%";
			query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_STR)
					.addEntity(SignedUser.class);
			query.setParameter(0, searchText);

			// retrieving results
			result = query.list();

			// closing transaction
			transaction.commit();
		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			// closing session
			if (session != null)
				session.close();
		}
		// return
		return result;
	}

	public List<Notebook> notebookSearch(String searchText, List<Tag> tags) {
		if (searchText == null)
			throw new NullPointerException("searchText is null");
		if (tags == null)
			throw new NullPointerException("tags is null");

		// declaration
		List<Notebook> result = new ArrayList<>();
		Session session = null;
		Transaction transaction = null;
		Query query = null;

		try {
			// session initialization
			session = sessionFactory.openSession();

			// transaction initialization
			transaction = session.beginTransaction();

			// query initialization
			searchText += "%";
			query = session.createSQLQuery(HibernateUtil.QueryRepo.buildRetrieveNotebookString(tags))
					.addEntity(Notebook.class);
			query.setParameter(0, searchText);

			// retrieving results
			result = query.list();

			// closing transaction
			transaction.commit();
		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {

			// closing session
			if (session != null)
				session.close();
		}
		// return
		return result;
	}

	public List<Note> noteSearch(String searchText, List<Tag> tags) {
		if (searchText == null)
			throw new NullPointerException("searchText is null");
		if (tags == null)
			throw new NullPointerException("tags is null");

		// declaration
		List<Note> result = new ArrayList<>();
		Session session = null;
		Transaction transaction = null;
		Query query = null;

		try {
			// session initialization
			session = sessionFactory.openSession();

			// transaction initialization
			transaction = session.beginTransaction();

			// query initialization
			searchText += "%";
			query = session.createSQLQuery(HibernateUtil.QueryRepo.buildRetrieveNoteStr(tags)).addEntity(Note.class);
			query.setParameter(0, searchText);

			// retrieving results
			result = query.list();

			// closing transaction
			transaction.commit();
		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			// closing session
			if (session != null)
				session.close();
		}
		// return
		return result;
	}

}
