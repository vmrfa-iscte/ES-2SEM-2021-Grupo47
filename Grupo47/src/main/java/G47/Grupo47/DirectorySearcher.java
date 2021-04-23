package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DirectorySearcher {
	
    
	private File fileToSearch; //Directory chosen to search for .java Files
    private int currentMethod_id; //Last method's method_id to keep track of current method_id
    private static final int START_LEVEL = 0,FIRST_METHOD_ID = 1;
    private static final String FILE_ENDING = ".java";
    private ArrayList<MethodMetrics> accumulateMetrics;

    public DirectorySearcher(File fileToSearch) {
        this.fileToSearch = fileToSearch;
        this.currentMethod_id = FIRST_METHOD_ID;
    }
           
    public ArrayList<MethodMetrics> exploreAndExtract() throws FileNotFoundException { return exploreAndExtract(START_LEVEL, "",fileToSearch); }
    
    public void verifyAndExtractMetrics(File currentFile,String filePath) throws FileNotFoundException {
    	if(filePath.endsWith(FILE_ENDING)) { //Verify if the currentFile is a java file
    		ExtractMetrics extractMetricsFromFile = new ExtractMetrics(currentFile); //Creating ExtractMetrics object with currentFile
    		extractMetricsFromFile.doExtractMetrics(accumulateMetrics,currentMethod_id); //Extract metrics for given file (currentFile) given the current method_id and metrics list
    		currentMethod_id = extractMetricsFromFile.getCurrentMethodID(); //Update current method_id
    	}
    }
    
    public ArrayList<MethodMetrics> exploreAndExtract(int level, String currentPath, File currentFile) throws FileNotFoundException {
		if (currentFile.isDirectory())
			goThroughFiles(currentFile, level, currentPath);
		else
			verifyAndExtractMetrics(currentFile, currentPath);
		return accumulateMetrics;
	}
    
    public void goThroughFiles(File currentFile, int level, String currentPath)
			throws FileNotFoundException {
		for (File child : currentFile.listFiles())
			exploreAndExtract(level + 1, currentPath + "/" + child.getName(), child);
	}
    
    
}
