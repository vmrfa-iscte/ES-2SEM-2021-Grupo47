package G47.Grupo47;

import java.io.File;

public class DirExplorer {
	public interface FileHandler {
        void handle(int level, String path, File file);
    }
 
    private FileHandler fileHandler;

    public DirExplorer(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
    
    public void explore(File root) {
        explore(0, "", root);
    }
    private void explore(int level, String path, File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (path.endsWith(".java")) {
                fileHandler.handle(level, path, file);
            }
        }
    }

}
