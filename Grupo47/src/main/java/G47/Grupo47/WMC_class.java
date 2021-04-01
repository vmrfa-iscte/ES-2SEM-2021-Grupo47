package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import G47.Grupo47.DirExplorer.FileHandler;

public class WMC_class implements FileHandler{
	public static void main(String[] args) {
		File dir = new File("C:\\Users\\alinc\\OneDrive\\Ambiente de Trabalho\\jasml_0.10\\src\\com\\jasml");
		DirExplorer de = new DirExplorer(new WMC_class());
		de.explore(dir);
	}
	
	@Override
	public void handle(int level, String path, File file) {
		// TODO Auto-generated method stub
		try {
			cyclicMethod(file, path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void cyclicMethod(File dir, String path) throws FileNotFoundException {
		JavaParser jp = new JavaParser();
		int i= 0;
		ParseResult<CompilationUnit> cu = jp.parse(dir);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<MethodDeclaration> ld= getMethodList(comp, dir);
			int co=0;
			for(MethodDeclaration m: ld) {
				System.out.println("CyclicMethod returns :"+getMethodComplexity(m));
				co=co+getMethodComplexity(m);
			}
			System.out.println("WMC returns :" +co);
		}
		
	}
private static int getMethodComplexity(MethodDeclaration md) {
	int complex= 1;
	if(md.toString().contains("if") || md.toString().contains("for") || md.toString().contains("case") || md.toString().contains("while") ) {
		complex++;
	}
	
	
	return complex;
	}


private static List<MethodDeclaration> getMethodList(CompilationUnit comp,File f) {
	Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
	List<MethodDeclaration> method = null;
	if(cid.isEmpty()) {
		cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
		method = cid.get().getMethods();
		if(cid.isEmpty()) {
			Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
			method = cid.get().getMethods();
		}
	}else {
		method = cid.get().getMethods();
	}
	
	return method;
}



}
