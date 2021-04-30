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

class DetectionChooserTest {
	
	protected File projectDir;
	protected DirExplorer de,de2;
	protected Rule RuleToTest1;
	protected Rule RuleToTest2;
	protected Rule RuleToTest3;
	protected Rule RuleToTest4;
	protected Rule RuleToTest5;
	protected Rule RuleToTest6;
	protected Rule RuleToTest7,RuleToTest8,RuleToTest9,RuleToTest10,RuleToTest11,
	RuleToTest12,RuleToTest13,RuleToTest14,RuleToTest15,RuleToTest16,RuleToTest17,RuleToTest18;
	protected ArrayList<MethodMetrics> metricsToTest,metricsToTest2;
	protected DetectionChooser chooser;
	protected ArrayList<HasCodeSmell> result;
	protected CodeSmellsDetector detector;
	protected ArrayList<HasCodeSmell> toCompare;

	@BeforeEach
	void setUp() throws Exception {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		assertNotNull(projectDir);
		de = new DirExplorer(projectDir);
		de2 = new DirExplorer(projectDir);
		assertNotNull(de);
		metricsToTest = de.exploreAndExtract();
		metricsToTest2 = de2.exploreAndExtract();
		assertNotNull(metricsToTest);
		chooser = new DetectionChooser();
		result = new ArrayList<HasCodeSmell>();
	
	}

	@Test
	void testChooseDetectionLocMethod() {
		RuleToTest1 = new Rule("LOC_Method",">","1","AND","CYCLO_Method",">","1","","","","");
		assertNotNull(RuleToTest1);
		RuleToTest2 = new Rule("LOC_Method",">","1","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(RuleToTest2);
		RuleToTest3 = new Rule("LOC_Method","<","1","AND","CYCLO_Method",">","1","","","","");
		assertNotNull(RuleToTest3);
		RuleToTest4 = new Rule("LOC_Method","<","1","AND","CYCLO_Method","<","1","","","","");
		assertNotNull(RuleToTest4);
		RuleToTest5 = new Rule("LOC_Method","ERROR","1","AND","CYCLO_Method","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
		RuleToTest6 = new Rule("LOC_Method",">","1","AND","CYCLO_Method","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
		RuleToTest7 = new Rule("LOC_Method","<","1","AND","CYCLO_Method","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
		
		result = chooser.chooseDetectionLocMethod(RuleToTest1, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest1);
		toCompare = new ArrayList<>();
		toCompare = detector.detectLongMethodGreaterGreater();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
		result = chooser.chooseDetectionLocMethod(RuleToTest2, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest2);
		toCompare = new ArrayList<>();
		toCompare = detector.detectLongMethodGreaterLesser();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
	
		
	
		result = chooser.chooseDetectionLocMethod(RuleToTest3, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest3);
		toCompare = new ArrayList<>();
		toCompare = detector.detectLongMethodSmallerBigger();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
	
		result = chooser.chooseDetectionLocMethod(RuleToTest4, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest4);
		toCompare = new ArrayList<>();
		toCompare = detector.detectLongMethodLesserLesser();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
		
		result = chooser.chooseDetectionLocMethod(RuleToTest5, metricsToTest);
		assertNotNull(result);
		
		

		result = chooser.chooseDetectionLocMethod(RuleToTest6, metricsToTest);
		assertNotNull(result);
		
	
		result = chooser.chooseDetectionLocMethod(RuleToTest7, metricsToTest);
		assertNotNull(result);
		
		
	}

	@Test
	void testChooseDetectionWMC_NOM() {
		RuleToTest1 = new Rule("WMC_Class",">","1","AND","NOM_Class",">","1","","","","");
		assertNotNull(RuleToTest1);
		RuleToTest2 = new Rule("WMC_Class",">","1","AND","NOM_Class","<","1","","","","");
		assertNotNull(RuleToTest2);
		RuleToTest3 = new Rule("WMC_Class","<","1","AND","NOM_Class",">","1","","","","");
		assertNotNull(RuleToTest3);
		RuleToTest4 = new Rule("WMC_Class","<","1","AND","NOM_Class","<","1","","","","");
		assertNotNull(RuleToTest4);
		RuleToTest5 = new Rule("WMC_Class","ERROR","1","AND","NOM_Class","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
		RuleToTest6 = new Rule("WMC_Class",">","1","AND","NOM_Class","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
		RuleToTest7 = new Rule("WMC_Class","<","1","AND","NOM_Class","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
	
		
		result = chooser.chooseDetectionWMC_NOM(RuleToTest1, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest1);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterGreaterWMC_NOM();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
	
		result = chooser.chooseDetectionWMC_NOM(RuleToTest2, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest2);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterLesserWMC_NOM();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
	
		
	
		result = chooser.chooseDetectionWMC_NOM(RuleToTest3, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest3);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserGreaterWMC_NOM();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
	
		result = chooser.chooseDetectionWMC_NOM(RuleToTest4, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest4);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserWMC_NOM();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
		result = chooser.chooseDetectionWMC_NOM(RuleToTest5, metricsToTest);
		assertNotNull(result);
		
		

		result = chooser.chooseDetectionWMC_NOM(RuleToTest6, metricsToTest);
		assertNotNull(result);
		
	
		result = chooser.chooseDetectionWMC_NOM(RuleToTest7, metricsToTest);
		assertNotNull(result);
		
		
	}

	@Test
	void testChooseDetectionWMC_LOC() {
		
			RuleToTest1 = new Rule("WMC_Class",">","1","AND","LOC_Class",">","1","","","","");
			assertNotNull(RuleToTest1);
			RuleToTest2 = new Rule("WMC_Class",">","1","AND","LOC_Class","<","1","","","","");
			assertNotNull(RuleToTest2);
			RuleToTest3 = new Rule("WMC_Class","<","1","AND","LOC_Class",">","1","","","","");
			assertNotNull(RuleToTest3);
			RuleToTest4 = new Rule("WMC_Class","<","1","AND","LOC_Class","<","1","","","","");
			assertNotNull(RuleToTest4);
			RuleToTest5 = new Rule("WMC_Class","ERROR","1","AND","LOC_Class","ERROR","1","","","","");
			assertNotNull(RuleToTest5);
			RuleToTest6 = new Rule("WMC_Class",">","1","AND","LOC_Class","ERROR","1","","","","");
			assertNotNull(RuleToTest5);
			RuleToTest7 = new Rule("WMC_Class","<","1","AND","LOC_Class","ERROR","1","","","","");
			assertNotNull(RuleToTest5);
		
			
			result = chooser.chooseDetectionWMC_LOC(RuleToTest1, metricsToTest);
			assertNotNull(result);
			detector = new CodeSmellsDetector(metricsToTest2,RuleToTest1);
			toCompare = new ArrayList<>();
			toCompare = detector.detectGodClassGreaterGreaterWMC_LOC();
			for (int i = 0; i != result.size(); i++) {
				assertEquals(toCompare.get(i).toString(),result.get(i).toString());
			}
			
		
			result = chooser.chooseDetectionWMC_LOC(RuleToTest2, metricsToTest);
			assertNotNull(result);
			detector = new CodeSmellsDetector(metricsToTest2,RuleToTest2);
			toCompare = new ArrayList<>();
			toCompare = detector.detectGodClassGreaterLesserWMC_LOC();
			for (int i = 0; i != result.size(); i++) {
				assertEquals(toCompare.get(i).toString(),result.get(i).toString());
			}
		
			
		
			result = chooser.chooseDetectionWMC_LOC(RuleToTest3, metricsToTest);
			assertNotNull(result);
			detector = new CodeSmellsDetector(metricsToTest2,RuleToTest3);
			toCompare = new ArrayList<>();
			toCompare = detector.detectGodClassLesserGreaterWMC_LOC();
			for (int i = 0; i != result.size(); i++) {
				assertEquals(toCompare.get(i).toString(),result.get(i).toString());
			}
			
			
		
			result = chooser.chooseDetectionWMC_LOC(RuleToTest4, metricsToTest);
			assertNotNull(result);
			detector = new CodeSmellsDetector(metricsToTest2,RuleToTest4);
			toCompare = new ArrayList<>();
			toCompare = detector.detectGodClassLesserLesserWMC_LOC();
			for (int i = 0; i != result.size(); i++) {
				assertEquals(toCompare.get(i).toString(),result.get(i).toString());
			}
			
			
			result = chooser.chooseDetectionWMC_LOC(RuleToTest5, metricsToTest);
			assertNotNull(result);
			
			

			result = chooser.chooseDetectionWMC_LOC(RuleToTest6, metricsToTest);
			assertNotNull(result);
			
		
			result = chooser.chooseDetectionWMC_LOC(RuleToTest7, metricsToTest);
			assertNotNull(result);
			
			
		
	}

	@Test
	void testChooseDetectionNOM_LOC() {
		RuleToTest1 = new Rule("NOM_Class",">","1","AND","LOC_Class",">","1","","","","");
		assertNotNull(RuleToTest1);
		RuleToTest2 = new Rule("NOM_Class",">","1","AND","LOC_Class","<","1","","","","");
		assertNotNull(RuleToTest2);
		RuleToTest3 = new Rule("NOM_Class","<","1","AND","LOC_Class",">","1","","","","");
		assertNotNull(RuleToTest3);
		RuleToTest4 = new Rule("NOM_Class","<","1","AND","LOC_Class","<","1","","","","");
		assertNotNull(RuleToTest4);
		RuleToTest5 = new Rule("NOM_Class","ERROR","1","AND","LOC_Class","ERROR","1","","","","");
		assertNotNull(RuleToTest5);
		RuleToTest6 = new Rule("NOM_Class",">","1","AND","LOC_Class","ERROR","1","","","","");
		assertNotNull(RuleToTest6);
		RuleToTest7 = new Rule("NOM_Class","<","1","AND","LOC_Class","ERROR","1","","","","");
		assertNotNull(RuleToTest7);
	
		
		result = chooser.chooseDetectionNOM_LOC(RuleToTest1, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest1);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterGreaterNOM_LOC();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
	
		result = chooser.chooseDetectionNOM_LOC(RuleToTest2, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest2);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterLesserNOM_LOC();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
	
		
		result = chooser.chooseDetectionNOM_LOC(RuleToTest3, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest3);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserGreaterNOM_LOC();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
	
		result = chooser.chooseDetectionNOM_LOC(RuleToTest4, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest4);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserNOM_LOC();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
		
		result = chooser.chooseDetectionNOM_LOC(RuleToTest5, metricsToTest);
		assertNotNull(result);
		
		

		result = chooser.chooseDetectionNOM_LOC(RuleToTest6, metricsToTest);
		assertNotNull(result);
		
	
		result = chooser.chooseDetectionNOM_LOC(RuleToTest7, metricsToTest);
		assertNotNull(result);
	}

	@Test
	void testChooseDetectionWMC_NOM_LOC() {
		RuleToTest1 = new Rule("NOM_Class",">","1","AND","NOM_Class",">","1","AND","LOC_Class",">","1");
		assertNotNull(RuleToTest1);
		RuleToTest2 = new Rule("NOM_Class",">","1","AND","NOM_Class",">","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest2);
		RuleToTest3 = new Rule("NOM_Class",">","1","AND","NOM_Class","<","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest3);
		RuleToTest4 = new Rule("NOM_Class",">","1","AND","NOM_Class","<","1","AND","LOC_Class",">","1");
		assertNotNull(RuleToTest4);
		RuleToTest5 = new Rule("NOM_Class","<","1","AND","NOM_Class",">","1","AND","LOC_Class",">","1");
		assertNotNull(RuleToTest5);
		RuleToTest6 = new Rule("NOM_Class","<","1","AND","NOM_Class","<","1","AND","LOC_Class",">","1");
		assertNotNull(RuleToTest6);
		RuleToTest7 = new Rule("NOM_Class","<","1","AND","NOM_Class",">","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest7);
		RuleToTest8 = new Rule("NOM_Class","<","1","AND","NOM_Class","<","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest8);
		RuleToTest9 = new Rule("NOM_Class","<","1","AND","NOM_Class","ERROR","1","AND","LOC_Class","ERROR","1");
		assertNotNull(RuleToTest9);
		RuleToTest10 = new Rule("NOM_Class",">","1","AND","NOM_Class","ERROR","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest10);
		RuleToTest11 = new Rule("NOM_Class",">","1","AND","NOM_Class","<","1","AND","LOC_Class","ERROR","1");
		assertNotNull(RuleToTest11);
		RuleToTest12 = new Rule("NOM_Class","ERROR","1","AND","NOM_Class",">","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest11);
		RuleToTest13 = new Rule("NOM_Class","<","1","AND","ERROR",">","1","AND","LOC_Class",">","1");
		assertNotNull(RuleToTest11);
		RuleToTest14 = new Rule("NOM_Class","<","1","AND","ERROR",">","1","AND","LOC_Class","ERROR","1");
		assertNotNull(RuleToTest11);
		RuleToTest15 = new Rule("NOM_Class","ERROR","1","AND","ERROR",">","1","AND","LOC_Class","<","1");
		assertNotNull(RuleToTest11);
		
		
		
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest1, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest1);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterGreaterGreater();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
	
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest2, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest2);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterGreaterLesser();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
	
		
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest3, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest3);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterLesserLesser();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
	
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest4, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest4);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassGreaterLesserGreater();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		
		
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest5, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest5);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserGreaterGreater();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		

		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest6, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest6);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserGreater();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
	
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest7, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest7);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserGreaterLesser();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest8, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest8);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();
		for (int i = 0; i != result.size(); i++) {
			assertEquals(toCompare.get(i).toString(),result.get(i).toString());
		}
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest9, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest9);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();

		
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest10, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest10);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();
	
		
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest11, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest11);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();
	
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest12, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest12);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();

		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest13, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest13);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();

		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest14, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest14);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();
	
		result = chooser.chooseDetectionWMC_NOM_LOC(RuleToTest15, metricsToTest);
		assertNotNull(result);
		detector = new CodeSmellsDetector(metricsToTest2,RuleToTest15);
		toCompare = new ArrayList<>();
		toCompare = detector.detectGodClassLesserLesserLesser();

		
	}

}
