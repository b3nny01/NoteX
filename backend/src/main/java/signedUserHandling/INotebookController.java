package signedUserHandling;

import java.util.List;

import javax.servlet.ServletContext;

import domain.*;
import exceptions.OperationFailedException;

public interface INotebookController {
	public void createNotebook(String username, String notebookName, List<NotebookTag> tags)
			throws OperationFailedException;

	public List<Notebook> retrieveNotebooks(String username);
	
	public void updateNotebook(String username, String oldNotebookName, String newNotebookName, List<NotebookTag> newTags)
			throws OperationFailedException;

	public void deleteNotebook(ServletContext context, String username, String notebookName)
			throws OperationFailedException;
}
