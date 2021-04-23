package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileHandler {
	
    private DirExplorer dirExplorer = new DirExplorer();
	private File fileToSearch; //Directory chosen to search for .java Files
    private int currentMethod_id; //Last method's method_id to keep track of current method_id
    private static final int START_LEVEL = 0,FIRST_METHOD_ID = 1;
    private static final String FILE_ENDING = ".java";

    public FileHandler(File fileToSearch) {
        this.fileToSearch = fileToSearch;
        dirExplorer.setAccumulateMetrics(new ArrayList<>());
        this.currentMethod_id = FIRST_METHOD_ID;
    }
           
    public ArrayList<MethodMetrics> exploreAndExtract() throws FileNotFoundException { return dirExplorer.exploreAndExtract(START_LEVEL, "",fileToSearch, this); }
    
    public void verifyAndExtractMetrics(File currentFile,String filePath) throws FileNotFoundException {
    	if(filePath.endsWith(FILE_ENDING)) { //Verify if the currentFile is a java file
    		ExtractMetrics extractMetricsFromFile = new ExtractMetrics(currentFile); //Creating ExtractMetrics object with currentFile
    		extractMetricsFromFile.doExtractMetrics(dirExplorer.getAccumulateMetrics(),currentMethod_id); //Extract metrics for given file (currentFile) given the current method_id and metrics list
    		currentMethod_id = extractMetricsFromFile.getCurrentMethodID(); //Update current method_id
    	}
    }
}
