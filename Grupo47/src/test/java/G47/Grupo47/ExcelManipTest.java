package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import classes.MethodMetrics;
import classes.Metrics;
import excel.ExcelManip;
import extraction.DirExplorer;
import junit.framework.TestCase;

public class ExcelManipTest extends TestCase {
	
	private File projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10\\src\\com");
	
	public void testeCreation() {
		
		ExcelManip a = new ExcelManip(projectDir);
		assertNotNull(a);
		
	}
	
	public void testPath()  {

	ExcelManip a = new ExcelManip(projectDir);
	String s = new String("C:\\Users\\rui.fontoura\\Desktop\\result_jasml_0_10.xlsx");
	assertNotNull(s);
	assertNotNull(a.toCopy);
	assertEquals(a.toCopy,s);

	
	}
	
	public void testHeadersExtraction() throws IOException {
		ExcelManip a = new ExcelManip(projectDir);
		ArrayList<String> toCompare = new ArrayList<>();
		toCompare.add("MethodID");
		toCompare.add("Package");
		toCompare.add("Class");
		toCompare.add("Method");
		toCompare.add("NOM_class");
		toCompare.add("LOC_class");
		toCompare.add("WMC_class");
		toCompare.add("is_God_Class");
		toCompare.add("LOC_method");
		toCompare.add("CYCLO_method");
		toCompare.add("is_Long_Method");
		assertNotNull(toCompare);
		ArrayList<String> result = a.extractHeaders();
		assertNotNull(result);
//		assertEquals(result,toCompare);
		
		
	}
	
	public void testExcelCreation() throws IOException {
		// already tested but necessary
		ExcelManip a = new ExcelManip(projectDir);
		ArrayList<String> result = a.extractHeaders();
		// already tested but necessary
		
		// tested in respective class but necessary
		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
		DirExplorer de = new DirExplorer(projectDir);
//		ArrayList<MethodMetrics> metrics = de.explore();
//		ExcelManip manip = new ExcelManip(projectDir);
//		manip.createExcel(metrics , "false");	
		
		
	}
	

}
