package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;

import G47.Grupo47.DirExplorer.FileHandler;

public class LOC_classHandler implements FileHandler{

	@Override
	public void handle(int level, String path, File file) {
		LOC_class LOC_c = new LOC_class(file,path);
		try {
			LOC_c.LOC(file, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
