package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import G47.Grupo47.DirExplorer.FileHandler;

public class LOC_class  {
	
	private File f;
	private String path;

	public LOC_class(File f, String path) {
		this.f = f;
		this.path = path;
	}


	
	public ArrayList<Metricas> extrair_LOC_class(File f,String path,ArrayList<Metricas> metricas) throws FileNotFoundException {
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		JavaParser jp  = new JavaParser();
		ParseResult<CompilationUnit> cu = jp.parse(f);
		int sum = 0;
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<Node> nodes = comp.getChildNodes();
			for(Node n: nodes) {
				int  linhas= n.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
				sum = sum +linhas;
			}	
			for(Metricas m: metricas) {
				if(m.getClasse().equals(f.getName().replace(".java", "")) && m.getPacote().equals(packageClass)){
					m.setLOC_class(sum);
				}
			}	
			extrair_LOC_class_inner(metricas,f,packageClass,comp);
		}
		return metricas;
	}
	
	
	public ArrayList<Metricas> extrair_LOC_class_inner(ArrayList<Metricas> metricas,File f, String packageClass,CompilationUnit cu){
		String nameClass = "";
		for(TypeDeclaration<?> type : cu.getTypes()) {
	        List<BodyDeclaration<?>> members = type.getMembers();
	        for(BodyDeclaration member : members) {
	            if(member.isClassOrInterfaceDeclaration()) {
	            	int sum = 0;            
	                if(member.asClassOrInterfaceDeclaration().getNameAsString() != f.getName().replace(".java", "")) {
	                	List<Node> nodes = member.asClassOrInterfaceDeclaration().getChildNodes();	          	
	                	for(Node n: nodes) {
	                		int  linhas= n.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
	        				sum = sum +linhas;	
	                	}                	
	                	nameClass = f.getName().replace(".java", "")+"."+member.asClassOrInterfaceDeclaration().getNameAsString();
	                	for(Metricas m: metricas) {
	 	        			if(m.getClasse().equals(nameClass) && m.getPacote().equals(packageClass)){
	 	        				m.setLOC_class(sum);
	 	        			}
	 	        		}
	                		
	                }
	
	            }
	                
	        }  
	    }
		return metricas;
		
	}

}