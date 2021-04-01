package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import G47.Grupo47.DirExplorer.FileHandler;

public class LOC_classHandler implements FileHandler{

	@Override
	public ArrayList<Metricas> handle(int level, String path, File file,ArrayList<Metricas> metricas) {
		LOC_class LOC_c = new LOC_class(file,path);
		ArrayList<Metricas> novas = new ArrayList<>();
		try {
			novas = LOC_c.extrair_LOC_class(file, path,metricas);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return novas;
		
	}

}
