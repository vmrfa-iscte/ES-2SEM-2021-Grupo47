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

	private File fileToExtract;
	private int method_id;
	private static int METRICS_INITIAL_VALUE = 0, COUNTER_INITIAL_VALUE = 0, COMPLEXITY_INITIAL_VALUE = 1;
	private static String SRC_FOLDER = "src", DEFAULT_PACKAGE = "Default Package", EMPTY_STRING = "", SPACE_STRING = " ", IF_CYCLE = "if",
			FOR_CYCLE = "for", WHILE_CYCLE = "while", CASE_CYCLE = "case", ELSE = "else", DEFAULT = "default", JAVA_FILE = ".java";
	private String className = EMPTY_STRING;
	private int LOC_method,CYCLO_method,LOC_class,NOM_class,WMC_class = METRICS_INITIAL_VALUE;
	private String packageClass;
	private ArrayList<Metrics> extractedMetrics;
	

	public ExtractMetrics(File file) {
		this.fileToExtract = file;
		this.packageClass = getPackageName();
	}

	public ArrayList<Metrics> doExtractMetrics(ArrayList<Metrics> extractedMetrics,int method_id) throws FileNotFoundException {
		this.method_id = method_id;
		this.extractedMetrics = extractedMetrics;
		JavaParser parseCodeFromFile  = new JavaParser();
		ParseResult<CompilationUnit> compilationUnitFromParser = parseCodeFromFile.parse(fileToExtract);
		if(compilationUnitFromParser.isSuccessful()) {
			CompilationUnit actualCompilationUnit = compilationUnitFromParser.getResult().get();
			findAllClassMetricsFromFile(actualCompilationUnit);
			findAllEnumMetricsFromFile(actualCompilationUnit);
		}
		return extractedMetrics;
	}
	
	private void findAllClassMetricsFromFile(CompilationUnit actualCompilationUnit) throws FileNotFoundException {
		for(ClassOrInterfaceDeclaration classTypeFromParser : actualCompilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {
			className = getClassName(classTypeFromParser);
			WMC_class = getClassComplexity(classTypeFromParser.getMethods(),classTypeFromParser.getConstructors());
			NOM_class = getNOM_class(classTypeFromParser.getMethods(),classTypeFromParser.getConstructors());
			LOC_class = getLOC_classCID(classTypeFromParser);
			addConstructorsToList(classTypeFromParser);
			addMethodsToList(classTypeFromParser);
		}
	}
	
	private void findAllEnumMetricsFromFile(CompilationUnit actualCompilationUnit) {
		for(EnumDeclaration enumTypeFromParser : actualCompilationUnit.findAll(EnumDeclaration.class)) {
			className = getClassNameForEnum(enumTypeFromParser);
			WMC_class = getClassComplexity(enumTypeFromParser.getMethods(),enumTypeFromParser.getConstructors());
			NOM_class = enumTypeFromParser.getMethods().size() + enumTypeFromParser.getConstructors().size();
			LOC_class = getLOC_classENUM(enumTypeFromParser);
			addEnumConstructorsToList(enumTypeFromParser);
			addEnumMethodsToList(enumTypeFromParser);

		}
	}

	private void addEnumConstructorsToList(EnumDeclaration enumTypeFromParser) {
		for(MethodDeclaration md: enumTypeFromParser.getMethods()) {
			LOC_method = getLOC_method_Met(md);
			CYCLO_method = getMethodComplexity(md);
			Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass,method_id, LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
			extractedMetrics.add(metric);
			method_id++;
		}
	}

	private void addEnumMethodsToList(EnumDeclaration enumTypeFromParser) {
		for(ConstructorDeclaration md: enumTypeFromParser.getConstructors()) {
			LOC_method = getLOC_method_Cons(md);
			CYCLO_method = getConstructorComplexity(md);
			Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass, method_id,LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
			extractedMetrics.add(metric);
			method_id++;
		}
	}

	private void addConstructorsToList(ClassOrInterfaceDeclaration classTypeFromParser) {
		for(ConstructorDeclaration md: classTypeFromParser.getConstructors()) {
			LOC_method = getLOC_method_Cons(md);
			CYCLO_method = getConstructorComplexity(md);
			Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass, method_id,LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
			extractedMetrics.add(metric);
			method_id++;
		}
	}
	private void addMethodsToList(ClassOrInterfaceDeclaration classTypeFromParser) {
		for(MethodDeclaration md: classTypeFromParser.getMethods()) {
			LOC_method = getLOC_method_Met(md);
			CYCLO_method = getMethodComplexity(md);
			Metrics metric = new Metrics(getMethodNameWithParameters(md.getNameAsString(),md.getParameters()), className, packageClass, method_id,LOC_method, LOC_class, CYCLO_method, NOM_class,WMC_class);
			extractedMetrics.add(metric);
			method_id++;
		}
	}

	private int getLOC_method_Cons(ConstructorDeclaration constructorFromParsedClass) {
		return (constructorFromParsedClass.getEnd().get().line - constructorFromParsedClass.getBegin().get().line)+1;
	}
	
	private String getClassName(ClassOrInterfaceDeclaration classFromFile) {
		if(classFromFile.getNameAsString().equals(fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING))) return classFromFile.getNameAsString();
		else return fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING)+"."+classFromFile.getNameAsString();
	}
	private String getClassNameForEnum(EnumDeclaration enumFromFile) {
		if(enumFromFile.getNameAsString().equals(fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING))) return enumFromFile.getNameAsString();
		else return fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING)+"."+enumFromFile.getNameAsString();
	}
	
	protected int getLOC_method_Met(MethodDeclaration methodFromParsedClass) {
		return (methodFromParsedClass.getEnd().get().line - methodFromParsedClass.getBegin().get().line)+1;
	}
	
	protected int getNOM_class(List<MethodDeclaration> allMethodsFromParsedClass, List<ConstructorDeclaration> allConstructorsFromParsedClass) {
		return allMethodsFromParsedClass.size()+ allConstructorsFromParsedClass.size();
	}

	protected String getMethodNameWithParameters(String ClassName,NodeList<Parameter> nodeList) {
		if(nodeList.isEmpty()) return ClassName+"()";
		else {
			ClassName = ClassName+"(";
			return addParametersToClassName(ClassName,nodeList);
		}
	}
	
	private String addParametersToClassName(String className,NodeList<Parameter> parametersList) {
		for(Node n: parametersList) {
			String pars[] = n.toString().split(SPACE_STRING);
			String par = pars[0];
			if(parametersList.indexOf(n) == parametersList.size()-1) className = className + par + ")";
			else className = className + par +",";
		}
		return className;
	}

	protected int getLOC_classCID(ClassOrInterfaceDeclaration cid) throws FileNotFoundException {
		return (cid.getEnd().get().line - cid.getBegin().get().line)+1;
	}
	
	protected int getLOC_classENUM(EnumDeclaration ed) {
		return (ed.getEnd().get().line - ed.getBegin().get().line)+1;
	}

	protected int getMethodComplexity(MethodDeclaration md) {
		int complex = COMPLEXITY_INITIAL_VALUE;
		int numbif = getCycloComplex(IF_CYCLE,md.toString());
		int numbwhile = getCycloComplex(WHILE_CYCLE,md.toString());
		int numbfor =getCycloComplex(FOR_CYCLE,md.toString());
		int numbelse = getCycloComplex(ELSE,md.toString());
		int numbcase = getCycloComplex(CASE_CYCLE,md.toString());
		int numbdefault = getCycloComplex(DEFAULT,md.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;

	}

	protected int getConstructorComplexity(ConstructorDeclaration md) {
		int complex = COMPLEXITY_INITIAL_VALUE;
		int numbif = getCycloComplex(IF_CYCLE,md.toString());
		int numbwhile = getCycloComplex(WHILE_CYCLE,md.toString());
		int numbfor =getCycloComplex(FOR_CYCLE,md.toString());
		int numbelse = getCycloComplex(ELSE,md.toString());
		int numbcase = getCycloComplex(CASE_CYCLE,md.toString());
		int numbdefault = getCycloComplex(DEFAULT,md.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	}

	protected int getCycloComplex (String wordToSearch, String data) {
		int count = COUNTER_INITIAL_VALUE;
		for (int index = data.indexOf(wordToSearch); 
				index != -1; 
				index = data.indexOf(wordToSearch, index + 1)) {
			count++;
		}
		return count;
	}

	protected int getClassComplexity(List<MethodDeclaration> md,List<ConstructorDeclaration> cd) {
		int complexity = COUNTER_INITIAL_VALUE;
		for(MethodDeclaration metdec: md) complexity = complexity + getMethodComplexity(metdec);
		for(ConstructorDeclaration consdec: cd) complexity = complexity + getConstructorComplexity(consdec);
		return complexity;
	}

	protected String getPackageName() {
		String packageName = EMPTY_STRING;
		boolean src = false;
		String[] separated = fileToExtract.getAbsolutePath().split(Pattern.quote(File.separator));
		for(int i = 0; i< separated.length-1;i++) {
			if(src && i <= separated.length-2) {
				if(i < separated.length-2) packageName = packageName + separated[i] + ".";
				else packageName = packageName + separated[i];							
			}
			if(separated[i].contains(SRC_FOLDER)) src = true;
		}
		if(isDefaultPackage(packageName,fileToExtract)) packageName = DEFAULT_PACKAGE;
		return packageName;
		
	}
	
	private boolean isDefaultPackage(String packageName,File fileToExtract) {
		if(packageName.equals(EMPTY_STRING) && fileToExtract.getAbsolutePath().contains(SRC_FOLDER)) return true;
		else return false;
	}
	
	public int getCurrentMethodID() {
		return method_id;
	}

}
