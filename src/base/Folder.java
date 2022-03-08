package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder> {

	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name)
	{
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note)
	{
		notes.add(note);
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Note> getNotes()
	{
		return notes;
	}

	public void sortNotes()
	{
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords)
	{
		keywords = keywords.toLowerCase();
		keywords = keywords.replace(" or ", "||");
		String list[] = keywords.split(" ");
		
		if (list.length == 0)
			return new ArrayList<Note>();
		
		ArrayList<Note> result = new ArrayList<Note>();
		ArrayList<Note> prevResult = notes;
		
		for (int i = 0; i < list.length; i++)
		{
			result = new ArrayList<Note>();
			
			if (!list[i].contains("||"))
			{
				for (Note note : prevResult)
					if (note.containKeyword(list[i]))
						result.add(note);
			}
			else
			{
				String orList[] = list[i].split("\\|\\|");

				for (Note note : prevResult)
					for (int j = 0; j < orList.length; j++)
					{
						if (note.containKeyword(orList[j]))
						{
							result.add(note);
							break;
						}
					}
			}
			
			prevResult = result;
		}
		
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof Folder))
			return false;
		Folder other = (Folder) obj;
		return name.equals(other.getName());
	}
	
	@Override
	public String toString()
	{
		int nText = 0;
		int nImage = 0;
		for (Note note : notes)
		{
			if (note instanceof TextNote)
				nText++;
			else if (note instanceof ImageNote)
				nImage++;
		}
		
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int compareTo(Folder o) {
		return name.compareTo(o.name);
	}
}
