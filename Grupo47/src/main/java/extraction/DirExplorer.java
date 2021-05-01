package extraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import classes.MethodMetrics;

/**
 * Classe responsavel por percorrer diretorias ate chegar a um ficheiro .java
 * @author Vasco Fontoura
 * @version 7
 *
 */
public class DirExplorer {
	
    private File fileToSearch; 
    private ArrayList<MethodMetrics> accumulateMetrics; // Lista onde as métricas vão ser acumuladas
    private int currentMethod_id; // Method id do último método para não haver ids repetidos
    private static int START_LEVEL = 0,FIRST_METHOD_ID = 1;
    private static String FILE_ENDING = ".java";

    /**
     * Construtor
     * @param fileToSearch uma diretoria escolhida para procurar ficheiros .java
     */
    public DirExplorer(File fileToSearch) {
        this.fileToSearch = fileToSearch;
        this.accumulateMetrics = new ArrayList<>();
        this.currentMethod_id = FIRST_METHOD_ID;
    }
           
    /**
     * Inicia a pesquisa de ficheiros
     * @return uma lista com os resultados da extracao de metricas
     * @throws FileNotFoundException excecao
     */
    public ArrayList<MethodMetrics> exploreAndExtract() throws FileNotFoundException {
        return exploreAndExtract(START_LEVEL, "",fileToSearch);
    }
    
    /**
     * Realiza a pesquisa, caso encontre uma diretoria chama o metodo goThroughFiles (recursivo), caso
     * encontre um ficheiro .java incia a extracao de metricas e acumula-as numa lista
     * @param level nivel
     * @param currentPath caminho do último ficheiro visitado
     * @param currentFile último ficheiro visitado
     * @return uma lista com os resultados da extração de métricas
     * @throws FileNotFoundException
     */
    private ArrayList<MethodMetrics> exploreAndExtract(int level, String currentPath,File currentFile) throws FileNotFoundException {
    	if (currentFile.isDirectory()) goThroughFiles(currentFile,level,currentPath); //If currentFile is a directory keep searching for files
    	else verifyAndExtractMetrics(currentFile,currentPath); //If current file is not a directory verify and extract metrics for that file
    	return accumulateMetrics;
    }
    
    /**
     * Verifica se o ficheiro e um ficheiro java e caso seja extrai metricas
     * @param currentFile ultimo ficheiro visitado
     * @param filePath caminho do ficheiro
     * @throws FileNotFoundException
     */
    private void verifyAndExtractMetrics(File currentFile,String filePath) throws FileNotFoundException {
    	if(filePath.endsWith(FILE_ENDING)) { //Verify if the currentFile is a java file
    		ExtractMetrics extractMetricsFromFile = new ExtractMetrics(currentFile); //Creating ExtractMetrics object with currentFile
    		extractMetricsFromFile.doExtractMetrics(accumulateMetrics,currentMethod_id); //Extract metrics for given file (currentFile) given the current method_id and metrics list
    		currentMethod_id = extractMetricsFromFile.getCurrentMethodID(); //Update current method_id
    	}
    }
    
    /**
     * Chama o metodo exploreAndExtract caso o ficheiro visitado seja uma diretoria
     * @param currentFile ultimo ficheiro visitado
     * @param level nivel
     * @param currentPath caminho do ultimo ficheiro visitado
     * @throws FileNotFoundException
     */
    private void goThroughFiles(File currentFile,int level,String currentPath) throws FileNotFoundException {
    	for (File child : currentFile.listFiles()) {
			exploreAndExtract(level + 1, currentPath + "/" + child.getName(), child); //Recursive, going through every file inside given file (currentFile) when its a Directory
		}
    }
}
