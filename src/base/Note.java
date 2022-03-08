package base;

import java.util.Date;

public class Note implements Comparable<Note> {
	private Date date;
	private String title;
	
	public Note(String title)
	{
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public boolean containKeyword(String keyword)
	{
//		System.out.println("Invoking superclass keyword search");
		return title.toLowerCase().contains(keyword);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof Note))
			return false;
		Note other = (Note) obj;
		return title.equals(other.getTitle());
	}

	@Override
	public int compareTo(Note o) 
	{
		return -date.compareTo(o.date);
	}
	
	public String toString()
	{
		return date.toString() + "\t" + title;
	}
}
