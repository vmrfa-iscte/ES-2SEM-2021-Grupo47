package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {


	public static void main(String[] args) throws IOException {

		File projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10");
		DirExplorer de = new DirExplorer(new ExtractMetrics_Handler());
		de.explore(projectDir);
		ExcelManip manip = new ExcelManip();
		System.out.println("Excel criado");
		for(Metrics m: de.getMetrics()) {
			System.out.println(m.toString());
		}
		manip.createExcel(manip.extractHeaders(),de.getMetrics());
		Statistics stats = new Statistics(de.getMetrics());
		System.out.println("Numero de métodos " + stats.countNumberOfMethods());
		System.out.println("Numero de classes " + stats.countClasses());
		System.out.println("Numero de classes " + stats.countPackages());
		System.out.println("Numero de classes " + stats.countLinesOfCode());
	}

}