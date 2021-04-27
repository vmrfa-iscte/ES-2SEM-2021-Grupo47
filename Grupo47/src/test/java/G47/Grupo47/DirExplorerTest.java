package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import extraction.DirExplorer;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DirExplorerTest extends TestCase {
	
	protected File projectDir,secondProject;
	protected DirExplorer directoryExplorer,secondDirectoryExplorer;
	
	protected void setUp() throws Exception {
		super.setUp();
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src");
		secondProject = new File("C:\\Users\\rui.fontoura\\OneDrive - ISCTE-IUL\\Documents\\eclipseenterprise-workspace\\SID2021\\src");
		directoryExplorer = new DirExplorer(projectDir);
		secondDirectoryExplorer = new DirExplorer(secondProject);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	
	public void testCreation() {
		assertNotNull(directoryExplorer);
		assertNotNull(secondDirectoryExplorer);
			
	}
	
	public void testExplore() {
		try {
			directoryExplorer.exploreAndExtract();
			secondDirectoryExplorer.exploreAndExtract();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
