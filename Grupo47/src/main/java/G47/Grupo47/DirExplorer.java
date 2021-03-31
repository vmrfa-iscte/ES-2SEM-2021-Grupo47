package G47.Grupo47;

import java.io.File;
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
    
    public static List<MethodDeclaration> getMethodList(CompilationUnit comp,File f) {
		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
		List<MethodDeclaration> method = null;
		if(cid.isEmpty()) {
			cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
			method = cid.get().getMethods();
			if(cid.isEmpty()) {
				Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
				method = ed.get().getMethods();
			}
		}else {
			method = cid.get().getMethods();
		}
		
		return method;
	}
    
    
    public static List<ConstructorDeclaration> getConstructors(CompilationUnit comp,File f) {
		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
		List<ConstructorDeclaration> method = null;
		if(cid.isEmpty()) {
			cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
			method = cid.get().getConstructors();
			if(cid.isEmpty()) {
				Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
				method = ed.get().getConstructors();
			}
		}else {
			method = cid.get().getConstructors();
		}
		
		return method;
	}
    


}
