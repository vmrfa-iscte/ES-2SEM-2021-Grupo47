package excel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Metrics;
import excel.ExcelManip;
import extraction.DirExplorer;
import junit.framework.TestCase;

class ExcelManipTest {
	
	private File projectDir,projectDir2;
	protected ExcelManip manip;
	protected ExcelManip manip2;
	
	
	protected void setUp() throws Exception {
		
		
	}

	protected void tearDown() throws Exception {
		
	}
	
	@Test
	public void testeCreation() {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		manip = new ExcelManip(projectDir);
		projectDir2 = new File("C:\\Code_Smells.xlsx");
		manip2 = new ExcelManip(projectDir2);
		
		manip = new ExcelManip(projectDir);
		assertNotNull(manip);
		
	}
	

	@Test
	public void testHeadersExtraction() throws IOException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		manip = new ExcelManip(projectDir);
		projectDir2 = new File("C:\\Code_Smells.xlsx");
		manip2 = new ExcelManip(projectDir2);
		// Headers Extraction
		ArrayList<String> toCompare = new ArrayList<>();
		toCompare.add("MethodID");
		toCompare.add("Package");
		toCompare.add("Class");
		toCompare.add("Method");
		toCompare.add("NOM_class");
		toCompare.add("LOC_class");
		toCompare.add("WMC_class");
		toCompare.add("LOC_method");
		toCompare.add("CYCLO_method");
		assertNotNull(toCompare);
		ArrayList<String> result = manip.extractHeaders();
		assertNotNull(result);


		
		
	}
	
	@Test
	public void testExcelCreation() throws IOException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		manip = new ExcelManip(projectDir);
		projectDir2 = new File("C:\\Code_Smells.xlsx");
		manip2 = new ExcelManip(projectDir2);

		assertNotNull(projectDir);
		DirExplorer de = new DirExplorer(projectDir);
		assertNotNull(de);
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		assertNotNull(manip);
		manip.createExcel(metrics,"C:\\Users\\rui.fontoura\\Desktop");	
	}
	
	@Test
	public void testConverionToArray() throws IOException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		manip = new ExcelManip(projectDir);
		projectDir2 = new File("C:\\Code_Smells.xlsx");
		manip2 = new ExcelManip(projectDir2);
	
	
		ArrayList<HasCodeSmell> converted = manip2.toComparables(7);
		assertNotNull(converted);
		ArrayList<HasCodeSmell> converted2 = manip2.toComparables(10);
		assertNotNull(converted2);
		
	}
	

}

