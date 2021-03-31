package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class LOC_method {
	private String path;
	private File f;
	
	public LOC_method(File f, String path) {
		this.f = f;
		this.path = path;
		
	}

	public List<Metodo> extrair_LOC_method(File f,String path) throws FileNotFoundException {
		List<Metodo> metodos = new ArrayList<>();
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		JavaParser jp  = new JavaParser();
		ParseResult<CompilationUnit> cu = jp.parse(f);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<MethodDeclaration> method = DirExplorer.getMethodList(comp,f);
			List<ConstructorDeclaration> constructors = DirExplorer.getConstructors(comp, f);
			makeMetodosConstrutores(constructors,comp,metodos,packageClass,f);
			makeMetodosMethods(method,comp,metodos,packageClass,f);
			
		}

		return metodos;
	}
	
	
	public void makeMetodosConstrutores(List<ConstructorDeclaration> method,CompilationUnit comp,List<Metodo> metodos,String packageClass,File f) {
		if(method.size() > 0) {
			for(ConstructorDeclaration md: method) {
				int sum = 0;
				
				for(Node noode: md.getChildNodes()) {
					if(noode.toString().startsWith("{") && noode.toString().endsWith("}")) {
						int tamanho = noode.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
						sum = sum + tamanho;
					}
				}
				
				String nomeMetodoPar = getClassNameWithParameters(md.getNameAsString(),md.getParameters());
				Metodo m = new Metodo(nomeMetodoPar,f.getName().replace(".java", ""),packageClass,sum);
				metodos.add(m);
				System.out.println("Package: "+packageClass+" // Classe: "+f.getName().replace(".java", "")+" // Construtor: "+nomeMetodoPar+" // Linhas: "+sum);
			}
				
			}
		
	}
	
	public void makeMetodosMethods(List<MethodDeclaration> method,CompilationUnit comp,List<Metodo> metodos,String packageClass,File f) {
		if(method.size() > 0) {
			for(MethodDeclaration md: method) {
				int sum = 0;
			
				for(Node noode: md.getChildNodes()) {
					if(noode.toString().startsWith("{") && noode.toString().endsWith("}")) {
						int tamanho = noode.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
						sum = sum + tamanho;
					}
				}
				
				String nomeMetodoPar = getClassNameWithParameters(md.getNameAsString(),md.getParameters());
				Metodo m = new Metodo(nomeMetodoPar,f.getName().replace(".java", ""),packageClass,sum);
				metodos.add(m);
				System.out.println("Package: "+packageClass+" // Classe: "+f.getName().replace(".java", "")+" // Método: "+nomeMetodoPar+" // Linhas: "+sum);
			}
				
			}
		
	}
	
	public boolean isClass(CompilationUnit comp,String ClassName) {
		return(!comp.getClassByName(ClassName).isEmpty());
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
