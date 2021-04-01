//package G47.Grupo47;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.List;
//import java.util.Optional;
//
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.math3.ode.events.FieldEventHandler;
//
//import com.github.javaparser.JavaParser;
//import com.github.javaparser.ParseResult;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.Node;
//import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
//import com.github.javaparser.ast.body.ConstructorDeclaration;
//import com.github.javaparser.ast.body.EnumDeclaration;
//import com.github.javaparser.ast.body.MethodDeclaration;
//
//import G47.Grupo47.DirExplorer.FileHandler;
//public class NOM_class implements FileHandler{
//
//	
//	public static void main(String[] args) {
//		
//		File dir = new File("C:\\Users\\alinc\\OneDrive\\Ambiente de Trabalho\\jasml_0.10\\src\\com\\jasml");
//		DirExplorer de = new DirExplorer(new NOM_class());
//		de.explore(dir);
//	}
//	@Override
//	public void handle(int level, String path, File file) {
//		try {
//			extractNOMclass(file,path);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void extractNOMclass(File f, String path) throws FileNotFoundException {
//		
//		JavaParser jp = new JavaParser();
//		//jp.parse(f);
//		
//		ParseResult<CompilationUnit> compUnit = jp.parse(f);
//		if(compUnit.isSuccessful()) {
//			CompilationUnit comp=compUnit.getResult().get();
//			List<MethodDeclaration> md = getMethodList(comp,f);
//			List<ConstructorDeclaration> coid = getConstructors(comp, f);
//			int sum= md.size()+coid.size();
//			System.out.println("Numero de metodos é:" + sum);
//			
//		}
//		
//		
//	}
//	private static List<MethodDeclaration> getMethodList(CompilationUnit comp,File f) {
//		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
//		List<MethodDeclaration> method = null;
//		if(cid.isEmpty()) {
//			cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
//			method = cid.get().getMethods();
//			if(cid.isEmpty()) {
//				Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
//				method = cid.get().getMethods();
//			}
//		}else {
//			method = cid.get().getMethods();
//		}
//		
//		return method;
//	}
//	public static List<ConstructorDeclaration> getConstructors(CompilationUnit comp,File f) {
//		Optional<ClassOrInterfaceDeclaration> cid = comp.getClassByName(f.getName().replace(".java", ""));
//		List<ConstructorDeclaration> method = null;
//		if(cid.isEmpty()) {
//			cid = comp.getInterfaceByName(f.getName().replace(".java", ""));
//			method = cid.get().getConstructors();
//			if(cid.isEmpty()) {
//				Optional<EnumDeclaration> ed = comp.getEnumByName(f.getName().replace(".java", ""));
//				method = ed.get().getConstructors();
//			}
//		}else {
//			method = cid.get().getConstructors();
//		}
//		
//		return method;
//	}
//	
//		
//	
//}
