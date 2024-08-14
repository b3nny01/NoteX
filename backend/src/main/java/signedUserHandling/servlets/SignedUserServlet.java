package signedUserHandling.servlets;

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

import domain.NotebookTag;
import domain.NoteTag;
import domain.UserSession;
import domain.frontend.NoteFrontend;
import domain.frontend.NotebookFrontend;
import domain.frontend.APIResult;
import domain.frontend.TagFrontend;
import exceptions.OperationFailedException;
import signedUserHandling.SignedUserController;

public class SignedUserServlet extends HttpServlet {

	private SignedUserController signedUserController;
	private Gson gson;
	private ServletContext servletContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		this.signedUserController = new SignedUserController();
		this.gson = new Gson();
		this.servletContext = this.getServletContext();
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse res) {
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
				if (action.equals("updateNotebook")) {
					try {
						// parsing
						String oldNotebookName = req.getParameter("oldNotebookName");
						String newNotebookName = req.getParameter("newNotebookName");
						TagFrontend[] newTagsArray = gson.fromJson(req.getParameter("newTags"), TagFrontend[].class);
						List<NotebookTag> newTags = new ArrayList<>();
						for (TagFrontend t : newTagsArray) {
							NotebookTag tag = new NotebookTag();
							tag.setName(t.getName());
							tag.setValue(t.getValue());
							newTags.add(tag);
						}

						// notebook creation
						APIResult result = new APIResult();

						try {
							signedUserController.updateNotebook(userSession.getUsername(), oldNotebookName,
									newNotebookName, newTags);
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
				if (action.equals("updateNote")) {
					try {
						// parsing
						String notebookName = req.getParameter("notebookName");
						String oldNoteName = req.getParameter("oldNoteName");
						String newNoteName = req.getParameter("newNoteName");
						TagFrontend[] newTagsArray = gson.fromJson(req.getParameter("newTags"), TagFrontend[].class);
						List<NoteTag> newTags = new ArrayList<>();
						for (TagFrontend t : newTagsArray) {
							NoteTag tag = new NoteTag();
							tag.setName(t.getName());
							tag.setValue(t.getValue());
							newTags.add(tag);
						}

						// notebook creation
						APIResult result = new APIResult();

						try {
							Optional<Part> newFile = Optional.ofNullable(req.getPart("newFile"));
							signedUserController.updateNote(servletContext, userSession.getUsername(), notebookName,
									oldNoteName, newNoteName, newTags, newFile);
							result.setOk(true);
						} catch (OperationFailedException e) {
							e.printStackTrace();
							result.setOk(false);
							result.setMsg(e.getMessage());
						} catch (ServletException e) {
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
				if (action.equals("createNotebook")) {
					try {
						// parsing
						String notebookName = req.getParameter("notebookName");
						TagFrontend[] tagsArray = gson.fromJson(req.getParameter("tags"), TagFrontend[].class);
						List<NotebookTag> tags = new ArrayList<>();
						for (TagFrontend t : tagsArray) {
							NotebookTag tag = new NotebookTag();
							tag.setName(t.getName());
							tag.setValue(t.getValue());
							tags.add(tag);
						}

						// notebook creation
						APIResult result = new APIResult();

						try {
							signedUserController.createNotebook(userSession.getUsername(), notebookName, tags);
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

				if (action.equals("createNote")) {
					try {
						// parsing
						String server = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
						String notebookName = req.getParameter("notebookName");
						String noteName = req.getParameter("noteName");
						TagFrontend[] tagsArray = gson.fromJson(req.getParameter("tags"), TagFrontend[].class);
						List<NoteTag> tags = new ArrayList<>();
						for (TagFrontend t : tagsArray) {
							NoteTag tag = new NoteTag();
							tag.setName(t.getName());
							tag.setValue(t.getValue());
							tags.add(tag);
						}

						// note creation
						APIResult result = new APIResult();
						try {
							Part file = req.getPart("file");
							signedUserController.createNote(server, servletContext, userSession.getUsername(),
									notebookName, noteName, tags, file);
							result.setOk(true);
						} catch (OperationFailedException e) {
							e.printStackTrace();
							result.setOk(false);
							result.setMsg(e.getMessage());
						} catch (ServletException e) {
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
		if (userSession.isAuthed()) {
			String action = req.getParameter("action");
			if (action != null) {
				if (action.equals("retrieveNotebooks")) {
					try {
						// parsing

						// retrieving notebooks
						APIResult result = new APIResult();
						List<NotebookFrontend> notebooks = signedUserController
								.retrieveNotebooks(userSession.getUsername()).stream().map(nb -> {
									return new NotebookFrontend(nb);
								}).collect(Collectors.toList());

						result.setOk(true);
						result.setObj(notebooks);
						res.getWriter().print(gson.toJson(result));

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (action.equals("retrieveNotes")) {
					try {
						// parsing
						String notebookName = req.getParameter("notebookName");
						// retrieving notes
						APIResult result = new APIResult();
						List<NoteFrontend> notes = signedUserController
								.retrieveNotes(userSession.getUsername(), notebookName).stream().map(n -> {
									return new NoteFrontend(n);
								}).collect(Collectors.toList());
						result.setOk(true);
						result.setObj(notes);
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
				e.printStackTrace();
			}
		}

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) {
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
				if (action.equals("deleteNotebook")) {
					try {
						// parsing
						String notebookName = req.getParameter("notebookName");
						// deleting notebook
						APIResult result = new APIResult();
						try {
							signedUserController.deleteNotebook(servletContext, userSession.getUsername(),
									notebookName);
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

				if (action.equals("deleteNote")) {
					try {
						// parsing
						String notebookName = req.getParameter("notebookName");
						String noteName = req.getParameter("noteName");
						// retrieving notebook
						APIResult result = new APIResult();
						try {
							signedUserController.deleteNote(servletContext, userSession.getUsername(), notebookName,
									noteName);
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
}