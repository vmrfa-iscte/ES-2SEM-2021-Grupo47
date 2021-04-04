package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import G47.Grupo47.DirExplorer.FileHandler;

public class ExtractMetrics_Handler implements FileHandler{

	@Override
	public ArrayList<Metrics> handle(int level, String path, File file, ArrayList<Metrics> metricas) {
		ExtractMetrics em = new ExtractMetrics(file, path);
		ArrayList<Metrics> metrics = new ArrayList<>();
		try {
			metrics = em.extrair_Metricas(metricas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(Metrics m: metrics) {
//			System.out.println(m.toString());
//			
//		}
		return metrics;
	}

}
