package G47.Grupo47;


import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class DirExplorer {
	private ArrayList<MethodMetrics> accumulateMetrics;

	public ArrayList<MethodMetrics> getAccumulateMetrics() {
		return accumulateMetrics;
	}

	public void setAccumulateMetrics(ArrayList<MethodMetrics> accumulateMetrics) {
		this.accumulateMetrics = accumulateMetrics;
	}

	public ArrayList<MethodMetrics> exploreAndExtract(int level, String currentPath, File currentFile,
			FileHandler fileHandler) throws FileNotFoundException {
		if (currentFile.isDirectory())
			goThroughFiles(currentFile, level, currentPath);
		else
			fileHandler.verifyAndExtractMetrics(currentFile, currentPath);
		return accumulateMetrics;
	}

	public void goThroughFiles(File currentFile, int level, String currentPath, FileHandler fileHandler)
			throws FileNotFoundException {
		for (File child : currentFile.listFiles())
			exploreAndExtract(level + 1, currentPath + "/" + child.getName(), child, fileHandler);
	}
}