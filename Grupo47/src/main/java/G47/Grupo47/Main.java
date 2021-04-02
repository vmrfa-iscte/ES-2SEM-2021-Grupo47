package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static int countNumberOfMethods(ArrayList<Metricas> finalMetrics) {
		return finalMetrics.size();
	}

	public static int countLinesOfCode(ArrayList<Metricas> finalMetrics) {
		int aux = 0;
		for (Metricas m : finalMetrics) {
			aux = aux + m.getLOC_method();
		}
		return aux;
	}

	public static int countPackages(ArrayList<Metricas> finalMetrics) {
		ArrayList<String> aux = new ArrayList<String>();
		for (Metricas m : finalMetrics) {
			if (aux.contains(m.getPacote())) {
				
			}
			else {
				aux.add(m.getPacote());
			}
			
		}
		return aux.size();
	}
	
	public static int countClasses(ArrayList<Metricas> finalMetrics) {
		ArrayList<String> aux = new ArrayList<String>();
		for (Metricas m : finalMetrics) {
			if (aux.contains(m.getClasse())) {
				
			}
			else {
				aux.add(m.getClasse());
			}
			
		}
		return aux.size();
	}
	
	

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
		DirExplorer de3 = new DirExplorer(new NOM_classHandler(),metricas_LOC_class);
		de3.explore(projectDir);
		ArrayList<Metricas> metricas_NOM_class = de3.getMetricas();
		DirExplorer d4 = new DirExplorer(new WMC_classHandler(),metricas_NOM_class);
		d4.explore(projectDir);
		ArrayList<Metricas> finalMetricas = d4.getMetricas();
		//		for(Metricas m: finalMetricas) {
		//		System.out.println(m.toString());
		//	}
		System.out.println("Numero de metodos " + countNumberOfMethods(finalMetricas));
		System.out.println("Numero de classes " + countClasses(finalMetricas));
		System.out.println("Numero de Packages " + countPackages(finalMetricas));
		System.out.println("Total linhas de código " + countLinesOfCode(finalMetricas));
//				ExcelManip manip = new ExcelManip();
//				manip.createExcel(manip.extractHeaders(),finalMetricas);
//				System.out.println("Excel criado e preenchido");

	}

}