package extraction;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.MethodMetrics;
import junit.framework.Assert;

public class DirExplorerTest {
	
	protected File projectDirectory,classIntegerArray,classOpcodeHelper,classOpcodeInfo,classOpcodeLoader,classUtil;
	protected DirExplorer directoryExplorer;
	protected ArrayList<MethodMetrics> extractedMetrics,resultsExtract;

	@Test
	public void testDirExplorer() {
		directoryExplorer = new DirExplorer(projectDirectory);
		assertNotNull(directoryExplorer);
	}

	@Test
	public void testExploreAndExtract() throws FileNotFoundException {
		classIntegerArray = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\IntegerArray.java");
		classOpcodeHelper = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\OpcodeHelper.java");
		classOpcodeInfo = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\OpcodeInfo.java");
		classOpcodeLoader = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\OpcodeLoader.java");
		classUtil = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\Util.java");
		
		resultsExtract = new ArrayList<MethodMetrics>();
		projectDirectory = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper");
		directoryExplorer = new DirExplorer(projectDirectory);
		extractedMetrics = directoryExplorer.exploreAndExtract();
		
		ExtractMetrics extractIntegerArray = new ExtractMetrics(classIntegerArray);
		ArrayList<MethodMetrics> resultsIntegerArray = extractIntegerArray.doExtractMetrics(resultsExtract, 0);
		
		ExtractMetrics extractOpcodeHelper = new ExtractMetrics(classOpcodeHelper);
		ArrayList<MethodMetrics> resultsOpcodeHelper = extractOpcodeHelper.doExtractMetrics(resultsIntegerArray, extractIntegerArray.getCurrentMethodID());
		
		ExtractMetrics extractOpcodeInfo = new ExtractMetrics(classOpcodeInfo);
		ArrayList<MethodMetrics> resultsOpcodeInfo = extractOpcodeInfo.doExtractMetrics(resultsOpcodeHelper, extractOpcodeHelper.getCurrentMethodID());
		
		ExtractMetrics extractOpcodeLoader = new ExtractMetrics(classOpcodeLoader);
		ArrayList<MethodMetrics> resultsOpcodeLoader = extractOpcodeLoader.doExtractMetrics(resultsOpcodeInfo, extractOpcodeInfo.getCurrentMethodID());
		
		ExtractMetrics extractUtil = new ExtractMetrics(classUtil);
		ArrayList<MethodMetrics> resultsUtil = extractUtil.doExtractMetrics(resultsOpcodeLoader, extractOpcodeLoader.getCurrentMethodID());
		assertEquals(resultsUtil.size(),extractedMetrics.size(),"Tamanho Diferente");
		for(int i = 0; i < resultsUtil.size(); i++) {
			assertEquals(resultsUtil.get(i).toString(),extractedMetrics.get(i).toString());
		}
	}

}
