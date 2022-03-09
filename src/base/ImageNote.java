package base;

import java.io.File;

public class ImageNote extends Note {
	File image;
	
	public ImageNote(String title)
	{
		super(title);
	}
	
	public boolean containKeyword(String keyword)
	{
		return getTitle().toLowerCase().contains(keyword);
	}
}
