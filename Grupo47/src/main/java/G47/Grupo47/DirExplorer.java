package G47.Grupo47;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class DirExplorer {
	public interface FileHandler {
        ArrayList<Metricas> handle(int level, String path, File file,ArrayList<Metricas> metricas);
    }
 
    private FileHandler fileHandler;
    private ArrayList<Metricas> metricas;

    public DirExplorer(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.metricas = new ArrayList<>();
    }
    
    public DirExplorer(FileHandler fileHandler,ArrayList<Metricas> metricas) {
    	this.fileHandler = fileHandler;
    	this.metricas = metricas;
    }
    
    public ArrayList<Metricas> getMetricas(){
    	return metricas;
    }
    
    public void explore(File root) {
        explore(0, "", root);
    }
    private void explore(int level, String path,File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (path.endsWith(".java")) {
                metricas = fileHandler.handle(level, path, file,metricas);
            }
        }
    }
    
    public static List<MethodDeclaration> getMethodList(CompilationUnit comp,String nameClass) {
		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(nameClass);
		
		List<MethodDeclaration> method = null;
		if(!cid.isPresent()) {
			cid = comp.getInterfaceByName(nameClass);
			
			if(!cid.isPresent()) {
				Optional<EnumDeclaration> ed = comp.getEnumByName(nameClass);
				method = ed.get().getMethods();
			}else {
				method = cid.get().getMethods();
			}
		}else {
			method = cid.get().getMethods();
		}
		
		return method;
	}
    
    
    public static List<ConstructorDeclaration> getConstructors(CompilationUnit comp,String nameClass) {
		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(nameClass);
		List<ConstructorDeclaration> method = null;
		if(!cid.isPresent()) {
			cid = comp.getInterfaceByName(nameClass);
			
			if(!cid.isPresent()) {
				Optional<EnumDeclaration> ed = comp.getEnumByName(nameClass);
				method = ed.get().getConstructors();
			}else {
				method = cid.get().getConstructors();
			}
		}else {
			method = cid.get().getConstructors();
		}
		
		return method;
	}
    


}
