package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

public class ExtractMetrics {

	private String path;
	private File file;
	private int method_id;


	public ExtractMetrics(File file, String path) {
		this.file = file;
		this.path = path;
	}

	public ArrayList<Metrics> extrair_Metricas(ArrayList<Metrics> metrics,int method_id) throws FileNotFoundException {
		String packageClass = getPackageName();
		JavaParser jp  = new JavaParser();
		ParseResult<CompilationUnit> cu = jp.parse(file);
		int LOC_method,CYCLO_method,LOC_class,NOM_class,WMC_class = 0;
		String className = "";
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			
			for(ClassOrInterfaceDeclaration type : comp.findAll(ClassOrInterfaceDeclaration.class)) {
				if(type.getNameAsString().equals(file.getName().replace(".java", ""))) {
					className = type.getNameAsString();
				}else {
					className = file.getName().replace(".java", "")+"."+type.getNameAsString();
				}
				WMC_class = getClassComplexity(type.getMethods(),type.getConstructors());
				NOM_class = getNOM_class(type.getMethods(),type.getConstructors());
				LOC_class = getLOC_classCID(type);
				for(ConstructorDeclaration md: type.getConstructors()) {
					LOC_method = getLOC_method_Cons(md);
					CYCLO_method = getConstructorComplexity(md);

					Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass, method_id,LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
					metrics.add(metric);
					method_id++;
				}
				for(MethodDeclaration md: type.getMethods()) {
					LOC_method = getLOC_method_Met(md);
					CYCLO_method = getMethodComplexity(md);
					Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass,method_id, LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
					metrics.add(metric);
					method_id++;
				}
				

			}
			for(EnumDeclaration type : comp.findAll(EnumDeclaration.class)) {
				if(type.getNameAsString().equals(file.getName().replace(".java", ""))) {
					className = type.getNameAsString();
				}else {
					className = file.getName().replace(".java", "")+"."+type.getNameAsString();
				}
				WMC_class = getClassComplexity(type.getMethods(),type.getConstructors());
				NOM_class = type.getMethods().size() + type.getConstructors().size();
				LOC_class = getLOC_classENUM(type);
				for(MethodDeclaration md: type.getMethods()) {
					LOC_method = getLOC_method_Met(md);
					CYCLO_method = getMethodComplexity(md);
					Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass,method_id, LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
					metrics.add(metric);
					method_id++;
				}
				for(ConstructorDeclaration md: type.getConstructors()) {
					LOC_method = getLOC_method_Cons(md);
					CYCLO_method = getConstructorComplexity(md);

					Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass, method_id,LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
					metrics.add(metric);
					method_id++;
				}
			}

		}else {
			
		}
		this.method_id = method_id;
		return metrics;
	}


	int getLOC_method_Cons(ConstructorDeclaration md) {
		return (md.getEnd().get().line - md.getBegin().get().line)+1;
	}

	protected int getLOC_method_Met(MethodDeclaration md) {
		return (md.getEnd().get().line - md.getBegin().get().line)+1;
	}
	
	protected int getNOM_class(List<MethodDeclaration> md, List<ConstructorDeclaration> cd) {
		return md.size()+ cd.size();
	}

	protected String getMethodNameWithParameters(String ClassName,NodeList<Parameter> nodeList) {
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

	protected int getLOC_classCID(ClassOrInterfaceDeclaration cid) throws FileNotFoundException {
		return (cid.getEnd().get().line - cid.getBegin().get().line)+1;
	}
	
	protected int getLOC_classENUM(EnumDeclaration ed) {
		return (ed.getEnd().get().line - ed.getBegin().get().line)+1;
	}

	protected int getMethodComplexity(MethodDeclaration md) {
		int complex = 1;
		int numbif = getCycloComplex("if",md.toString());
		int numbwhile = getCycloComplex("while",md.toString());
		int numbfor =getCycloComplex("for",md.toString());
		int numbelse = getCycloComplex("else",md.toString());
		int numbcase = getCycloComplex("case",md.toString());
		int numbdefault = getCycloComplex("default",md.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;

	}

	protected int getConstructorComplexity(ConstructorDeclaration md) {
		int complex = 1;
		int numbif = getCycloComplex("if",md.toString());
		int numbwhile = getCycloComplex("while",md.toString());
		int numbfor =getCycloComplex("for",md.toString());
		int numbelse = getCycloComplex("else",md.toString());
		int numbcase = getCycloComplex("case",md.toString());
		int numbdefault = getCycloComplex("default",md.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;

	}

	protected int getCycloComplex (String wordToSearch, String data) {
		int count = 0;
		for (int index = data.indexOf(wordToSearch); 
				index != -1; 
				index = data.indexOf(wordToSearch, index + 1)) {
			count++;
		}
		return count;
	}

	protected int getClassComplexity(List<MethodDeclaration> md,List<ConstructorDeclaration> cd) {
		int complexity = 0;
		for(MethodDeclaration metdec: md) {
			complexity = complexity + getMethodComplexity(metdec);
		}
		for(ConstructorDeclaration consdec: cd) {
			complexity = complexity + getConstructorComplexity(consdec);
		}
		return complexity;

	}

	protected String getPackageName() {
		String packageName = "";
		boolean src = false;
		String[] separated = file.getAbsolutePath().split(Pattern.quote(File.separator));
		for(int i = 0; i< separated.length-1;i++) {
			if(src && i <= separated.length-2) {
				if(i < separated.length-2) {
					packageName = packageName + separated[i] + ".";
				}else {
					packageName = packageName + separated[i];							
				}
			}
			if(separated[i].contains("src")) src = true;
		}
		if(packageName.equals("") && file.getAbsolutePath().contains("src")) packageName = "Default Package";

			return packageName;
		
	}
	
	public int getCurrentMethodID() {
		return method_id;
	}

}
