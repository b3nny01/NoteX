package signedUserHandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import domain.Note;
import domain.Notebook;
import domain.Tag;
import exceptions.OperationFailedException;
import domain.NotebookTag;
import domain.SignedUser;
import filesystem.util.FileController;
import filesystem.util.FileSystemUtil;
import hibernate.util.HibernateUtil;

public class NotebookController implements INotebookController {
	private SessionFactory sessionFactory;
	private FileController fileController;

	public NotebookController() {
		sessionFactory = HibernateUtil.getSessionFactory();
		fileController = FileSystemUtil.getFileController();
	}

	@Override
	public void createNotebook(String username, String notebookName, List<NotebookTag> tags)
			throws OperationFailedException {
		// parameters check
		if (username == null)
			throw new NullPointerException("username is null");
		if (notebookName == null)
			throw new NullPointerException("notebookName is null");
		if (tags == null)
			throw new NullPointerException("tags is null");

		// declaration
		Session session = null;
		Transaction transaction = null;
		Query query = null;

		try {
			// session initialization
			session = sessionFactory.openSession();

			// transaction initialization
			transaction = session.beginTransaction();

			// query initialization
			query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u = retrieved.get(0);

				// creating notebook
				u.createNotebook(notebookName, tags);

				// persisting notebook
				session.persist(u);

				// closing transaction
				transaction.commit();
			} else {
				// throwing exception
				throw new OperationFailedException("user " + username + " does not exist");
			}
		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();

			e.printStackTrace();
			throw new OperationFailedException(e);
		} finally {
			// closing session
			if (session != null)
				session.close();
		}

	}

	@Override
	public List<Notebook> retrieveNotebooks(String username) {

		// parameters check
		if (username == null)
			throw new NullPointerException("username is null");

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
			query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u = retrieved.get(0);

				// retrieving blocchiDiAppunti
				result = u.getNotebooks();

			}
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

	@Override
	public void updateNotebook(String username, String oldNotebookName, String newNotebookName,
			List<NotebookTag> newTags) throws OperationFailedException {
		// parameters check
		if (username == null)
			throw new NullPointerException("username is null");
		if (oldNotebookName == null)
			throw new NullPointerException("oldNotebookName is null");
		if (newNotebookName == null)
			throw new NullPointerException("newNotebookName is null");
		if (newTags == null)
			throw new NullPointerException("newTags is null");

		// declaration
		Session session = null;
		Transaction transaction = null;
		Query query = null;

		try {
			// session initialization
			session = sessionFactory.openSession();

			// transaction initialization
			transaction = session.beginTransaction();

			// query initialization
			query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u = retrieved.get(0);

				Optional<Notebook> optNb = u.getNotebook(oldNotebookName);
				if (optNb.isEmpty())
					throw new OperationFailedException("notebook " + oldNotebookName + " does not exist");

				Notebook nb = optNb.get();

				List<NotebookTag> oldTags = new ArrayList<>(nb.getTags());
				
				// updating notebook
				u.updateNotebook(oldNotebookName, newNotebookName, newTags);

//				for(int i=0;i<oldTags.size();i++) {
//					session.delete(oldTags.get(i));
//				}
				
				// persisting notebook
				session.persist(u);

				// closing transaction
				transaction.commit();
			} else {
				// throwing exception
				throw new OperationFailedException("user " + username + " does not exist");
			}
		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw new OperationFailedException(e);
		} finally {
			// closing session
			if (session != null)
				session.close();
		}
	}

	@Override
	public void deleteNotebook(ServletContext context, String username, String notebookName)
			throws OperationFailedException {

		// parameters check
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (notebookName == null)
			throw new NullPointerException("notebookName is null");

		// declaration
		Session session = null;
		Transaction transaction = null;
		Query query = null;

		try {
			// session initialization
			session = sessionFactory.openSession();

			// transaction initialization
			transaction = session.beginTransaction();

			// query initialization
			query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u = retrieved.get(0);
				Optional<Notebook> optNb = u.getNotebook(notebookName);

				if (optNb.isEmpty())
					throw new OperationFailedException("notebook " + notebookName + " does not exist");

				Notebook nb = optNb.get();

				// getting file paths
				List<String> filePaths = nb.getNotes().stream().map((Note n) -> {
					return n.getFilePath();
				}).collect(Collectors.toList());

				// deleting notebook
				u.deleteNotebook(notebookName);

				// deleting note files
				for (String fp : filePaths) {
					fileController.delete(context, fp);
				}

				// deleting db entry
				session.delete(nb);

				// closing transaction
				transaction.commit();

			} else {
				// throwing exception
				throw new OperationFailedException("user " + username + " does not exist");
			}

		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw new OperationFailedException(e);
		} finally {
			// closing session
			if (session != null)
				session.close();
		}

	}

}
