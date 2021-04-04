package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class ExcelManipTest extends TestCase {
	
	
	public void testeCreation() {
		ExcelManip a = new ExcelManip();
		assertNotNull(a);
		
	}
	
	public void testPath()  {
	ExcelManip a = new ExcelManip();
	String s = new String("C:\\Users\\Tomás Mendes\\Desktop\\result.xlsx");
	assertNotNull(s);
	assertNotNull(a.toCopy);
	assertEquals(a.toCopy,s);

	
	}
	
	public void testHeadersExtraction() throws IOException {
		ExcelManip a = new ExcelManip();
		ArrayList<String> toCompare = new ArrayList<>();
		toCompare.add("MethodID");
		toCompare.add("package");
		toCompare.add("class");
		toCompare.add("method");
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
		assertEquals(result,toCompare);
		
		
	}
	
	public void testExcelCreation() throws IOException {
		// already tested but necessary
		ExcelManip a = new ExcelManip();
		ArrayList<String> result = a.extractHeaders();
		// already tested but necessary
		
		// tested in respective class but necessary
		File projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10");
		DirExplorer de = new DirExplorer(projectDir);
		ArrayList<Metrics> metrics = de.explore();
		ExcelManip manip = new ExcelManip();
		manip.createExcel(result,metrics );
		
		


		
		
		
		
		
	}
	

}
