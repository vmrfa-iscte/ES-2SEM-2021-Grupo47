package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

public class WMC_class {
	public static void main(String[] args) {
		try {
			cyclicMethod();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void cyclicMethod() throws FileNotFoundException {
		File dir = new File("C:\\Users\\alinc\\OneDrive\\Ambiente de Trabalho\\Medicao.java");
		JavaParser jp = new JavaParser();
		int i= 0;
		ParseResult<CompilationUnit> cu = jp.parse(dir);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<Node> nodes = comp.getChildNodes();
			System.out.println(nodes.size());
			for(Node n: nodes) {
				
				List<Node> nodeChildren=n.getChildNodes();
				for(Node n2: nodeChildren) {
					System.out.println(n2.toString());
					System.out.println("------------");
//					System.out.println(n2.toString());
//					if(n2.toString().contains("if") || n2.toString().contains("for") || n2.toString().contains("while") || n2.toString().contains("case") ) {
//					i++;	
//					}
					if(n2.toString().contains("public")) {
						i++;
					}
				}
				
			}
			System.out.println("CyclicMethod returns :" +i);
		}
		
	}

}
