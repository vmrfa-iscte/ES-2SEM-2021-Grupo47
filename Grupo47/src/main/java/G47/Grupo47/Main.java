package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws IOException {
		File projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10\\src\\com\\jasml");
		DirExplorer de = new DirExplorer(new LOC_methodHandler());
		de.explore(projectDir);
		ArrayList<Metricas> metricas = de.getMetricas();
//		for(Metricas m: metricas) {
//			System.out.println(m.toString());
//		}
		DirExplorer de2 = new DirExplorer(new LOC_classHandler(),metricas);
		de2.explore(projectDir);
		ArrayList<Metricas> metricas_LOC_class = de2.getMetricas();
//		for(Metricas m: metricas_LOC_class) {
//			System.out.println(m.toString());
//		}
		ExcelManip manip = new ExcelManip();
		System.out.println("Excel criado");
		manip.createExcel(manip.extractHeaders(),metricas_LOC_class);
	}

}