package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;

import G47.Grupo47.DirExplorer.FileHandler;

public class LOC_methodHandler implements FileHandler {

	@Override
	public void handle(int level, String path, File file) {
		LOC_method LOC = new LOC_method(file,path);
		try {
			LOC.extrair_LOC_method(file, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
