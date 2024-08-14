package signedUserHandling;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import domain.Tag;
import exceptions.OperationFailedException;
import domain.NoteTag;
import domain.Note;

public interface INoteController {
	public void createNote(String server, ServletContext context, String username, String notebookName, String noteName,
			List<NoteTag> tags, Part file) throws OperationFailedException;

	public List<Note> retrieveNotes(String username, String notebookName);
	
	public void updateNote(ServletContext context,String username, String notebookName, String oldNoteName, String newNoteName,
			List<NoteTag> newTags, Optional<Part> newFile) throws OperationFailedException;

	public void deleteNote(ServletContext context, String username, String notebookName, String noteName)
			throws OperationFailedException;

}
