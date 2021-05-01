package detection;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Rule;
import extraction.DirExplorer;

class EvaluateAndDetectTest {
	
	protected File projectDir;
	protected DirExplorer directoryExplorer,secondDirectoryExplorer;
	protected EvaluateAndDetect evaluateNDetect;
	protected ArrayList<MethodMetrics> results, compareGetter,compareEvaluation;
	protected HashMap<String,ArrayList<HasCodeSmell>> resultMap, compareMap;
	protected ArrayList<HasCodeSmell> resultList, compareList;
	protected String resultString, compareString;
	
	
	@BeforeEach
	void setUp() throws Exception {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		directoryExplorer = new DirExplorer(projectDir);
		results = directoryExplorer.exploreAndExtract();
		
		secondDirectoryExplorer = new DirExplorer(projectDir);
		compareEvaluation = secondDirectoryExplorer.exploreAndExtract();
		
		evaluateNDetect = new EvaluateAndDetect();
		evaluateNDetect.setActualmetrics(results);
	}

	@Test
	void testGetActualmetrics() throws FileNotFoundException {
		
		evaluateNDetect = new EvaluateAndDetect();
		assertNotNull(evaluateNDetect);
		
		evaluateNDetect.setActualmetrics(results);
		compareGetter = evaluateNDetect.getActualmetrics();
		assertEquals(results.size(),compareGetter.size());
		
		for(int i = 0; i< results.size();i++) 
			assertEquals(results.get(i).toString(),compareGetter.get(i).toString());
	
	}


	@Test
	void testEvaluationChooserLOC() throws FileNotFoundException {
		
		
		Rule isLong = new Rule("LOC_method",">","1","AND","CYCLO_method","<","1","","","","");
		resultMap = evaluateNDetect.evaluationChooser(isLong);
		resultList = resultMap.values().iterator().next();
		resultString = resultMap.keySet().iterator().next();
		
		DetectionChooser chooser = new DetectionChooser();
		compareList = chooser.chooseDetectionLocMethod(isLong, compareEvaluation);
		compareString = "IsLong Method Detection";
		
		assertEquals(compareString,resultString);
		for(int i = 0; i < resultList.size();i++) 
			assertEquals(compareList.get(i).toString(),resultList.get(i).toString());
		
	}

	@Test
	void testEvaluationChooserWMC_NOM() throws FileNotFoundException {
		
		
		Rule isGod_WMC_NOM = new Rule("WMC_class",">","1","AND","NOM_class","<","1","","","","");
		resultMap = evaluateNDetect.evaluationChooser(isGod_WMC_NOM);
		resultList = resultMap.values().iterator().next();
		resultString = resultMap.keySet().iterator().next();
		
		DetectionChooser chooser = new DetectionChooser();
		compareList = chooser.chooseDetectionWMC_NOM(isGod_WMC_NOM, compareEvaluation);
		compareString = "IsGod Class Detection";
		
		assertEquals(compareString,resultString);
		for(int i = 0; i < resultList.size();i++) 
			assertEquals(compareList.get(i).toString(),resultList.get(i).toString());
		
	}

	@Test
	void testEvaluationChooserWMC_LOC() throws FileNotFoundException {
		
		
		Rule isGod_WMC_LOC = new Rule("WMC_class",">","1","AND","LOC_class","<","1","","","","");
		resultMap = evaluateNDetect.evaluationChooser(isGod_WMC_LOC);
		resultList = resultMap.values().iterator().next();
		resultString = resultMap.keySet().iterator().next();
		
		DetectionChooser chooser = new DetectionChooser();
		compareList = chooser.chooseDetectionWMC_LOC(isGod_WMC_LOC, compareEvaluation);
		compareString = "IsGod Class Detection";
		
		assertEquals(compareString,resultString);
		for(int i = 0; i < resultList.size();i++) 
			assertEquals(compareList.get(i).toString(),resultList.get(i).toString());
		
	}
	
	@Test
	void testEvaluationChooserNOM_LOC() throws FileNotFoundException {
		
		
		Rule isGod_NOM_LOC = new Rule("NOM_class",">","1","AND","LOC_class","<","1","","","","");
		resultMap = evaluateNDetect.evaluationChooser(isGod_NOM_LOC);
		resultList = resultMap.values().iterator().next();
		resultString = resultMap.keySet().iterator().next();
		
		DetectionChooser chooser = new DetectionChooser();
		compareList = chooser.chooseDetectionNOM_LOC(isGod_NOM_LOC, compareEvaluation);
		compareString = "IsGod Class Detection";
		
		assertEquals(compareString,resultString);
		for(int i = 0; i < resultList.size();i++) 
			assertEquals(compareList.get(i).toString(),resultList.get(i).toString());
		
	}

	@Test
	void testEvaluationChooserIsGod() throws FileNotFoundException {
		
		
		Rule isGod = new Rule("WMC_class",">","1","AND","NOM_class","<","1","AND","LOC_class",">","1");
		resultMap = evaluateNDetect.evaluationChooser(isGod);
		resultList = resultMap.values().iterator().next();
		resultString = resultMap.keySet().iterator().next();
		
		DetectionChooser chooser = new DetectionChooser();
		compareList = chooser.chooseDetectionWMC_NOM_LOC(isGod, compareEvaluation);
		compareString = "IsGod Class Detection";
		
		assertEquals(compareString,resultString);
		for(int i = 0; i < resultList.size();i++) 
			assertEquals(compareList.get(i).toString(),resultList.get(i).toString());
		
	}
	
	@Test
	void testEvaluationChooserIsNull2() throws FileNotFoundException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		directoryExplorer = new DirExplorer(projectDir);
		results = directoryExplorer.exploreAndExtract();
		
		
		evaluateNDetect = new EvaluateAndDetect();
		evaluateNDetect.setActualmetrics(results);
		
		Rule isGod = new Rule("WMC_class",">","1","AND","LOC_class","<","1","AND","LOC_class",">","1");
		resultMap = evaluateNDetect.evaluationChooser(isGod);
	
		assertNull(resultMap);
	
	
	}
	
	@Test
	void testEvaluationChooserIsNull3() throws FileNotFoundException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		directoryExplorer = new DirExplorer(projectDir);
		results = directoryExplorer.exploreAndExtract();
		
		
		evaluateNDetect = new EvaluateAndDetect();
		evaluateNDetect.setActualmetrics(results);
		
		Rule isGod = new Rule("NOM_class",">","1","AND","LOC_class","<","1","AND","LOC_class",">","1");
		resultMap = evaluateNDetect.evaluationChooser(isGod);
	
		assertNull(resultMap);
	
	
	}
	
	
	@Test
	void testIsGod() throws FileNotFoundException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		directoryExplorer = new DirExplorer(projectDir);
		results = directoryExplorer.exploreAndExtract();
		
		
		evaluateNDetect = new EvaluateAndDetect();
		evaluateNDetect.setActualmetrics(results);
		
		Rule isGod = new Rule("WMC_class",">","1","AND","NOM_class","<","1","","","","");
		Boolean detection = isGod.isGodClass();
	
		assertEquals(false,detection);
	
	
	}
	
	
	@Test
	void testIsNOM_LOC() throws FileNotFoundException {
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com");
		directoryExplorer = new DirExplorer(projectDir);
		results = directoryExplorer.exploreAndExtract();
		
		
		evaluateNDetect = new EvaluateAndDetect();
		evaluateNDetect.setActualmetrics(results);
		
		Rule isGod = new Rule("NOM_class",">","1","AND","NOM_class","<","1","","","","");
		Boolean detection = isGod.isNOMAndLOC();
	
		assertEquals(false,detection);
	
	
	}
	
	

}
