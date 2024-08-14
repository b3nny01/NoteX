package registration.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.UserType;
import domain.frontend.APIResult;
import exceptions.OperationFailedException;
import registration.RegistrationController;

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private RegistrationController registrationController;
	private Gson gson;

	@Override
	public void init() {
		registrationController = new RegistrationController();
		this.gson = new Gson();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		APIResult result=new APIResult();
		try {
			registrationController.registerUser(username, password);
			result.setOk(true);
		} catch (OperationFailedException e) {
			e.printStackTrace();
			result.setOk(false);
			result.setMsg(e.getMessage());
		}
		response.getWriter().println(this.gson.toJson(result));
	}

}
