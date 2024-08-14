package reviewHandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import domain.Notebook;
import domain.Note;
import domain.Review;
import domain.SignedUser;
import exceptions.OperationFailedException;
import hibernate.util.HibernateUtil;

public class ReviewController implements IReviewController {
	private SessionFactory sessionFactory;

	public ReviewController() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}

	@Override
	public boolean sendReview(String reviewerUsername, String reviewedUsername, String reviewedNotebook,
			String reviewedNote, int score, Optional<String> text) throws OperationFailedException {
		// parameter check
		if (reviewerUsername == null)
			throw new NullPointerException("reviewerUsername is null");
		if (reviewedUsername == null)
			throw new NullPointerException("reviewedUsername is null");
		if (reviewedNotebook == null)
			throw new NullPointerException("reviewedNotebook is null");
		if (reviewedNote == null)
			throw new NullPointerException("reviewedNote is null");
		if (score < 0 || score > 5)
			throw new IllegalArgumentException("score is not valid");
		if (text == null)
			throw new NullPointerException("text is null");

		// declaration
		boolean result = false;
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

			// retrieving reviewer user
			query.setParameter(0, reviewerUsername);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u1 = retrieved.get(0);

				// retrieving reviewed user
				query.setParameter(0, reviewedUsername);

				// retrieving results
				retrieved = query.list();

				if (retrieved.size() == 1) {
					SignedUser u2 = retrieved.get(0);

					// retrieving notebook
					Optional<Notebook> optNb = u2.getNotebook(reviewedNotebook);
					if (optNb.isEmpty())
						throw new OperationFailedException("notebook " + reviewedNotebook + " does not exist");

					Notebook nb = optNb.get();

					Optional<Note> optN = nb.getNote(reviewedNote);
					if (optN.isEmpty())
						throw new OperationFailedException("note " + reviewedNote + " does not exist");

					Note n = optN.get();

					Review r = new Review();
					r.setReviewerUser(u1);
					r.setReviewedNote(n);
					r.setScore(score);
					r.setText(text.orElse(null));

					n.addReview(r);

					// persisting review
					session.persist(r);

					// closing transaction
					transaction.commit();

				} else {
					throw new OperationFailedException("user " + reviewedUsername + " does not exist");
				}
			} else {
				throw new OperationFailedException("user " + reviewerUsername + " does not exist");
			}

		} catch (Exception e) {
			// transactioln rollback
			if (transaction != null)
				transaction.rollback();
			throw new OperationFailedException(e);
		} finally {
			// closing session
			if (session != null)
				session.close();
		}
		// return
		return result;
	}

	@Override
	public List<Review> allReviewsOf(String reviewedUsername, String reviewedNotebook, String reviewedNote) {
		// parameter check
		if (reviewedUsername == null)
			throw new NullPointerException("reviewedUsername is null");
		if (reviewedNotebook == null)
			throw new NullPointerException("reviewedNotebook is null");
		if (reviewedNote == null)
			throw new NullPointerException("reviewedNote is null");

		// declaration
		List<Review> result = new ArrayList<>();
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

			// retrieving user
			query.setParameter(0, reviewedUsername);

			// retrieving results
			List<SignedUser> retrieved = query.list();

			if (retrieved.size() == 1) {
				SignedUser u = retrieved.get(0);

				// retrieving notebook
				Optional<Notebook> optNb = u.getNotebook(reviewedNotebook);
				if (optNb.isPresent()) {
					// retrieving note
					Optional<Note> n = optNb.get().getNote(reviewedNote);

					if (n.isPresent()) {
						result = n.get().getReviews();
					}

				}
			}

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
