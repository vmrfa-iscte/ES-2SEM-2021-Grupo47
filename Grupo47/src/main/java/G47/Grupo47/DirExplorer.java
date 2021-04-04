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
 
    private ArrayList<Metrics> metrics;
    private File file;

    public DirExplorer(File file) {
    	this.file = file;
        this.metrics = new ArrayList<>();
    }
    
    public ArrayList<Metrics> explore() {
        return explore(0, "", file);
    }
    private ArrayList<Metrics> explore(int level, String path,File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (path.endsWith(".java")) {
            	System.out.println(level+"  "+path+"  ");
//                metrics = fileHandler.handle(level, path, file,metrics);
            	ExtractMetrics em = new ExtractMetrics(file,path);
            	try {
					metrics = em.extrair_Metricas(metrics);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
            }
        }
        return metrics;
    }
    
    

}
