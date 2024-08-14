package userHandling;

import java.util.List;

import domain.Notebook;
import domain.Tag;
import domain.Note;
import domain.SignedUser;

public interface ISearchController {
	
	public List<SignedUser> signedUserSearch(String searchText);
	public List<Notebook> notebookSearch(String searchText, List<Tag> tags);
	public List<Note> noteSearch(String searchText, List<Tag> tags);

}
