package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;

import G47.Grupo47.DirExplorer.FileHandler;

public class LOC_class  {
	
	private File f;
	private String path;

	public LOC_class(File f, String path) {
		this.f = f;
		this.path = path;
	}


	
	public int LOC(File f,String path) throws FileNotFoundException {
		LOC_method locm = new LOC_method(f,path);
		List<Metodo> metodos = locm.LOC(f, path);
		int sum = 0;
		for(Metodo m: metodos) {
			sum = sum+ m.getLinhas();
		}
		System.out.println("Linhas totais da classe: "+sum);
		return sum;
	}

}
