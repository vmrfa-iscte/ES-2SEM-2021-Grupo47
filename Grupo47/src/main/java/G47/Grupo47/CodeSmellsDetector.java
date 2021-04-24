package G47.Grupo47;

import java.util.ArrayList;

public class CodeSmellsDetector {

	private int rule1_threshold,rule2_threshold,rule3_threshold;
	private String operator, operator2,lastclassview;
	private ArrayList<MethodMetrics> results;
	private ArrayList<HasCodeSmell> readyToShow = new ArrayList<HasCodeSmell>();
	private ArrayList<HasCodeSmell> notReady = new ArrayList<HasCodeSmell>();
	private ArrayList<String> classWithSmell = new ArrayList<>();
	private boolean hasDetection = false;
	private static final int ARRAY_FIRST_ELEMENT_INDEX = 0;
	private static final String POSITIVE_CLASS = "Classe: Verdadeiro", NEGATIVE_CLASS = "Classe: Negativo", POSITIVE_METHOD="TRUE", NEGATIVE_METHOD = "FALSE";
	private CheckRuleCombinations ruleCombo;

	// Construtor para o caso em que o utilizador decide limitar 2 métricas
	public CodeSmellsDetector (int rule1, int rule2, String operator, ArrayList<MethodMetrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.operator = operator;
		this.results = results;
		this.lastclassview = results.get(ARRAY_FIRST_ELEMENT_INDEX).getClasse();
		this.ruleCombo = new CheckRuleCombinations(rule1,rule2);
	}

	// Construtor para o caso em que o utilizador decide limitar 3 métricas
	public CodeSmellsDetector (int rule1, int rule2, int rule3, String operator, String operator2, ArrayList<MethodMetrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.rule3_threshold = rule3;
		this.operator = operator;
		this.operator2 = operator2;
		this.results = results;
		this.lastclassview = results.get(ARRAY_FIRST_ELEMENT_INDEX).getClasse();
		this.ruleCombo = new CheckRuleCombinations(rule1,rule2,rule3);
	}
	
	private void checkDetection(boolean hasDetection,MethodMetrics methodWithMetrics) {
		if (hasDetection) {
//			createAndAddNotReady(methodWithMetrics.getNome_metodo(),POSITIVE_METHOD,String.valueOf(methodWithMetrics.getMethod_ID()),methodWithMetrics.getPacote(),methodWithMetrics.getClasse());		
			HasCodeSmell codesmell = new HasCodeSmell(methodWithMetrics.getNome_metodo(),POSITIVE_METHOD,String.valueOf(methodWithMetrics.getMethod_ID()),methodWithMetrics.getPacote(),methodWithMetrics.getClasse(),null);
			notReady.add(codesmell);
			addToCodeSmellsList(methodWithMetrics);
		}else {
//			createAndAddNotReady(methodWithMetrics.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(methodWithMetrics.getMethod_ID()),methodWithMetrics.getPacote(),methodWithMetrics.getClasse());
			HasCodeSmell codesmell = new HasCodeSmell(methodWithMetrics.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(methodWithMetrics.getMethod_ID()),methodWithMetrics.getPacote(),methodWithMetrics.getClasse(),null);
			notReady.add(codesmell);
		}
		lastclassview = lastVerification(methodWithMetrics);
		hasDetection = false;
	}


	private void createAndAddReady(String name,String detection,String methodId,String classpackage,String className) {
		HasCodeSmell codesmell = new HasCodeSmell(name,detection,methodId,classpackage,className,null);
		readyToShow.add(codesmell);
		// Adicionar objeto HasCodeSmell com a qualidade de deteção já determinada a um array que compõe todos os resultados
	}
	
	private void createAndAddNotReady(String name,String detection,String methodId,String classpackage,String className) {
		HasCodeSmell codesmell = new HasCodeSmell(name,detection,methodId,classpackage,className,null);
		notReady.add(codesmell);
		// Adicionar objeto HasCodeSmell com a qualidade de deteção já determinada a um array que compõe todos os resultados
	}


	private String verifyLastClass(MethodMetrics methodWithMetrics) {
		if(!lastclassview.equals(methodWithMetrics.getClasse())) {
			if(classWithSmell.indexOf(lastclassview) != -1 ) {
//				createAndAddNotReady(lastclassview,POSITIVE_CLASS,null,null,null);
				HasCodeSmell codesmell = new HasCodeSmell(lastclassview,POSITIVE_CLASS,null,null,null,null);
				readyToShow.add(codesmell);
			}
			else {
//				createAndAddReady(lastclassview,NEGATIVE_CLASS,null,null,null);
				HasCodeSmell codesmell = new HasCodeSmell(lastclassview,NEGATIVE_CLASS,null,null,null,null);
				readyToShow.add(codesmell);
			}
			lastclassview = methodWithMetrics.getClasse();
			readyToShow.addAll(notReady);
			notReady.removeAll(notReady);
		}
		return lastclassview;
	}

	private String lastVerification(MethodMetrics methodWithMetrics) {
		if(results.indexOf(methodWithMetrics) == results.size()-1) {
			checkClassSmell(methodWithMetrics);
			lastclassview = methodWithMetrics.getClasse();
			notReady.removeAll(notReady);
		}
		return lastclassview;
	}

	private void checkClassSmell(MethodMetrics methodWithMetrics) {
		if (classWithSmell.indexOf(methodWithMetrics.getClasse()) != -1) {
			createAndAddReady(lastclassview,POSITIVE_CLASS,null,null,null);
		}
		else {
			createAndAddReady(lastclassview,NEGATIVE_CLASS,null,null,null);
		}
		readyToShow.addAll(notReady);
	}

	private void addToCodeSmellsList(MethodMetrics methodWithMetrics) {
		if(classWithSmell.indexOf(methodWithMetrics.getClasse()) == -1) classWithSmell.add(methodWithMetrics.getClasse());
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de maior.
	public ArrayList<HasCodeSmell> detectLongMethodGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				// Caso em que o perador é AND
				if (ruleCombo.isLongGGAnd(methodWithMetrics)) hasDetection = true;
			}else{
				// Caso em que o perador é OR
				if (ruleCombo.isLongGGOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Bigger para o LONG_METHOD");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectLongMethodGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				// Caso em que o perador é AND
				if (ruleCombo.isLongGLAnd(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o perador é OR
				if (ruleCombo.isLongGLOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Smaller para LongMethod");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	public ArrayList<HasCodeSmell> detectLongMethodSmallerBigger() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isLongLesserGreaterAnd(methodWithMetrics)) hasDetection = true;
			}else  {
				// Caso em que o operador é OR
				if (ruleCombo.isLongLesserGreaterOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri SmallerBigger para Long Method");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de menor para a primeira métrica e um de maior para a segunda. 
	public ArrayList<HasCodeSmell> detectLongMethodLesserLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isLongLesserLesserAnd(methodWithMetrics)) hasDetection = true;
			}else  {
				// Caso em que o operador é OR
				if (ruleCombo.isLongLesserLesserOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Smaller para Long Method");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites com o sinal de maior para ambas.
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterWMC_NOM() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isGodGreaterGreaterNOM_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodGreaterGreaterNOM_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Bigger para WMC_NOM");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserWMC_NOM() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				if (ruleCombo.isGodGreaterLesserWMC_And(methodWithMetrics)) hasDetection = true;
			}else  {
				if (ruleCombo.isGodGreaterLesserNOM_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Smaller para WMC_NOM");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserWMC_NOM() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				if (ruleCombo.isGodLesserLesserNOM_And(methodWithMetrics)) hasDetection = true;
			}else {
				if (ruleCombo.isGodLesserLesserNOM_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Smaller para WMC_NOM");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
		
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterWMC_NOM() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// 	Caso em que o operador é AND
				if (ruleCombo.isGodLesserGreaterNOM_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodLesserGreaterNOM_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Bigger para WMC_NOM");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de maior para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é ANDA
				if (ruleCombo.isGodGreaterGreaterLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodGreaterGreaterLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corru Bigger Bigger para WMC_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isGodGreaterLesserLOC_And(methodWithMetrics)) hasDetection = true;
			}else  {
				// Caso em que o operador é OR
				if (ruleCombo.isGodGreaterLesserLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Smaller para WMC_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell ruleCombo.isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isGodLesserLesserLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodLesserLesserLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Smaller para WMC_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isGodLesserGreaterLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				if (ruleCombo.isGodLesserGreaterLOC_Or(methodWithMetrics)) hasDetection = true; // Caso em que o operador é OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Bigger para WMC_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de maior para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (ruleCombo.isGodGreaterGreaterNOMLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				if (ruleCombo.isGodGreaterGreaterNOMLOC_Or(methodWithMetrics)) hasDetection = true; // Caso em que o operador é OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Bigger para NOM_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND"))
				// Caso em que o operador é AND
				if (ruleCombo.isGodGreaterLesserNOMLOC_And(methodWithMetrics)) hasDetection = true;
			else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodGreaterLesserNOMLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Bigger Smaller para NOM_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND"))
				// Caso em que o operador é AND
				if (ruleCombo.isGodLesserGreaterNOMLOC_And(methodWithMetrics)) hasDetection = true;
			else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodLesserGreaterNOMLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Bigger para NOM_LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	

	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if (operator.equals("AND"))
				// Caso em que o operador é AND
				if (ruleCombo.isGodLesserLesserNOMLOC_And(methodWithMetrics)) hasDetection = true;
			else {
				// Caso em que o operador é OR
				if (ruleCombo.isGodLesserLesserNOMLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		System.out.println("Corri Smaller Smaller para NOM LOC");
		return readyToShow;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodGreaterGreaterGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterGreaterGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else 
				if(operator2.equals("AND")) {
					if (ruleCombo.isGodGreaterGreaterGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterGreaterGreater_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de menor para a segunda e um de menor para a teceira.
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLesserLesserLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserLesserLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLesserLesserLesser_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserLesserLesser_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de menor para a segunda e um de menor para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodGreaterLesserLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodGreaterLesserLesser_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserLesser_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;

	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de menor para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND"))
					if (ruleCombo.isGodGreaterLesserGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodGreaterLesserGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserGreater_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de maior para a segunda e um de menor para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassGreateGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodGreaterGreaterLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterGreaterLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodGreaterGreaterLesser_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterGreaterLesser_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND"))
					if (ruleCombo.isGodLesserLesserGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserLesserGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLesserLesserGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserLesserGreater_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLesserGreaterGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserGreaterGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLesserGreaterGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserGreaterGreater_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerSmaller() {
		for (MethodMetrics methodWithMetrics : results) {
			lastclassview = verifyLastClass(methodWithMetrics);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLGLAndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLGLAndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals("AND")) 
					if (ruleCombo.isGodLGLOrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLGLOrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	
	
	
}
