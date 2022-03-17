package base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextNote extends Note {
	String content;
	
	public TextNote(String title)
	{
		super(title);
	}
	
	public TextNote(String title, String content)
	{
		super(title);
		this.content = content;
	}
	
	public TextNote(File f)
	{
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	public boolean containKeyword(String keyword)
	{
		return (getTitle().toLowerCase().contains(keyword) || content.toLowerCase().contains(keyword));
	}
	
	private String getTextFromFile(String absolutePath)
	{
		String result = "";
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));
			String line;
			while ((line = in.readLine()) != null)
				result += line;
			in.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void exportTextToFile(String pathFolder)
	{
		if (pathFolder == "")
			pathFolder = ".";
		String outTitle = getTitle().replace(" ", "_");
		
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(pathFolder + File.separator + outTitle + ".txt"));
			out.write(this.content);
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
