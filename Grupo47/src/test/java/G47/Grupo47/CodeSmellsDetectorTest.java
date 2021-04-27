package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import classes.MethodMetrics;
import classes.Rule;
import detection.CodeSmellsDetector;
import excel.ExcelManip;
import extraction.DirExplorer;
import junit.framework.TestCase;

public class CodeSmellsDetectorTest extends TestCase {
	
	protected File projectDir;
	protected DirExplorer de;
	
	protected void setUp() throws Exception {
		super.setUp();
		projectDir = new File("C:\\Users\\Tomás Mendes\\Desktop\\jasml_0.10\\src\\com");
		de = new DirExplorer(projectDir);

		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	
 
	public void testConstructor() throws FileNotFoundException {
	
		assertNotNull(projectDir);
		assertNotNull(de);
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		Rule received = new Rule("LOC_Method",">","1","AND","CYCLO_Method",">","1","","","","");
		Rule received2 = new Rule("WMC_Class",">","1","AND","NOM_Class",">","1","AND","LOC_Class","<","2");
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received2);
		
	}
	
	public void testLongMethodGreaterGreater() throws FileNotFoundException {
		Rule received = new Rule("LOC_Method",">","1","AND","CYCLO_Method",">","1","","","","");
		Rule received2 = new Rule("LOC_Method",">","1","OR","CYCLO_Method",">","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received2);
		detector.detectLongMethodGreaterGreater();
		detector2.detectLongMethodGreaterGreater();
		
	}
	
	public void testLongMethodGreaterLesser() throws FileNotFoundException {
		Rule received = new Rule("LOC_Method",">","1","AND","CYCLO_Method","<","1","","","","");
		Rule received2 = new Rule("LOC_Method",">","1","OR","CYCLO_Method","<","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received2);
		detector.detectLongMethodGreaterLesser();
		detector2.detectLongMethodGreaterLesser();
		
	}
	public void testLongMethodLesserGreater() throws FileNotFoundException {
		Rule received = new Rule("LOC_Method","<","1","AND","CYCLO_Method",">","1","","","","");
		Rule received2 = new Rule("LOC_Method","<","1","OR","CYCLO_Method",">","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received2);
		detector.detectLongMethodSmallerBigger();
		detector2.detectLongMethodSmallerBigger();
		
	}
	public void testLongMethodLesserLesser() throws FileNotFoundException {
		Rule received = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","1","","","","");
		Rule received2 = new Rule("LOC_Method","<","1","OR","CYCLO_Method","<","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received);
		detector.detectLongMethodLesserLesser();
		detector2.detectLongMethodLesserLesser();
	}
	
	public void testGodClassGreaterGreaterWMC_NOM() throws FileNotFoundException {
		Rule received = new Rule("WMC_Class",">","1","AND","NOM_Class",">","1","","","","");
		Rule received2 = new Rule("LOC_Method",">","1","OR","CYCLO_Method",">","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received);
		detector.detectGodClassGreaterGreaterWMC_NOM();
		detector2.detectGodClassGreaterGreaterWMC_NOM();
	}
	public void testGodClassGreaterLesserWMC_NOM() throws FileNotFoundException {
		Rule received = new Rule("WMC_Class",">","1","AND","NOM_Class","<","1","","","","");
		Rule received2 = new Rule("LOC_Method",">","1","OR","CYCLO_Method","<","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received);
		detector.detectGodClassGreaterLesserWMC_NOM();
		detector2.detectGodClassGreaterLesserWMC_NOM();
	}
	public void testGodClassLesserGreaterWMC_NOM() throws FileNotFoundException {
		Rule received = new Rule("WMC_Class","<","1","AND","NOM_Class",">","1","","","","");
		Rule received2 = new Rule("LOC_Method","<","1","OR","CYCLO_Method",">","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received);
		detector.detectGodClassLesserGreaterWMC_NOM();
		detector2.detectGodClassLesserGreaterWMC_NOM();
	}
	public void testGodClassLesserLesserWMC_NOM() throws FileNotFoundException {
		Rule received = new Rule("WMC_Class","<","1","AND","NOM_Class","<","1","","","","");
		Rule received2 = new Rule("LOC_Method","<","1","OR","CYCLO_Method","<","1","","","","");
		ArrayList<MethodMetrics> metrics = de.exploreAndExtract();
		CodeSmellsDetector detector = new CodeSmellsDetector(metrics,received);
		CodeSmellsDetector detector2 = new CodeSmellsDetector(metrics,received);
		detector.detectGodClassLesserLesserWMC_NOM();
		detector2.detectGodClassLesserLesserWMC_NOM();
	}
	


}
