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

public class LOC_method implements FileHandler {
	
	public static void main(String[] args) {
		File projectDir = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10\\src\\com\\jasml");
		DirExplorer de = new DirExplorer(new LOC_method());
		de.explore(projectDir);
	}
	
	@Override
	public void handle(int level, String path, File file) {
		try {
			LOC(file,path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void LOC(File f,String path) throws FileNotFoundException {
		String[] path2 = path.split("/");
		String packageClass = path2[1];
		JavaParser jp  = new JavaParser();
		ParseResult<CompilationUnit> cu = jp.parse(f);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			List<MethodDeclaration> method = getMethodList(comp,f);
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
	}
	
	
	private List<MethodDeclaration> getMethodList(CompilationUnit comp,File f) {
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


