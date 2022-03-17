package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>, Serializable {

	private static final long serialVersionUID = 1L;
	
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
		
		// Split up the keywords and store in an ArrayList
		ArrayList<String> list =  new ArrayList<String>(Arrays.asList(keywords.split(" ")));
		
		ArrayList<String[]> orPairs = new ArrayList<String[]>();
		
		int orIndex = list.indexOf("or"); 
		
		// Repeat until no more OR keyword is found
		while (orIndex != -1) 
		{
			if (orIndex == 0 || orIndex == list.size()-1)
				System.err.println("Or operator should not be the first or final word in the keyword string");
			
			String[] pair = new String[] {list.get(orIndex-1), list.get(orIndex+1)};
			orPairs.add(pair); // Add the pair to the orPairs ArrayList
			
			// Remove the pair and the OR operator from the list
			list.remove(orIndex-1);
			list.remove(orIndex-1);
			list.remove(orIndex-1);
			
			orIndex = list.indexOf("or");
		}
		
		// Since all the OR pairs were removed, list now contains only keywords with the AND relationship
		// Create an alias simply for readability
		ArrayList<String> andList = list;
		ArrayList<Note> result = new ArrayList<Note>();
		
		/* The search approach here is to find all the notes that satisfy the AND requirement first,
		 * Then, base on those notes (which is stored in the result ArrayList), find the notes that satisfy the OR requirement
		 */
		if (!andList.isEmpty())
		{
			for (Note note : notes)
			{
				boolean andFlag = true;
				for (String kw : andList)
				{
					if (!(note.containKeyword(kw)))
					{
						andFlag = false;
						break;
					}
				}
				
				if (andFlag)
					result.add(note);
			}
			
			// No need to do the search for the OR requirement and can simply return the current result if there is no OR relationship at all
			if (orPairs.isEmpty())
				return result;
		}
		else // Change the search range to all the notes in the folder instead of the AND result when there is no AND relationship at all
		{
			result = notes;
		}
		
		// Find the notes that satisfy the OR requirement
		ArrayList<Note> newResult = new ArrayList<Note>();
		for (String[] kwPair : orPairs)
		{
			newResult = new ArrayList<Note>();
			
			for (Note note : result) // The search range is based on the search result in the first part
			{
				if (note.containKeyword(kwPair[0]) || note.containKeyword(kwPair[1]))
				{
					newResult.add(note);
				}
			}
			
			result = newResult; // Update the search range for the next iteration because the search should base on the latest search result
		}
		
		return newResult;
			
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
