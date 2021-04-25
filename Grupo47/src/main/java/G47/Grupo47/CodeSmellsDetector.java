package G47.Grupo47;

import java.util.ArrayList;

public class CodeSmellsDetector {

	private String operator, operator2;
	private MethodMetrics lastMethod;
	private ArrayList<MethodMetrics> results;
	private ArrayList<HasCodeSmell> readyToShow = new ArrayList<HasCodeSmell>();
	private ArrayList<HasCodeSmell> notReady = new ArrayList<HasCodeSmell>();
	private ArrayList<String> classWithSmell = new ArrayList<>();
	private boolean hasDetection = false;
	private static final int ARRAY_FIRST_ELEMENT_INDEX = 0;
	private static final String POSITIVE_CLASS = "Classe: Verdadeiro", NEGATIVE_CLASS = "Classe: Negativo", POSITIVE_METHOD="TRUE"
			, NEGATIVE_METHOD = "FALSE", AND_LOGIC_OPERATOR = "AND";
	private CheckRuleCombinations ruleCombo;

	// Construtor para o caso em que o utilizador decide limitar 2 métricas
	public CodeSmellsDetector (ArrayList<MethodMetrics> results, Rule ruleReceived) {
		int rule1_threshold = Integer.parseInt(ruleReceived.getLimit1());
		int rule2_threshold = Integer.parseInt(ruleReceived.getLimit2());
		this.operator = ruleReceived.getOperator();
		this.results = results;
		this.lastMethod = results.get(ARRAY_FIRST_ELEMENT_INDEX);
		if(ruleReceived.getOperator2() == "") 
			this.ruleCombo = new CheckRuleCombinations(rule1_threshold,rule2_threshold);
		else {
			int rule3_threshold = Integer.parseInt(ruleReceived.getLimit3());
			this.operator2 = ruleReceived.getOperator2();
			this.ruleCombo = new CheckRuleCombinations(rule1_threshold,rule2_threshold,rule3_threshold);
		}
	}
	
	// Cria e adiciona um objeto HasCodeSmell à lista de métodos ainda não prontos para serem mostrados
	private void createAndAddNotReady(String detection,MethodMetrics methodWithMetrics) {
		HasCodeSmell codesmell = new HasCodeSmell(detection,null,methodWithMetrics,true);
		notReady.add(codesmell);
		
	}
	
	// Verifica se foi detetado um code smell
	private void checkDetection(boolean hasDetection,MethodMetrics methodWithMetrics) {
		if (hasDetection) {
			// Se foi detetado então é criado e adicionado um objeto HasCodeSmell e é adicionado o nome da classe à lista de classes com code smells
			createAndAddNotReady(POSITIVE_METHOD,methodWithMetrics);
			addToCodeSmellsList(methodWithMetrics);
		}else {
			// Se não foi detatado code smell cria-se um objeto HasCodeSmell mas com deteção NEGATIVE_METHOD
			createAndAddNotReady(NEGATIVE_METHOD,methodWithMetrics);
		}
		lastMethod = lastVerification(methodWithMetrics);
		this.hasDetection = false;
	}

	// Adiciona o nome da classe de um método à lista de classes com code smells
	private void addToCodeSmellsList(MethodMetrics methodWithMetrics) {
		if(classWithSmell.indexOf(methodWithMetrics.getClasse()) == -1) classWithSmell.add(methodWithMetrics.getClasse());
	}
	
	private void createAndAdd(String detection,MethodMetrics methodWithMetrics) {
		HasCodeSmell codesmell = new HasCodeSmell(detection,null,methodWithMetrics,false);
		readyToShow.add(codesmell);
		// Adicionar objeto HasCodeSmell com a qualidade de deteção já determinada a um array que compõe todos os resultados
	}

	// Caso a classe tenha se alterado vai se verificar se a classe anterior tinha algum método com code smells
	private MethodMetrics verifyLastClass(MethodMetrics metric) {
		if(!lastMethod.getClasse().equals(metric.getClasse())) {
			// se o nome da classe estiver presente na lista classWithSmell então a classe tem um método com code smells
			if(classWithSmell.indexOf(lastMethod.getClasse()) != -1 ) {
				createAndAdd(POSITIVE_CLASS,lastMethod);
			}else {
				createAndAdd(NEGATIVE_CLASS,lastMethod);
			}
			//muda o nome da classe atual
			lastMethod = metric;
			// Adiciona os métodos que não estavam prontos à lista de métodos prontos
			readyToShow.addAll(notReady);
			// Remove todos os métodos que foram adicionados à lista de métodos prontos
			notReady.removeAll(notReady);
		}
		return lastMethod;
	}

	// Para o caso da última classe da lista faz-se uma verificação parecida para ver se a classe tem code smells
	private MethodMetrics lastVerification(MethodMetrics metric) {
		if(results.indexOf(metric) == results.size()-1) {
			checkClassSmell(metric);
			lastMethod = metric;
			notReady.removeAll(notReady);
		}
		return lastMethod;
	}

	private void checkClassSmell(MethodMetrics metric) {
		if (classWithSmell.indexOf(metric.getClasse()) != -1) {
			createAndAdd(POSITIVE_CLASS,metric);
		} else {
			createAndAdd(NEGATIVE_CLASS,metric);
		}
		readyToShow.addAll(notReady);
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de maior.
	public ArrayList<HasCodeSmell> detectLongMethodGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR))
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR))
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR))
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodGreaterGreaterGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterGreaterGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else 
				if(operator2.equals(AND_LOGIC_OPERATOR)) {
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserLesserLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserLesserLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodGreaterLesserLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR))
					if (ruleCombo.isGodGreaterLesserGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
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
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodGreaterGreaterLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterGreaterLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR))
					if (ruleCombo.isGodLesserLesserGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserLesserGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
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
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserGreaterGreater_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
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
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserGreaterLesser_AndOr(methodWithMetrics)) hasDetection = true;
			}else {
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterLesser_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodLesserGreaterLesser_OrOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	
	
	
}
