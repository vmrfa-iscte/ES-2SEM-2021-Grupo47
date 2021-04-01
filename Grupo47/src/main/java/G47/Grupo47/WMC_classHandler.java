package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import G47.Grupo47.DirExplorer.FileHandler;

public class WMC_classHandler implements FileHandler{

	@Override
	public ArrayList<Metricas> handle(int level, String path, File file, ArrayList<Metricas> metricas) {
		WMC_CYCLO wc = new WMC_CYCLO();
		ArrayList<Metricas> metricas2 =new ArrayList<Metricas>();
		try {
			metricas2 = wc.cyclicMethod(file, path, metricas);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return metricas2;
	}

}
