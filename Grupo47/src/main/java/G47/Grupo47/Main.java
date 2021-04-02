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

		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
		DirExplorer de = new DirExplorer(new ExtractMetrics_Handler());
		ExcelManip manip = new ExcelManip();
		System.out.println("Excel criado");
		manip.createExcel(manip.extractHeaders(),de.getMetrics());

	}

}