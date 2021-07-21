package Mindmap;

import java.io.File;

public class Mindmap_Main {
	public static void main(String[] args) {
		File Mindmap_Dir = new File("C:\\Mindmap");
		if(Mindmap_Dir.exists()==false)
			Mindmap_Dir.mkdir();
		
		new Mindmap_Frame();
	}
}
