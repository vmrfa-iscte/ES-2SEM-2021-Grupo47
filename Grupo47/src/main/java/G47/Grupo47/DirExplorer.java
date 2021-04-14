package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class DirExplorer {
	public interface FileHandler {
        ArrayList<Metrics> handle(int level, String path, File file,ArrayList<Metrics> metrics);
    }
 
    private File file;
    private ArrayList<Metrics> metrics;
    private int method_id;

    public DirExplorer(File file) {
        this.file = file;
        this.metrics = new ArrayList<>();
        this.method_id = 1;
    }
           
    public ArrayList<Metrics> explore() throws FileNotFoundException {
        return explore(0, "", file);
        
    }
    private ArrayList<Metrics> explore(int level, String path,File file) throws FileNotFoundException {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (path.endsWith(".java")) {
            	
                ExtractMetrics a = new ExtractMetrics(file,path);
                a.extrair_Metricas(metrics,method_id);
                method_id = a.getCurrentMethodID();
            }
        }
        return metrics;
    }
    

    

}
