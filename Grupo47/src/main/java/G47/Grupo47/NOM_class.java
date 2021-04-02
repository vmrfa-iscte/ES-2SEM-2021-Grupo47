
package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.math3.ode.events.FieldEventHandler;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import G47.Grupo47.DirExplorer.FileHandler;
public class NOM_class {


	
	
	public ArrayList<Metricas> extractNOMclass(File f, String path,ArrayList<Metricas> metricas) throws FileNotFoundException {
		JavaParser jp = new JavaParser();
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		int sum = 0;
		ParseResult<CompilationUnit> compUnit = jp.parse(f);
		if(compUnit.isSuccessful()) {
			CompilationUnit comp=compUnit.getResult().get();
			List<MethodDeclaration> md = getMethodList(comp,f);
			List<ConstructorDeclaration> coid = getConstructors(comp, f);
			sum= md.size()+coid.size();
//			System.out.println("Numero de metodos é:" + sum);
		}
		
		for(Metricas m: metricas) {
			if(m.getClasse().equals(f.getName().replace(".java", "")) && m.getPacote().equals(packageClass) ) {
				m.setNOM_class(sum);
			}
		}
		
		
		return metricas;
		
		
	}
	private List<MethodDeclaration> getMethodList(CompilationUnit comp,File f) {
		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
		List<MethodDeclaration> method = null;
		if(!cid.isPresent()) {
			cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
			method = cid.get().getMethods();
			if(!cid.isPresent()) {
				Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
				method = cid.get().getMethods();
			}
		}else {
			method = cid.get().getMethods();
		}
		
		return method;
	}
	public List<ConstructorDeclaration> getConstructors(CompilationUnit comp,File f) {
		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
		List<ConstructorDeclaration> method = null;
		if(!cid.isPresent()) {
			cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
			method = cid.get().getConstructors();
			if(!cid.isPresent()) {
				Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
				method = ed.get().getConstructors();
			}
		}else {
			method = cid.get().getConstructors();
		}
		
		return method;
	}
	
		
	
}

