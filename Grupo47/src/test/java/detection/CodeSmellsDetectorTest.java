package detection;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Rule;
import extraction.DirExplorer;

class CodeSmellsDetectorTest {

	protected File projectDir;
	protected DirExplorer de;
	protected Rule rule;
	protected ArrayList<MethodMetrics> metrics;
	protected ArrayList<HasCodeSmell> smells;
	protected CodeSmellsDetector detector;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		assertNotNull(projectDir);
		de = new DirExplorer(projectDir);
		assertNotNull(de);
		metrics = de.exploreAndExtract();
		assertNotNull(metrics);
	}



	@Test
	void testDetectLongMethodGreaterGreater() {
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterGreater();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectLongMethodGreaterLesser() {
		rule = new Rule("LOC_Method",">","1","AND","CYCLO_Method","<","2000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method","<","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodGreaterLesser();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectLongMethodSmallerBigger() {
		rule = new Rule("LOC_Method","<","3000","AND","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","3000","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserGreater();
		assertNotNull(smells);
	}

	@Test
	void testDetectLongMethodLesserLesser() {
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","5","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","5","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserLesser();
		assertNotNull(smells);
//		
//		rule = new Rule("LOC_Method","<","3000","ERROR","CYCLO_Method","<","1","","","","");
//		assertNotNull(rule);
//		detector = new CodeSmellsDetector(metrics,rule);
//		assertNotNull(detector);
//		smells = detector.detectLongMethodLesserLesser();
//		assertNotNull(smells);
//		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method","<","10","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","10","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserLesser();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassGreaterGreaterWMC_NOM() {
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterWMC_NOM();
		assertNotNull(smells);
	}

	@Test
	void testDetectGodClassGreaterLesserWMC_NOM() {
		rule = new Rule("LOC_Method",">","1","AND","CYCLO_Method","<","2000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method","<","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_NOM();
		assertNotNull(smells);
	}

	@Test
	void testDetectGodClassLesserLesserWMC_NOM() {
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","5","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","5","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_NOM();
		assertNotNull(smells);
//		
//		rule = new Rule("LOC_Method","<","3000","ERROR","CYCLO_Method","<","1","","","","");
//		assertNotNull(rule);
//		detector = new CodeSmellsDetector(metrics,rule);
//		assertNotNull(detector);
//		smells = detector.detectGodClassLesserLesserWMC_NOM();
//		assertNotNull(smells);
//		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method","<","10","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","10","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_NOM();
		assertNotNull(smells);
	}

	@Test
	void testDetectGodClassLesserGreaterWMC_NOM() {
		rule = new Rule("LOC_Method","<","3000","AND","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","3000","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_NOM();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectLongMethodLesserGreater();
		assertNotNull(smells);
	
	}
//
	@Test
	void testDetectGodClassGreaterGreaterWMC_LOC() {
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterWMC_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassGreaterLesserWMC_LOC() {
		rule = new Rule("LOC_Method",">","1","AND","CYCLO_Method","<","2000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method","<","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserWMC_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassLesserLesserWMC_LOC() {
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","5","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","5","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_LOC();
		assertNotNull(smells);
//		
//		rule = new Rule("LOC_Method","<","3000","ERROR","CYCLO_Method","<","1","","","","");
//		assertNotNull(rule);
//		detector = new CodeSmellsDetector(metrics,rule);
//		assertNotNull(detector);
//		smells = detector.detectGodClassLesserLesserWMC_LOC();
//		assertNotNull(smells);
//		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method","<","10","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","10","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserWMC_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassLesserGreaterWMC_LOC() {
		rule = new Rule("LOC_Method","<","3000","AND","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","3000","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterWMC_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassGreaterGreaterNOM_LOC() {
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterNOM_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassGreaterLesserNOM_LOC() {
		rule = new Rule("LOC_Method",">","1","AND","CYCLO_Method","<","2000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","ERROR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","3000","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method",">","1","OR","CYCLO_Method","<","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserNOM_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassLesserGreaterNOM_LOC() {
		rule = new Rule("LOC_Method","<","3000","AND","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","ERROR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method",">","3000","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","3000","OR","CYCLO_Method",">","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterNOM_LOC();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassLesserLesserNOM_LOC() {
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","5","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","5","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserNOM_LOC();
		assertNotNull(smells);
//		
//		rule = new Rule("LOC_Method","<","3000","ERROR","CYCLO_Method","<","1","","","","");
//		assertNotNull(rule);
//		detector = new CodeSmellsDetector(metrics,rule);
//		assertNotNull(detector);
//		smells = detector.detectGodClassLesserLesserWMC_LOC();
//		assertNotNull(smells);
//		
		rule = new Rule("LOC_Method","<","1","OR","CYCLO_Method","<","10","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserNOM_LOC();
		assertNotNull(smells);
		
		rule = new Rule("LOC_Method","<","10","OR","CYCLO_Method","<","1","","","","");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserNOM_LOC();
		assertNotNull(smells);
	}

	@Test
	void testDetectGodClassGreaterGreaterGreater() {
		rule = new Rule("method1",">","1","OR","method2",">","1","OR","method3",">","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","OR","method2",">","40","AND","method3",">","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2",">","1","OR","method3",">","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2",">","1","AND","method3",">","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterGreater();
		assertNotNull(smells);
	}

	@Test
	void testDetectGodClassLesserLesserLesser() {
		rule = new Rule("method1","<","1","OR","method2","<","1","OR","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","OR","method2","<","40","AND","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2","<","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2","<","1","AND","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserLesser();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassGreaterLesserLesser() {
		rule = new Rule("method1",">","1","OR","method2","<","1","OR","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","OR","method2","<","40","AND","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2","<","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2","<","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserLesser();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassGreaterLesserGreater() {
		rule = new Rule("method1",">","1","OR","method2","<","1","OR","method3",">","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","OR","method2","<","40","AND","method3",">","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2","<","1","OR","method3",">","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2","<","1","OR","method3",">","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterLesserGreater();
		assertNotNull(smells);

	}
//
	@Test
	void testDetectGodClassGreaterGreaterLesser() {
		rule = new Rule("method1",">","1","OR","method2",">","1","OR","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","OR","method2",">","40","AND","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2",">","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1",">","40","AND","method2",">","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassGreaterGreaterLesser();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassLesserLesserGreater() {
		rule = new Rule("method1","<","1","OR","method2","<","1","OR","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","OR","method2","<","40","AND","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2","<","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2","<","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserLesserGreater();
		assertNotNull(smells);
	}

	
	void testDetectGodClassLesserGreaterGreater() {
		rule = new Rule("method1","<","1","OR","method2",">","1","OR","method3",">","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","OR","method2",">","40","AND","method3",">","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2",">","1","OR","method3",">","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterGreater();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2",">","1","OR","method3",">","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterGreater();
		assertNotNull(smells);
	}
//
	@Test
	void testDetectGodClassLesserGreaterLesser() {
		rule = new Rule("method1","<","1","OR","method2",">","1","OR","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","OR","method2",">","40","AND","method3","<","1");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2",">","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterLesser();
		assertNotNull(smells);
		
		rule = new Rule("method1","<","40","AND","method2",">","1","OR","method3","<","40");
		assertNotNull(rule);
		detector = new CodeSmellsDetector(metrics,rule);
		assertNotNull(detector);
		smells = detector.detectGodClassLesserGreaterLesser();
		assertNotNull(smells);
	}

}
