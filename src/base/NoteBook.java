package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NoteBook implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public NoteBook(String file)
	{
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try
		{
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook n = (NoteBook) in.readObject();
			in.close();
			folders = n.folders;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	// helper
	public Folder getFolder(String name)
	{
		for (Folder folder : folders)
			if (folder.getName().compareTo(name) == 0)
				return folder;
		
		return null;
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
	
	public boolean save(String file)
	{
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try
		{
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void addFolder(String folderName)
	{
		if (folderName.isEmpty())
			return;
		if (getFolder(folderName) != null)
			return;
		
		folders.add(new Folder(folderName));
	}
}
