package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Metrics;
import excel.ExcelManip;
import extraction.DirExplorer;
import junit.framework.TestCase;

public class ExcelManipTest extends TestCase {
	
	private File projectDir,projectDir2;
	protected ExcelManip manip;
	protected ExcelManip manip2;
	
	
	protected void setUp() throws Exception {
		super.setUp();
		projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10\\src\\com");
		manip = new ExcelManip(projectDir);
		projectDir2 = new File("C:\\Users\\Tomás Mendes\\Desktop\\Code_Smells.xlsx");
		manip2 = new ExcelManip(projectDir2);
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	
	public void testeCreation() {
		
		manip = new ExcelManip(projectDir);
		assertNotNull(manip);
		
	}
	

//	
	public void testHeadersExtraction() throws IOException {
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
	
	public void testExcelCreation() throws IOException {

		File projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10\\src\\com");
		assertNotNull(projectDir);
		DirExplorer de = new DirExplorer(projectDir);
		assertNotNull(de);
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		assertNotNull(manip);
		manip.createExcel(metrics,"C:\\Users\\Tomás Mendes\\Desktop");	
	}
	
	public void testConverionToArray() throws IOException {
	
	
		ArrayList<HasCodeSmell> converted = manip2.toComparables(7);
		assertNotNull(converted);
		ArrayList<HasCodeSmell> converted2 = manip2.toComparables(10);
		assertNotNull(converted2);
		
	}
	

}
