package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.util.SystemOutLogger;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import G47.Grupo47.DirExplorer.FileHandler;

public class WMC_CYCLO {


	public ArrayList<Metricas> cyclicMethod(File dir, String path,ArrayList<Metricas> metricas) throws FileNotFoundException {
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		JavaParser jp = new JavaParser();
		int i= 0;
		ParseResult<CompilationUnit> cu = jp.parse(dir);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<MethodDeclaration> ld= getMethodList(comp, dir);
			List<ConstructorDeclaration> ld2 = DirExplorer.getConstructors(comp,dir.getName().replace(".java", ""));
			int co=0;
			for(MethodDeclaration md: ld) {
				for(Metricas m: metricas) {

					if(m.getClasse().equals(dir.getName().replace(".java", "")) && m.getPacote().equals(packageClass) && m.getNome_metodo().equals(getClassNameWithParameters(md.getNameAsString(),md.getParameters()) )) {

						m.setCYCLO_method(getMethodComplexity(md));
					}
				}
				co=co+getMethodComplexity(md);
			}
			for(ConstructorDeclaration md: ld2) {
				for(Metricas m: metricas) {

					if(m.getClasse().equals(dir.getName().replace(".java", "")) && m.getPacote().equals(packageClass) && m.getNome_metodo().equals(getClassNameWithParameters(md.getNameAsString(),md.getParameters()) )) {

						m.setCYCLO_method(getConstructorComplexity(md));
					}
				}

				co=co+getConstructorComplexity(md);	
			}
			for(Metricas m: metricas) {
				if(m.getClasse().equals(dir.getName().replace(".java", "")) && m.getPacote().equals(packageClass)) {
					m.setWMC_class(co);
					
				}
			}
		}
		return metricas;

	}
	private int getMethodComplexity(MethodDeclaration md) {
	int complex = 1;
	int numbif = cycloComplex("if",md.toString());
	int numbwhile = cycloComplex("while",md.toString());
	int numbfor =cycloComplex("for",md.toString());
	int numbelse = cycloComplex("else",md.toString());
	int numbcase = cycloComplex("case",md.toString());
	int numbdefault = cycloComplex("default",md.toString());
	
	return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	
	}
	
	private int getConstructorComplexity(ConstructorDeclaration md) {
		int complex = 1;
		int numbif = cycloComplex("if",md.toString());
		int numbwhile = cycloComplex("while",md.toString());
		int numbfor =cycloComplex("for",md.toString());
		int numbelse = cycloComplex("else",md.toString());
		int numbcase = cycloComplex("case",md.toString());
		int numbdefault = cycloComplex("default",md.toString());
		
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	
	}
	
	public int cycloComplex (String wordToSearch, String data) {
	    
	    int count = 0;
	    for (int index = data.indexOf(wordToSearch); 
	             index != -1; 
	             index = data.indexOf(wordToSearch, index + 1)) {
	        count++;
	    }
		return count;
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
	
	public String getClassNameWithParameters(String ClassName,NodeList<Parameter> nodeList) {
		if(nodeList.size() == 0) {
			return ClassName+"()";
		}else {
			ClassName = ClassName+"(";
			for(Node n: nodeList) {
				String pars[] = n.toString().split(" ");
				String par = pars[0];
				if(nodeList.indexOf(n) == nodeList.size()-1) {
					ClassName = ClassName + par + ")";
				}else {
					ClassName = ClassName + par +",";
				}
				
			}
			return ClassName;
		}
	}



}
