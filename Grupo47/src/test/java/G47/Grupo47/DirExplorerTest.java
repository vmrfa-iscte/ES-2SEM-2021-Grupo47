package G47.Grupo47;

import java.io.File;
import java.util.ArrayList;

import G47.Grupo47.DirExplorer.FileHandler;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DirExplorerTest extends TestCase {
	
	protected File projectDir;
	protected FileHandler filehandler;
	protected DirExplorer de,de2;
	protected ArrayList<Metrics> metrics;
	protected Metrics m;
	protected ExtractMetrics em;
	
	protected void setUp() throws Exception {
		super.setUp();
		projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
		de = new DirExplorer(projectDir);
		metrics = new ArrayList<>();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	
	public void testCreation() {
		assertNotNull(de);
	}
	
	public void testExplore() {
		
	}
	
	
	

}
