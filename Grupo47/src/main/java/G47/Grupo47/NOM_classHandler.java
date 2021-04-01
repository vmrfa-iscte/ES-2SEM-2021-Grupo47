package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import G47.Grupo47.DirExplorer.FileHandler;

public class NOM_classHandler implements FileHandler {

	@Override
	public ArrayList<Metricas> handle(int level, String path, File file, ArrayList<Metricas> metricas) {
		NOM_class nom = new NOM_class();
		ArrayList<Metricas> metricas2 = new ArrayList<Metricas>();
		try {
			metricas2 = nom.extractNOMclass(file, path,metricas);
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return metricas2;
	}

}
