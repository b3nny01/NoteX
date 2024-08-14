package reviewHandling.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import domain.Notebook;
import domain.Tag;
import domain.UserSession;
import domain.frontend.APIResult;
import domain.frontend.NoteFrontend;
import domain.frontend.NotebookFrontend;
import domain.frontend.ReviewFrontend;
import domain.frontend.UserFrontend;
import exceptions.OperationFailedException;
import filesystem.util.FileController;
import filesystem.util.FileSystemUtil;
import filesystem.util.IllegalFileTypeException;
import hibernate.util.HibernateUtil;
import reviewHandling.IReviewController;
import reviewHandling.ReviewController;
import signedUserHandling.SignedUserController;

public class ReviewServlet extends HttpServlet {

	private IReviewController reviewController;
	private Gson gson;
	private ServletContext servletContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		this.reviewController = new ReviewController();
		this.gson = new Gson();
		this.servletContext = this.getServletContext();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		UserSession userSession = null;
		HttpSession session = req.getSession();
		synchronized (session) {
			userSession = (UserSession) session.getAttribute("userSession");
			if (userSession == null) {
				userSession = new UserSession();
				session.setAttribute("userSession", userSession);
			}
		}
		if (userSession.isAuthed()) {
			String action = req.getParameter("action");
			if (action != null) {
				if (action.equals("sendReview")) {
					try {
						String reviewedUsername = req.getParameter("reviewedUsername");
						String reviewedNotebookName = req.getParameter("reviewedNotebookName");
						String reviewedNoteName = req.getParameter("reviewedNoteName");
						int score = Integer.parseInt(req.getParameter("score"));
						Optional<String> text = Optional.ofNullable(req.getParameter("text"));

						APIResult result = new APIResult();
						try {
							reviewController.sendReview(userSession.getUsername(), reviewedUsername,
									reviewedNotebookName, reviewedNoteName, score, text);
							result.setOk(true);
						} catch (OperationFailedException e) {
							e.printStackTrace();
							result.setOk(false);
							result.setMsg(e.getMessage());
						}
						res.getWriter().print(gson.toJson(result));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				APIResult result = new APIResult();
				result.setOk(false);
				result.setMsg("auth error");
				res.getWriter().print(gson.toJson(result));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		UserSession userSession = null;
		HttpSession session = req.getSession();
		synchronized (session) {
			userSession = (UserSession) session.getAttribute("userSession");
			if (userSession == null) {
				userSession = new UserSession();
				session.setAttribute("userSession", userSession);
			}
		}
		String action = req.getParameter("action");
		if (action != null) {
			if (action.equals("retrieveReviews")) {
				try {
					String reviewedUsername = req.getParameter("reviewedUsername");
					String reviewedNotebookName = req.getParameter("reviewedNotebookName");
					String reviewedNoteName = req.getParameter("reviewedNoteName");

					APIResult result = new APIResult();
					List<ReviewFrontend> reviews = reviewController
							.allReviewsOf(reviewedUsername, reviewedNotebookName, reviewedNoteName).stream().map(r -> {
								return new ReviewFrontend(r);
							}).collect(Collectors.toList());

					result.setOk(true);
					result.setObj(reviews);
					res.getWriter().print(gson.toJson(result));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}