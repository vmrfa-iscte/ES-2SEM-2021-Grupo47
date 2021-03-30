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
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

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
		String packageClass = path2[1];
		JavaParser jp  = new JavaParser();
		ParseResult<CompilationUnit> cu = jp.parse(f);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<MethodDeclaration> method = DirExplorer.getMethodList(comp,f);
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
									if(nn.getChildNodes().get(1).toString().contains(md.getNameAsString()) ) {
										if(index +1 <= method.size()-1)  index++;
							
										int  linhas= nn.getRange().map(range -> range.end.line - range.begin.line).orElse(0);
										Metodo m = new Metodo(md.getNameAsString(),f.getName(),packageClass,linhas);
										metodos.add(m);
										System.out.println("Package: "+packageClass+" // Classe: "+f.getName()+" // Método: "+md.getNameAsString()+" // Linhas: "+linhas);
									}
								}
							}
						}
						break;
					}

				}
			}
		}
		return metodos;
	}
	
	
	
}


