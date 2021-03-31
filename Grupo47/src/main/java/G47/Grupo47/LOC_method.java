package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

public class LOC_method  {
	
	private String path;
	private File f;
	
	public LOC_method(File f, String path) {
		this.f = f;
		this.path = path;
		
	}

	public List<Metodo> LOC(File f,String path) throws FileNotFoundException {
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
			makeMetodosMethods(method,comp,metodos,packageClass);
			
		}

		return metodos;
	}
	
	
	public void makeMetodosConstrutores(List<ConstructorDeclaration> method,CompilationUnit comp,List<Metodo> metodos,String packageClass,File f) {
		if(method.size() > 0) {

			List<Node> nodes = comp.getChildNodes();
			for(Node n: nodes) {
				if(n.toString().endsWith("}")) {
					List<Node> nodes1 = n.getChildNodes();
					int index = 0;
					for(Node nn: nodes1) {
						if(nn.toString().endsWith("}") && (nn.toString().startsWith("public") || nn.toString().startsWith("private") || nn.toString().startsWith("protected"))) {
							ConstructorDeclaration md = method.get(index);
							if(nn.getChildNodes().size()>1) {
								String nomeConsPar = getClassNameWithParameters(md.getNameAsString(),md.getParameters());
							
								if(nn.getChildNodes().get(1).toString().contains(md.getNameAsString()) || nn.getChildNodes().get(2).toString().contains(md.getNameAsString())) {
									if(index +1 <= method.size()-1) index++;
									
									int  linhas= nn.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
									Metodo m = new Metodo(nomeConsPar,f.getName().replace(".java", ""),packageClass,linhas+1);
									metodos.add(m);
									System.out.println("Package: "+packageClass+" // Classe: "+f.getName().replace(".java", "")+" // Construtor: "+nomeConsPar+" // Linhas: "+linhas);
								}
							}
						}

					}
					
				}

			}
		}
//			else {
//			if(isClass(comp,f.getName().replace(".java", ""))) {
//				Metodo m = new Metodo(f.getName().replace(".java", ""),f.getName(),packageClass,1);
//				metodos.add(m);
//				System.out.println("Package: "+packageClass+" // Classe: "+f.getName().replace(".java", "")+" // Construtor: "+f.getName().replace(".java", "")+" // Linhas: "+1);
//			}
//			
//			
//		}
		
	}
	
	public void makeMetodosMethods(List<MethodDeclaration> method,CompilationUnit comp,List<Metodo> metodos,String packageClass) {
		if(method.size() > 0) {
			List<Node> nodes = comp.getChildNodes();
			for(Node n: nodes) {
				if(n.toString().endsWith("}")) {
					List<Node> nodes1 = n.getChildNodes();
					int index = 0;
					for(Node nn: nodes1) {
						if(nn.toString().endsWith("}")) {
							MethodDeclaration md = method.get(index);
							if(nn.getChildNodes().size()>1) {
								
								String nomeMetodoPar = getClassNameWithParameters(md.getNameAsString(),md.getParameters());
								
						
								if(nn.getChildNodes().get(1).toString().contains(md.getNameAsString()) || nn.getChildNodes().get(2).toString().contains(md.getNameAsString()) ) {
									if(index +1 <= method.size()-1)  index++;
						
									int  linhas= nn.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
									Metodo m = new Metodo(nomeMetodoPar,f.getName().replace(".java", ""),packageClass,linhas+1);
									metodos.add(m);
									System.out.println("Package: "+packageClass+" // Classe: "+f.getName().replace(".java", "")+" // Método: "+nomeMetodoPar+" // Linhas: "+linhas);
								}
							}
						}
					}
		
				}

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


