package base;

import java.util.List;

public class KelvinTest {
	public static void main(String args[]){
		
		NoteBook nb = new NoteBook();
		nb.createTextNote("folder1", "java attendance");
		nb.createTextNote("folder1", "java session");
		nb.createTextNote("folder1", "java on99");
		nb.createTextNote("folder1", "lab attendance");
		nb.createTextNote("folder1", "lab session");
		nb.createTextNote("folder1", "lab on99");

		List<Note> result = nb.getFolders().get(0).searchNotes(" ");
		for (Note n : result)
			System.out.println(n.getTitle());
	}
}
