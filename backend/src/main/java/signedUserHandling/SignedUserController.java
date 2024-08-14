package signedUserHandling;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import domain.Notebook;
import domain.Tag;
import exceptions.OperationFailedException;
import domain.NotebookTag;
import domain.NoteTag;
import domain.Note;
import domain.SignedUser;
import filesystem.util.FileController;
import filesystem.util.FileSystemUtil;
import hibernate.util.HibernateUtil;

public class SignedUserController implements INotebookController, INoteController, ISignedUserController {

	private INoteController noteController;
	private INotebookController notebookController;
	private SessionFactory sessionFactory;
	private FileController fileController;

	public SignedUserController() {
		super();
		this.noteController = new NoteController();
		this.notebookController = new NotebookController();
		this.sessionFactory = HibernateUtil.getSessionFactory();
		this.fileController = FileSystemUtil.getFileController();
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
	}

	// --------------------------------------------------------------------------------------------------------------
	// Note CRUD methods
	// --------------------------------------------------------------------------------------------------------------
	@Override
	public void createNote(String server, ServletContext context, String username, String notebookName, String noteName,
			List<NoteTag> tags, Part file) throws OperationFailedException {
		this.noteController.createNote(server, context, username, notebookName, noteName, tags, file);
	}

	@Override
	public List<Note> retrieveNotes(String username, String notebookName) {
		return this.noteController.retrieveNotes(username, notebookName);
	}

	@Override
	public void updateNote(ServletContext context, String username, String notebookName, String oldNoteName,
			String newNoteName, List<NoteTag> newTags, Optional<Part> newFile) throws OperationFailedException {
		this.noteController.updateNote(context, username, notebookName, oldNoteName, newNoteName, newTags, newFile);
	}

	@Override
	public void deleteNote(ServletContext context, String username, String notebookName, String noteName)
			throws OperationFailedException {
		this.noteController.deleteNote(context, username, notebookName, noteName);
	}

	// --------------------------------------------------------------------------------------------------------------
	// Notebook CRUD methods
	// --------------------------------------------------------------------------------------------------------------

	@Override
	public void createNotebook(String username, String notebookName, List<NotebookTag> tags)
			throws OperationFailedException {
		this.notebookController.createNotebook(username, notebookName, tags);
	}

	@Override
	public List<Notebook> retrieveNotebooks(String username) {
		return this.notebookController.retrieveNotebooks(username);
	}

	@Override
	public void updateNotebook(String username, String oldNotebookName, String newNotebookName,
			List<NotebookTag> newTags) throws OperationFailedException {
		this.notebookController.updateNotebook(username,oldNotebookName,newNotebookName,newTags);

	}

	@Override
	public void deleteNotebook(ServletContext context, String username, String notebookName)
			throws OperationFailedException {
		this.notebookController.deleteNotebook(context, username, notebookName);
	}

	@Override
	public Optional<SignedUser> retrieveSignedUser(String username) {
		// parameter check
		if (username == null)
			throw new NullPointerException("username is null");

		// declaration
		Optional<SignedUser> result = Optional.empty();
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
				result = Optional.of(retrieved.get(0));
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

}
