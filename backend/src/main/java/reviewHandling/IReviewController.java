package reviewHandling;

import java.util.List;
import java.util.Optional;

import domain.Review;
import exceptions.OperationFailedException;

public interface IReviewController {
	public boolean sendReview(String usernameRecensore, String usernameNotaRecensita,
			String bloccoDiAppuntiNotaRecensita, String notaRecensita, int punteggio, Optional<String> testo)
			throws OperationFailedException;

	public List<Review> allReviewsOf(String usernameNotaRecensita, String bloccoDiAppuntiNotaRecensita,
			String notaRecensita);
}
