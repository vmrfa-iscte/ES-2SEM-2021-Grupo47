package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import G47.Grupo47.DirExplorer.FileHandler;

public class LOC_methodHandler implements FileHandler {

	@Override
	public ArrayList<Metricas> handle(int level, String path, File file,ArrayList<Metricas> metricas) {
		LOC_method LOC = new LOC_method(file,path);
		ArrayList<Metricas> novas = new ArrayList<Metricas>();
		try {
			novas =  LOC.extrair_LOC_method(file, path,metricas);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return novas;
		
	}

}
