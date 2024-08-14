package signedUserHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ibm.db2.jcc.am.n;

import domain.Notebook;
import domain.NoteTag;
import domain.Note;
import domain.SignedUser;
import exceptions.OperationFailedException;
import filesystem.util.FileController;
import filesystem.util.FileSystemUtil;
import hibernate.util.HibernateUtil;

public class NoteController implements INoteController {

	private SessionFactory sessionFactory;
	private FileController fileController;

	public NoteController() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
		this.fileController = FileSystemUtil.getFileController();
	}

	@Override
	public void createNote(String server, ServletContext context, String username, String notebookName, String noteName,
			List<NoteTag> tags, Part file) throws OperationFailedException {
		// parameters check
		if (server == null)
			throw new NullPointerException("server is null");
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (notebookName == null)
			throw new NullPointerException("notebookName is null");
		if (noteName == null)
			throw new NullPointerException("noteName is null");
		if (tags == null)
			throw new NullPointerException("tags is null");
		if (file == null)
			throw new NullPointerException("file is null");

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
					throw new OperationFailedException(notebookName + " does not exists");

				Notebook nb = optNb.get();

				// persiting fileDiAppunti
				String serverContextFile = server + "/" + context.getContextPath();
				String filePath = fileController.persist(context, file);
				// creating nota
				try {
					nb.createNote(noteName, serverContextFile, filePath, tags);
				} catch (IllegalArgumentException e) {
					fileController.delete(context, filePath);
					throw e;
				}
				// persisting nota
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
	public void updateNote(ServletContext context, String username, String notebookName, String oldNoteName,
			String newNoteName, List<NoteTag> newTags, Optional<Part> newFile) throws OperationFailedException {
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (notebookName == null)
			throw new NullPointerException("notebookName is null");
		if (oldNoteName == null)
			throw new NullPointerException("oldNoteName is null");
		if (newNoteName == null)
			throw new NullPointerException("newNoteName is null");
		if (newTags == null)
			throw new NullPointerException("newTags is null");
		if (newFile == null)
			throw new NullPointerException("newFile is null");

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
					throw new OperationFailedException(notebookName + " does not exist");

				Notebook nb = optNb.get();

				Optional<Note> optN = nb.getNote(oldNoteName);

				if (optN.isEmpty())
					throw new OperationFailedException("note " + newNoteName + " does not exist");

				Note n = optN.get();

				String oldFile = n.getFilePath();

				nb.updateNote(oldNoteName, newNoteName, newTags);

				if (newFile.isPresent()) {
					fileController.replace(context, n.getFilePath(), newFile.get());
				}

				// persisting nota
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
	public List<Note> retrieveNotes(String username, String notebookName) {
		// parameter check
		if (username == null)
			throw new NullPointerException("username is null");
		if (notebookName == null)
			throw new NullPointerException("notebookName is null");

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
			query = session.createSQLQuery(HibernateUtil.QueryRepo.RETRIEVE_SIGNED_USER_BY_USERNAME)
					.addEntity(SignedUser.class);
			query.setParameter(0, username);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u = retrieved.get(0);
				Optional<Notebook> nb = u.getNotebook(notebookName);

				if (nb.isPresent()) {
					// retrieving notes
					result = nb.get().getNotes();
				}
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
	public void deleteNote(ServletContext context, String username, String notebookName, String noteName)
			throws OperationFailedException {
		// parameter check
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (notebookName == null)
			throw new NullPointerException("notebookName is null");
		if (noteName == null)
			throw new NullPointerException("noteName is null");

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
				Optional<Note> optN = nb.getNote(noteName);

				if (optN.isEmpty())
					throw new OperationFailedException("note " + noteName + " does note exist");

				Note n = optN.get();

				// getting file path
				String filePath = n.getFilePath();

				// deleting note
				nb.deleteNote(noteName);

				// deleting note file
				fileController.delete(context, filePath);

				// deleting db entry
				session.delete(optN.get());

			} else {
				// throwing exception
				throw new OperationFailedException("user " + username + " does not exist");
			}

			// closing transaction
			transaction.commit();
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
