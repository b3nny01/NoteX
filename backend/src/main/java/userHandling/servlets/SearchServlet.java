package userHandling.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import domain.Notebook;
import domain.Tag;
import domain.frontend.APIResult;
import domain.frontend.NoteFrontend;
import domain.frontend.NotebookFrontend;
import domain.frontend.SearchResultFrontend;
import domain.frontend.TagFrontend;
import domain.frontend.UserFrontend;
import filesystem.util.FileController;
import filesystem.util.FileSystemUtil;
import filesystem.util.IllegalFileTypeException;
import hibernate.util.HibernateUtil;
import signedUserHandling.SignedUserController;
import userHandling.ISearchController;
import userHandling.SearchController;

public class SearchServlet extends HttpServlet {

	private ISearchController searchController;
	private Gson gson;
	private ServletContext servletContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		this.searchController = new SearchController();
		this.gson = new Gson();
		this.servletContext = this.getServletContext();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String searchType = req.getParameter("searchType");
		if (searchType != null) {
			if (searchType.equals("signedUsers")) {
				try {
					// parsing
					String searchText = req.getParameter("searchText");

					// retrieving result
					APIResult result = new APIResult();
					SearchResultFrontend searchResult = new SearchResultFrontend();
					searchResult.setType(searchType);
					searchResult.setResults(searchController.signedUserSearch(searchText).stream().map(u -> {
						return new UserFrontend(u);
					}).collect(Collectors.toList()));

					result.setOk(true);
					result.setObj(searchResult);
					res.getWriter().print(gson.toJson(result));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (searchType.equals("notebooks")) {
				try {
					// parsing
					String searchText = req.getParameter("searchText");
					TagFrontend[] tagsArray = gson.fromJson(req.getParameter("tags"), TagFrontend[].class);
					List<Tag> tags = new ArrayList<>();
					for (TagFrontend t : tagsArray) {
						Tag tag = new Tag();
						tag.setName(t.getName());
						tag.setValue(t.getValue());
						tags.add(tag);
					}

					// retrieving result
					APIResult result = new APIResult();
					SearchResultFrontend searchResult = new SearchResultFrontend();
					searchResult.setType(searchType);
					searchResult.setResults(searchController.notebookSearch(searchText, tags).stream().map(b -> {
						return new NotebookFrontend(b);
					}).collect(Collectors.toList()));

					result.setOk(true);
					result.setObj(searchResult);
					res.getWriter().print(gson.toJson(result));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (searchType.equals("notes")) {
				try {
					// parsing
					String searchText = req.getParameter("searchText");
					TagFrontend[] tagsArray = gson.fromJson(req.getParameter("tags"), TagFrontend[].class);
					List<Tag> tags = new ArrayList<>();
					for (TagFrontend t : tagsArray) {
						Tag tag = new Tag();
						tag.setName(t.getName());
						tag.setValue(t.getValue());
						tags.add(tag);
					}

					// retrieving result
					APIResult result = new APIResult();
					SearchResultFrontend searchResult = new SearchResultFrontend();
					searchResult.setType(searchType);
					searchResult.setResults(searchController.noteSearch(searchText, tags).stream().map(n -> {
						return new NoteFrontend(n);
					}).collect(Collectors.toList()));
					
					result.setOk(true);
					result.setObj(searchResult);
					res.getWriter().print(gson.toJson(result));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}