package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static int countNumberOfMethods(ArrayList<Metrics> finalMetrics) {
		return finalMetrics.size();
	}

	public static int countLinesOfCode(ArrayList<Metrics> finalMetrics) {
		int aux = 0;
		for (Metrics m : finalMetrics) {
			aux = aux + m.getLOC_method();
		}
		return aux;
	}

	public static int countPackages(ArrayList<Metrics> finalMetrics) {
		ArrayList<String> aux = new ArrayList<String>();
		for (Metrics m : finalMetrics) {
			if (aux.contains(m.getPacote())) {
				
			}
			else {
				aux.add(m.getPacote());
			}
			
		}
		return aux.size();
	}
	
	public static int countClasses(ArrayList<Metrics> finalMetrics) {
		ArrayList<String> aux = new ArrayList<String>();
		for (Metrics m : finalMetrics) {
			if (aux.contains(m.getClasse())) {
				
			}
			else {
				aux.add(m.getClasse());
			}
			
		}
		return aux.size();
	}
	
	

	public static void main(String[] args) throws IOException {

		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
		DirExplorer de = new DirExplorer(new ExtractMetrics_Handler());
		de.explore(projectDir);
		ExcelManip manip = new ExcelManip();
		System.out.println("Excel criado");
		for(Metrics m: de.getMetrics()) {
			System.out.println(m.toString());
		}
		manip.createExcel(manip.extractHeaders(),de.getMetrics());

	}

}