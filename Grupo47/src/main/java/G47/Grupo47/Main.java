package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {


	public static void main(String[] args) throws IOException {

		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
		DirExplorer de = new DirExplorer(projectDir);
		ArrayList<Metrics> metrics = de.explore();
		ExcelManip manip = new ExcelManip();
		System.out.println("Excel criado");
		manip.createExcel(manip.extractHeaders(),metrics);
		Statistics stats = new Statistics(metrics);
		System.out.println("Numero de métodos " + stats.countNumberOfMethods());
		System.out.println("Numero de classes " + stats.countClasses());
		System.out.println("Numero de classes " + stats.countPackages());
		System.out.println("Numero de classes " + stats.countLinesOfCode());
		for(Metrics m: metrics) {
			System.out.println(m.toString());
		}
	}

}