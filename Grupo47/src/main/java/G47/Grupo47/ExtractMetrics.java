package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

	private MetricParser metricParser = new MetricParser();
	private File fileToExtract;
	private int method_id;
	/* Todas as métricas são inicializadas a 0 com exceção da complexidade ciclomática que é inicializada a 1
	  	Os contadores utilizados são inicializados também a 0
	 */
	public static final int METRICS_INITIAL_VALUE = 0, COUNTER_INITIAL_VALUE = 0, COMPLEXITY_INITIAL_VALUE = 1; 
	public static final String SRC_DIR = "src", DEFAULT_PACKAGE = "Default Package", EMPTY_STRING = "", SPACE_STRING = " ", IF_CYCLE = "if",
			FOR_CYCLE = "for", WHILE_CYCLE = "while", CASE_CYCLE = "case", ELSE = "else", DEFAULT = "default", JAVA_FILE = ".java", DOT = ".",
			LEFT_PARANTESES = "(", RIGHT_PARENTESES = ")", COMMA = ",", CLOSED_PARENTESES = "()";
	private String className = EMPTY_STRING;
	private int LOC_method,CYCLO_method,LOC_class,NOM_class,WMC_class = METRICS_INITIAL_VALUE;
	private String packageClass;
	private ArrayList<MethodMetrics> extractedMetrics;
	

	public ExtractMetrics(File file) {
		this.fileToExtract = file;
		this.packageClass = getPackageName();
	}

	public ArrayList<MethodMetrics> doExtractMetrics(ArrayList<MethodMetrics> extractedMetrics,int method_id) throws FileNotFoundException {
		this.method_id = method_id;
		this.extractedMetrics = extractedMetrics;
		// Obter o código do ficheiro 'FileToExtract' através do objeto JavaParser
		JavaParser parseCodeFromFile  = new JavaParser();
		ParseResult<CompilationUnit> compilationUnitFromParser = parseCodeFromFile.parse(fileToExtract); 
		if(compilationUnitFromParser.isSuccessful()) {
			extract(compilationUnitFromParser);
		}
		return extractedMetrics;
	}

	private void extract(ParseResult<CompilationUnit> compilationUnitFromParser) throws FileNotFoundException {
		// Cria objeto CompilatioUnit para obter a tradução do código
		CompilationUnit actualCompilationUnit = compilationUnitFromParser.getResult().get();
		//Obtém todas as métricas de todos os métodos de todas as classes (classes, interfaces ou enumerados)
		findAllClassMetricsFromFile(actualCompilationUnit);
		findAllEnumMetricsFromFile(actualCompilationUnit);
	}
	
	private void findAllClassMetricsFromFile(CompilationUnit actualCompilationUnit) throws FileNotFoundException {
		// Percorrer todas as classes e interfaces dentro do ficheiro dado
		for(ClassOrInterfaceDeclaration classTypeFromParser : actualCompilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {
			// Obter nome, complexidade ciclomática, número de métodos e número de linhas da classe
			className = getClassName(classTypeFromParser);
			WMC_class = metricParser.getClassComplexity(classTypeFromParser.getMethods(),classTypeFromParser.getConstructors());
			NOM_class = metricParser.getNOM_class(classTypeFromParser.getMethods(),classTypeFromParser.getConstructors());
			LOC_class = metricParser.getLOC_classFromClass(classTypeFromParser);
			// Adicionar construtores e métodos juntamente com as métricas associadas, à lista de métricas extraídas
			addConstructorsToList(classTypeFromParser);
			addMethodsToList(classTypeFromParser);
		}
	}
	
	private void findAllEnumMetricsFromFile(CompilationUnit actualCompilationUnit) {
		// Percorrer todos os enumerdos dentro do ficheiro
		for(EnumDeclaration enumTypeFromParser : actualCompilationUnit.findAll(EnumDeclaration.class)) {
			// Obter nome, complexidade ciclomática, número de métodos e número de linhas do enumerado
			className = getClassNameForEnum(enumTypeFromParser);
			WMC_class = metricParser.getClassComplexity(enumTypeFromParser.getMethods(),enumTypeFromParser.getConstructors());
			NOM_class = enumTypeFromParser.getMethods().size() + enumTypeFromParser.getConstructors().size();
			// Adicionar construtores e métodos juntamente com as métricas associadas à lista de métricas extraídas
			LOC_class = metricParser.getLOC_classFromEnum(enumTypeFromParser);
			addEnumConstructorsToList(enumTypeFromParser);
			addEnumMethodsToList(enumTypeFromParser);
		}
	}

	private void addEnumMethodsToList(EnumDeclaration enumTypeFromParser) {
		// Percorrer todos os métodos dentro do enumerado
		for(MethodDeclaration methodFromEnum: enumTypeFromParser.getMethods()) {
			extractMethodMetricsForEnum(methodFromEnum);
			// Incrementar method_id
			method_id++;
		}
	}

	private void extractMethodMetricsForEnum(MethodDeclaration methodFromEnum) {
		// Extrair linhas de código e complexidade ciclomática através do objeto metricParser
		LOC_method = metricParser.getLOC_methodMethod(methodFromEnum);
		CYCLO_method = metricParser.getMethodComplexity(methodFromEnum);
		// Criar objeto Metrics e adicioná-lo à lista
		MethodIdentity currentMethod = new MethodIdentity(getMethodNameWithParameters(methodFromEnum.getNameAsString(), methodFromEnum.getParameters()), className, packageClass, method_id);
		Metrics metricsForMethod = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodMetrics metricToAdd = new MethodMetrics(currentMethod,metricsForMethod);
		extractedMetrics.add(metricToAdd);
	}

	private void addEnumConstructorsToList(EnumDeclaration enumTypeFromParser) {
		// Percorrer todos os construtores dentro do enumerado
		for(ConstructorDeclaration constructorFromEnum: enumTypeFromParser.getConstructors()) {
			extractConstructorsMetricsForEnum(constructorFromEnum);
			//Incrementar method_id
			method_id++;
		}
	}

	private void extractConstructorsMetricsForEnum(ConstructorDeclaration constructorFromEnum) {
		// Extrair linhas de código e complexidade ciclomática através do objeto metricParser
		LOC_method = metricParser.getLOC_methodConstructor(constructorFromEnum);
		CYCLO_method = metricParser.getConstructorComplexity(constructorFromEnum);
		// Criar objeto Metrics e adicioná-lo à lista
		MethodIdentity currentMethod = new MethodIdentity(getMethodNameWithParameters(constructorFromEnum.getNameAsString(), constructorFromEnum.getParameters()), className, packageClass, method_id);
		Metrics metricsForMethod = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodMetrics metricToAdd = new MethodMetrics(currentMethod,metricsForMethod);
		extractedMetrics.add(metricToAdd);
	}

	private void addConstructorsToList(ClassOrInterfaceDeclaration classTypeFromParser) {
		// Percorrer todos os construtores dentro da classe
		for(ConstructorDeclaration constructorFromClass: classTypeFromParser.getConstructors()) {
			extractConstructorMetrics(constructorFromClass);
			//Incrementar method_id
			method_id++;
		}
	}

	private void extractConstructorMetrics(ConstructorDeclaration constructorFromClass) {
		// Extrair linhas de código e complexidade ciclomática do construtor
		LOC_method = metricParser.getLOC_methodConstructor(constructorFromClass);
		CYCLO_method = metricParser.getConstructorComplexity(constructorFromClass);
		// Criar objeto Metrics com todas as métricas obtidas da classe e do método
		Metrics metricsForMethod = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodIdentity currentMethod = new MethodIdentity(getMethodNameWithParameters(constructorFromClass.getNameAsString(),
				constructorFromClass.getParameters()), className, packageClass, method_id);
		MethodMetrics metric = new MethodMetrics(currentMethod,metricsForMethod);
		// Adicionar objeto Metrics à lista
		extractedMetrics.add(metric);
	}
	
	private void addMethodsToList(ClassOrInterfaceDeclaration classTypeFromParser) {
		// Percorrer todos os métodos dentro da classe
		for(MethodDeclaration methodFromClass: classTypeFromParser.getMethods()) {
			extractMethodMetrics(methodFromClass);
			//Incrementar method_id
			method_id++;
		}
	}

	private void extractMethodMetrics(MethodDeclaration methodFromClass) {
		// Extrair linhas de código e complexidade ciclomática através do objeto metricParser
		LOC_method = metricParser.getLOC_methodMethod(methodFromClass);
		CYCLO_method = metricParser.getMethodComplexity(methodFromClass);
		// Criar objeto Metrics e adicioná-lo à lista
		MethodIdentity currentMethod = new MethodIdentity(getMethodNameWithParameters(methodFromClass.getNameAsString(), methodFromClass.getParameters()), className, packageClass, method_id);
		Metrics metricsForMethod = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodMetrics metric = new MethodMetrics(currentMethod,metricsForMethod);
		extractedMetrics.add(metric);
	}
	
	private String getClassName(ClassOrInterfaceDeclaration classFromFile) {
		/** Obter nome da classe ou interface, caso o nome da classe seja igual ao nome do ficheiro a que pertence sem ".java", 
		 * então o nome da classe será esse mesmo.
		 * Por outro lado, se o nome da classe não corresponder ao nome do ficheiro estamos perante uma Inner Classe e portanto o nome da classe terá 
		 * a formatação NomeDoFicheiro.NomeDaClasse
		 */
		if(classFromFile.getNameAsString().equals(fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING))) return classFromFile.getNameAsString();
		else return fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING)+ DOT +classFromFile.getNameAsString();
	}
	
	private String getClassNameForEnum(EnumDeclaration enumFromFile) {
		// A obtenção do nome da classe para os enumerados segue exatamente a mesma lógica que para as classes e interfaces
		if(enumFromFile.getNameAsString().equals(fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING))) return enumFromFile.getNameAsString();
		else return fileToExtract.getName().replace(JAVA_FILE, EMPTY_STRING)+ DOT +enumFromFile.getNameAsString();
	}

	private String getMethodNameWithParameters(String methodName,NodeList<Parameter> nodeList) {
		// Adicionar os parâmetros ao nome do método para evitar confusões com outros métodos com o mesmo nome e parâmetros diferentes
		// Se não tiver parâmetros então o nome do método será nomeMetodo()
		if(nodeList.isEmpty()) return methodName+ CLOSED_PARENTESES;
		// Se tiver parâmetros é necessário abrir parênteses e adicionar o tipo dos parâmetros
		else {
			methodName = methodName+ LEFT_PARANTESES;
			return addParametersToClassName(methodName,nodeList);
		}
	}
	
	private String addParametersToClassName(String methodName,NodeList<Parameter> parametersList) {
		// Percorrer lista de parâmetros, para obter apenas o tipo dos parâmetros é necessário separar os Nodes e ir buscar apenas a primeira posição
		for(Node parameter: parametersList) {
			String separatedParameter[] = parameter.toString().split(SPACE_STRING);
			String parameterType = separatedParameter[0];
			// Caso a posição atual da lista seja a última então será adicionado o tipo do parâmetros juntamente com o parênteses que faltava
			if(parametersList.indexOf(parameter) == parametersList.size()-1) methodName = methodName + parameterType + RIGHT_PARENTESES;
			// Se ainda não for a última posição da lista adiciona-se o tipo do parâmetro e uma vírgula para posteriormente adicionar mais um tipo de parâmetro
			else methodName = methodName + parameterType + COMMA;
		}
		return methodName;
	}

	private String getPackageName() {
		// Método para obter o nome do pacote a que a classe pertence através do caminho do ficheiro
		String packageName = EMPTY_STRING;
		boolean src = false;
		// Separar o caminho do ficheiro para obter as diretorias a que o ficheiro pertence e depois percorrê-las
		String[] separated = fileToExtract.getAbsolutePath().split(Pattern.quote(File.separator));
		/** O ciclo for seguinte vai procurar a primeira ocorrência da diretoria "src" pois é dentro desta diretoria que se encontram os pacotes 
		 * se já tiver passado a ocrrência da diretoria "src" vai verificar se está na penúltima posição do array, caso esteja, vai apenas adicionar
		 * o nome da diretoria à variável "packageName", caso não seja a penúltima posição do array então vai adicionar a diretoria à variável "packageName"
		 * mais um ponto final para separar
		 */
		for(int i = 0; i< separated.length-1;i++) {
			if(src && i <= separated.length-2) {
				if(i < separated.length-2) packageName += separated[i] + DOT;
				else packageName += separated[i];							
			}
			if(separated[i].contains(SRC_DIR)) src = true;
		}
		if(isDefaultPackage(packageName,fileToExtract)) packageName = DEFAULT_PACKAGE;
		return packageName;
		
	}
	
	private boolean isDefaultPackage(String packageName,File fileToExtract) {
		/** Verifica se a classe está no "Default Package", vai verificar se a variável packageName se encontra vazia, se estiver vazia 
		 * e o caminho do ficheiro contiver a pasta "src" então o ficheiro encontra-se diretamente dentro dessa diretoria
		 */
		if(packageName.equals(EMPTY_STRING) && fileToExtract.getAbsolutePath().contains(SRC_DIR)) return true;
		else return false;
	}
	
	public int getCurrentMethodID() {
		return method_id;
	}
}
