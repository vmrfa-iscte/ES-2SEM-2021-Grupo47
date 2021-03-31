package G47.Grupo47;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com\\jasml");
//		DirExplorer de = new DirExplorer(new LOC_methodHandler());
//		de.explore(projectDir);
		
		DirExplorer de2 = new DirExplorer(new LOC_classHandler());
		de2.explore(projectDir);
	}

}
