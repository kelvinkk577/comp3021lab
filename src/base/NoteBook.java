package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName, String title)
	{
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean createTextNote(String folderName, String title, String content)
	{
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}

	
	public boolean createImageNote(String folderName, String title)
	{
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders()
	{
		return folders;
	}
	
	public boolean insertNote(String folderName, Note note)
	{
		Folder folder = null;
		for (Folder f : folders)
		{
			if (f.getName().equals(folderName))
				folder = f;
		}
		if (folder == null)
		{
			folder = new Folder(folderName);
			folders.add(folder);
		}
		
		for (Note n : folder.getNotes())
		{
			if (n.equals(note))
			{
				System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		folder.addNote(note);
		return true;
	}
	
	public void sortFolders()
	{
		for (Folder folder : folders)
			folder.sortNotes();
		Collections.sort(folders);
	}
	
	public List<Note> searchNotes(String keywords)
	{
		ArrayList<Note> result = new ArrayList<Note>();
		for (Folder folder : folders)
			for (Note note : folder.searchNotes(keywords))
				result.add(note);
		
		return result;
	}
}
