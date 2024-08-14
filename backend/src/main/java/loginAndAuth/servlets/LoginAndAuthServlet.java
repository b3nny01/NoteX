package loginAndAuth.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import domain.UserSession;
import domain.UserType;
import domain.frontend.APIResult;
import loginAndAuth.LoginController;

public class LoginAndAuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LoginController loginController;
	private Gson gson;

	@Override
	public void init() {
		loginController = new LoginController();
		this.gson = new Gson();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// retrieving userSession
		UserSession userSession = null;
		HttpSession session = req.getSession();
		synchronized (session) {
			userSession = (UserSession) session.getAttribute("userSession");
			if (userSession == null) {
				userSession = new UserSession();
				session.setAttribute("userSession", userSession);
			}
		}

		// parsing
		String action = req.getParameter("action");
		// logic
		if (action != null) {
			if (action.equals("signedUserLogin")) {
				try {
					// parsing
					String username = req.getParameter("username");
					String password = req.getParameter("password");

					// autentication and session creation
					UserSession newUserSession = new UserSession();
					APIResult result = new APIResult();
					boolean logged = loginController.isAuthed(UserType.SIGNED_USER, username, password);
					if (logged) {
						newUserSession.setUsername(username);
						newUserSession.setUserType(UserType.SIGNED_USER);
						newUserSession.setAuthed(true);
						result.setOk(true);
					} else {
						result.setOk(false);
						result.setMsg("auth failed");
					}

					// setting userSession
					synchronized (session) {
						session.setAttribute("userSession", newUserSession);
					}
					res.getWriter().println(this.gson.toJson(result));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (action.equals("getSession")) {
				try {
					APIResult result = new APIResult();
					result.setOk(true);
					result.setObj(userSession);
					res.getWriter().println(this.gson.toJson(result));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (action.equals("logout")) {
				try {
					// autentication and session creation
					UserSession newUserSession = new UserSession();
					APIResult result = new APIResult();

					// setting userSession
					synchronized (session) {
						session.setAttribute("userSession", newUserSession);
					}
					result.setOk(true);
					res.getWriter().println(this.gson.toJson(result));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
