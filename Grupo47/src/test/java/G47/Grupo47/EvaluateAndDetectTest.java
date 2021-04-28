//package G47.Grupo47;
//
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
//
//
//import classes.HasCodeSmell;
//import classes.MethodMetrics;
//import classes.Rule;
//import detection.CodeSmellsDetector;
//import detection.DetectionChooser;
//import detection.EvaluateAndDetect;
//import extraction.DirExplorer;
//import junit.framework.TestCase;
//
//public class EvaluateAndDetectTest extends TestCase{
//	
//	protected File projectDir;
//	protected DirExplorer directoryExplorer,dirExplorer2;
//	protected CodeSmellsDetector firstDetector,secondDetector;
//	protected Rule ruleIsLong,ruleIsGod;
//	protected ArrayList<MethodMetrics> extractedMetrics,extractedMetrics2;
//	protected DetectionChooser chooser;
//	protected ArrayList<HasCodeSmell> resultsFromChooser;
//	protected EvaluateAndDetect evaluateNDetect;
//	protected HashMap<String,ArrayList<HasCodeSmell>> toCompare,results;
//
//	
//	public void setUp() throws Exception {
//		System.out.println("set up");
//		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src");
//		directoryExplorer = new DirExplorer(projectDir);
//		dirExplorer2 = new DirExplorer(projectDir);
//		extractedMetrics2 = dirExplorer2.exploreAndExtract();
//		ruleIsLong = new Rule("LOC_Method",">","1","AND","CYCLO_Method",">","1","","","","");
//		ruleIsGod = new Rule("WMC_Class",">","1","AND","NOM_Class",">","1","AND","LOC_Class","<","2");
//		extractedMetrics = directoryExplorer.exploreAndExtract();
//		chooser = new DetectionChooser();
//		toCompare = new HashMap<String,ArrayList<HasCodeSmell>>();
//		evaluateNDetect = new EvaluateAndDetect();
//	}
//	
//	
//	public void testConstructors() {
//		assertNotNull(projectDir);
//		assertNotNull(directoryExplorer);
//		assertNotNull(chooser);
//		assertNotNull(ruleIsLong);
//		assertNotNull(ruleIsGod);
//		assertNotNull(extractedMetrics);
//		assertNotNull(evaluateNDetect);
//	}
//
//	
////	public void testGetActualmetrics() {
////		fail("Not yet implemented");
////	}
////
////	
////	public void testSetActualmetrics() {
////		fail("Not yet implemented");
////	}
//
//	
////	public void testEvaluationChooser() throws FileNotFoundException {
////		resultsFromChooser = chooser.chooseDetectionWMC_NOM(ruleIsGod, extractedMetrics);
////		toCompare.put("IsGod Class Detection", resultsFromChooser);
////		extractedMetrics = directoryExplorer.exploreAndExtract();
////		evaluateNDetect.setActualmetrics(extractedMetrics);
////		results = evaluateNDetect.evaluationChooser("WMC_class", "NOM_class", ruleIsLong);
////		if(toCompare.equals(results)) System.out.println("equals!");
////		assertEquals(toCompare,results);
//
////	}
//
//
//	public void testEvaluateLocMethod() throws FileNotFoundException {
//		resultsFromChooser = chooser.chooseDetectionLocMethod(ruleIsLong, extractedMetrics);
//		toCompare.put("IsLong Method Detection", resultsFromChooser);
//		ArrayList<HasCodeSmell> l = toCompare.values().iterator().next();
//		for(HasCodeSmell hcs: l) System.out.println(hcs.getMethodName());
//		System.out.println("MEIO");
//		evaluateNDetect.setActualmetrics(extractedMetrics2);
//		results = evaluateNDetect.evaluateLocMethod(ruleIsLong);
//		ArrayList<HasCodeSmell> l2 = results.values().iterator().next();
//		for(HasCodeSmell hcs2: l2) System.out.println(hcs2.getMethodName());
//		if(l.equals(l2)) System.out.println("listas iguais");
//		if(toCompare.equals(results)) System.out.println("equals! loc");
//		assertEquals(toCompare,results);
//	}
//
////	
////	public void testEvaluateGodClassWithWMC_NOM() {
////		fail("Not yet implemented");
////	}
////
////
////	public void testEvaluateGodClassWithWMC_LOC() {
////		fail("Not yet implemented");
////	}
////
////
////	public void testEvaluateGodClassWithNOM_LOC() {
////		fail("Not yet implemented");
////	}
////
////
////	public void testEvaluateGodClassWithWMC_NOM_LOC() {
////		fail("Not yet implemented");
////	}
//
//}
