package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws IOException {
		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
		DirExplorer de = new DirExplorer(new ExtractMetrics_Handler());
		de.explore(projectDir);
		ExcelManip manip = new ExcelManip();
		System.out.println("Excel criado");
		manip.createExcel(manip.extractHeaders(),de.getMetrics());
	}

}