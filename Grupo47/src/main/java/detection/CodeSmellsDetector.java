package detection;

import java.util.ArrayList;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Rule;

/**
 * Classe utilizada para detetar a existencia de Code smells num determinado metodo e numa determinada classe, consoante a regra
 * definida pelo utilizadar para esta detecao
 * @author Tomas Mendes
 * @author Vasco Fontoura
 * @version 3.0
 * 

 *
 */
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
	
	

	
	/**
	 * Construtor, lida com processamento de regras com limitacao de duas metricas e com limitacao de tres metricas
	 * @param results ArrayList com as metricas extraidas de um determinado projeto
	 * @param ruleReceived Regra que ira ser aplicada para detetar codeSmells
	 */
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
	
	
	/**
	 * Cria e adiciona um objeto HasCodeSmell a lista de metodos ainda nao prontos para serem mostrados
	 * @param detection resultado da detecao de codeSmells
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 */
	private void createAndAddNotReady(String detection,MethodMetrics methodWithMetrics) {
		HasCodeSmell codesmell = new HasCodeSmell(detection,null,methodWithMetrics,true);
		notReady.add(codesmell);
		
	}
	
	
	/**
	 *  Verifica se foi detetado um code smell
	 * @param hasDetection booleano que indica se foi detetado um codeSmell
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 */
	private void checkDetection(boolean hasDetection,MethodMetrics methodWithMetrics) {
		if (hasDetection) {
			// Se foi detetado entao e criado e adicionado um objeto HasCodeSmell e e adicionado o nome da classe a lista de classes com code smells
			createAndAddNotReady(POSITIVE_METHOD,methodWithMetrics);
			addToCodeSmellsList(methodWithMetrics);
		}else {
			// Se nao foi detatado code smell cria-se um objeto HasCodeSmell mas com detecao NEGATIVE_METHOD
			createAndAddNotReady(NEGATIVE_METHOD,methodWithMetrics);
		}
		lastMethod = lastVerification(methodWithMetrics);
		this.hasDetection = false;
	}

	
	/**
	 * Adiciona o nome da classe de um metodo a lista de classes com code smells
	 * @param methodWithMetrics objeto que permite identificar um metodo e as suas metricas
	 */
	private void addToCodeSmellsList(MethodMetrics methodWithMetrics) {
		if(classWithSmell.indexOf(methodWithMetrics.getClasse()) == -1) classWithSmell.add(methodWithMetrics.getClasse());
	}
	
	/**
	 * Adicionar objeto HasCodeSmell com a qualidade de detecao ja determinada a um array que compoe todos os resultados
	 * @param detection indica a detecao ou nao de codesmell
	 * @param methodWithMetrics objeto que permite identificar um metodo e as suas metricas
	 */
	private void createAndAdd(String detection,MethodMetrics methodWithMetrics) {
		HasCodeSmell codesmell = new HasCodeSmell(detection,null,methodWithMetrics,false);
		readyToShow.add(codesmell);
		
	}

	
	/**Caso a classe tenha se alterado vai se verificar se a classe anterior tinha algum metodo com code smells
	 * @param metric objeto que permite identificar um metodo e as suas metricas
	 * @return ultimo metodo
	 */
	private MethodMetrics verifyLastClass(MethodMetrics metric) {
		if(!lastMethod.getClasse().equals(metric.getClasse())) {
			// se o nome da classe estiver presente na lista classWithSmell entao a classe tem um metodo com code smells
			if(classWithSmell.indexOf(lastMethod.getClasse()) != -1 )
				createAndAdd(POSITIVE_CLASS,lastMethod);
			else
				createAndAdd(NEGATIVE_CLASS,lastMethod);

			//muda o nome da classe atual
			lastMethod = metric;
			// Adiciona os metodos que nao estavam prontos a lista de metodos prontos
			readyToShow.addAll(notReady);
			// Remove todos os metodos que foram adicionados a lista de metodos prontos
			notReady.removeAll(notReady);
		}
		return lastMethod;
	}

	
	/**
	 *  Para o caso da última classe da lista faz-se uma verificacao parecida para ver se a classe tem code smells
	 * @param metric objeto que permite identificar um objeto e as suas metricas
	 * @return ultimo metodo
	 */
	private MethodMetrics lastVerification(MethodMetrics metric) {
		if(results.indexOf(metric) == results.size()-1) {
			checkClassSmell(metric);
			lastMethod = metric;
			notReady.removeAll(notReady);
		}
		return lastMethod;
	}

	/**
	 * metodo utilizado para verificar se uma respetiva classe tem algum metodo onde tenha sido detetado um codeSmell
	 * @param metric objeto que identifica um metodo e as suas metricas associadas
	 */
	private void checkClassSmell(MethodMetrics metric) {
		if (classWithSmell.indexOf(metric.getClasse()) != -1) {
			createAndAdd(POSITIVE_CLASS,metric);
			// Caso em que existe um metodo com codeSmell na respetiva classe
		} else {
			createAndAdd(NEGATIVE_CLASS,metric);
			// Caso em que nao existe
		}
		readyToShow.addAll(notReady);
	}


	/**
	 * 	 Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta detecao define os limites
		das metricas com um sinal de maior.
	 * @return Array List com resultados da aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectLongMethodGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o perador e AND
				if (ruleCombo.isLongGGAnd(methodWithMetrics)) hasDetection = true;
			}else{
				// Caso em que o perador e OR
				if (ruleCombo.isLongGGOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	
	
	/**
	 *  Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta detecao define os limites
	 das metricas com um sinal de maior para a primeira metrica e um de menor para a segunda. 
	 * @return ArrayList com resultado da aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectLongMethodGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o perador e AND
				if (ruleCombo.isLongGLAnd(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o perador e OR
				if (ruleCombo.isLongGLOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	/**
	 *   Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta detecao define os limites
	 das metricas com um sinal de menor para a primeira metrica e um de maior para a segunda.
	 * @return ArrayList com resultado de aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectLongMethodSmallerBigger() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isLongLesserGreaterAnd(methodWithMetrics)) hasDetection = true;
			}else  {
				// Caso em que o operador e OR
				if (ruleCombo.isLongLesserGreaterOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	


	/**
	 * 	 Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta detecao define os limites
	 das metricas com um sinal de menor para a primeira metrica e um de maior para a segunda. 
	 * @return ArrayList com resultado da aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectLongMethodLesserLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isLongLesserLesserAnd(methodWithMetrics)) hasDetection = true;
			}else  {
				// Caso em que o operador e OR
				if (ruleCombo.isLongLesserLesserOr(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	


	/**
	 * 	 Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class e NOM_Class, e para esta detecao define os limites com o sinal de maior para ambas.
	 * @return ArrayList com resultado de aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterWMC_NOM() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isGodGreaterGreaterNOM_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodGreaterGreaterNOM_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	


	/**
	 * 	 Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	 as metricas WMC_Class e NOM_Class, e para esta detecao define os limites 
	 com um sinal de maior para a primeira metrica e um de menor para a segunda. 
	 * @return ArrayList com resultado de aplicacao da regra
	 */
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
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class e NOM_Class, e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica e um de menor para a segunda.
	 * @return ArrayList com resultado de aplicacao das regras
	 */
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
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
		
 
	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class e NOM_Class, e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica e um de maior para a segunda.
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterWMC_NOM() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// 	Caso em que o operador e AND
				if (ruleCombo.isGodLesserGreaterNOM_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodLesserGreaterNOM_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica e um de maior para a segunda.
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e ANDA
				if (ruleCombo.isGodGreaterGreaterLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodGreaterGreaterLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	


	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica e um de menor para a segunda. 
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isGodGreaterLesserLOC_And(methodWithMetrics)) hasDetection = true;
			}else  {
				// Caso em que o operador e OR
				if (ruleCombo.isGodGreaterLesserLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell ruleCombo.isGodClass,conjugando as
	as metricas WMC_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica e um de menor para a segunda. 
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isGodLesserLesserLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodLesserLesserLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica e um de maior para a segunda. 
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterWMC_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isGodLesserGreaterLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				if (ruleCombo.isGodLesserGreaterLOC_Or(methodWithMetrics)) hasDetection = true; // Caso em que o operador e OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas NOM_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica e um de maior para a segunda.
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR)) {
				// Caso em que o operador e AND
				if (ruleCombo.isGodGreaterGreaterNOMLOC_And(methodWithMetrics)) hasDetection = true;
			}else {
				if (ruleCombo.isGodGreaterGreaterNOMLOC_Or(methodWithMetrics)) hasDetection = true; // Caso em que o operador e OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas NOM_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica e um de menor para a segunda. 
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR))
				// Caso em que o operador e AND
				if (ruleCombo.isGodGreaterLesserNOMLOC_And(methodWithMetrics)) hasDetection = true;
			else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodGreaterLesserNOMLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}

	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas NOM_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica e um de maior para a segunda.
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR))
				// Caso em que o operador e AND
				if (ruleCombo.isGodLesserGreaterNOMLOC_And(methodWithMetrics)) hasDetection = true;
			else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodLesserGreaterNOMLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}
	

	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas NOM_Class e LOC_Class, e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica e um de menor para a segunda. 
	 * @return ArrayList com resultado de aplicacao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserNOM_LOC() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if (operator.equals(AND_LOGIC_OPERATOR))
				// Caso em que o operador e AND
				if (ruleCombo.isGodLesserLesserNOMLOC_And(methodWithMetrics)) hasDetection = true;
			else {
				// Caso em que o operador e OR
				if (ruleCombo.isGodLesserLesserNOMLOC_Or(methodWithMetrics)) hasDetection = true;
			}
			checkDetection(hasDetection,methodWithMetrics);
		}

		return readyToShow;
		// No fim e retornado um ArrayList com os resultados da aplicacao da regra
	}


	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica,um de maior para a segunda e um de maior para a teceira. 
	 * @return ArrayList com resultado de geracao de regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro operador e AND
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					// segunfo operador e AND
					if (ruleCombo.isGodGreaterGreaterGreater_AndAnd(methodWithMetrics)) hasDetection = true;
					
				else 
					if (ruleCombo.isGodGreaterGreaterGreater_AndOr(methodWithMetrics)) hasDetection = true;
						
			}else 
				// primeiro operador e OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) {
					// segundo operador e AND
					if (ruleCombo.isGodGreaterGreaterGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				   
				else 
					if (ruleCombo.isGodGreaterGreaterGreater_OrOr(methodWithMetrics)) hasDetection = true;
				
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	
	
	/**
	 * Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica,um de menor para a segunda e um de menor para a teceira.
	 * @return ArrayList com o resultado de aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro operador e AND
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					// segundo operador e AND
					if (ruleCombo.isGodLesserLesserLesser_AndAnd(methodWithMetrics)) hasDetection = true;
						// terceiro operador e AND
				else 
					if (ruleCombo.isGodLesserLesserLesser_AndOr(methodWithMetrics)) hasDetection = true;
				
			}else {
				// primeiro operador e OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					// segundo operador e AND
					if (ruleCombo.isGodLesserLesserLesser_OrAnd(methodWithMetrics)) hasDetection = true;
					
				else 
					if (ruleCombo.isGodLesserLesserLesser_OrOr(methodWithMetrics)) hasDetection = true;
			
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica,um de menor para a segunda e um de menor para a teceira. 
	 * @return ArrayList com resultado de aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro operador AND
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					// segundo operador AND
					if (ruleCombo.isGodGreaterLesserLesser_AndAnd(methodWithMetrics)) hasDetection = true;
						// terceiro operador AND
				else 
					if (ruleCombo.isGodGreaterLesserLesser_AndOr(methodWithMetrics)) hasDetection = true;
					
			}else {
				// primeiro operador OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					// segundo operador OR
					if (ruleCombo.isGodGreaterLesserLesser_OrAnd(methodWithMetrics)) hasDetection = true;
					
				else 
					if (ruleCombo.isGodGreaterLesserLesser_OrOr(methodWithMetrics)) hasDetection = true;
						
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;

	}
	
	

	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica,um de menor para a segunda e um de maior para a teceira. 
	 * @return ArrayList com resultado de aplicacao das regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterLesserGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro operador AND
				if(operator2.equals(AND_LOGIC_OPERATOR))
					// segundo operador AND
					if (ruleCombo.isGodGreaterLesserGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserGreater_AndOr(methodWithMetrics)) hasDetection = true;
				// segundo operador OR
			}else {
				// primeiro operador OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					// segundo AND
					if (ruleCombo.isGodGreaterLesserGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				else 
					if (ruleCombo.isGodGreaterLesserGreater_OrOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	


	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de maior para a primeira metrica,um de maior para a segunda e um de menor para a teceira. 
	 * @return ArrayList com resultado de aplicacao das regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassGreaterGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro AND
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodGreaterGreaterLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				// segundo AND
				else 
					if (ruleCombo.isGodGreaterGreaterLesser_AndOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}else {
				// primeiro OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodGreaterGreaterLesser_OrAnd(methodWithMetrics)) hasDetection = true;
				// segundo AND
				else 
					if (ruleCombo.isGodGreaterGreaterLesser_OrOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	


	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica,um de maior para a segunda e um de maior para a teceira. 
	 * @return ArrayList com resultado de aplicacao das regras
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserLesserGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro AND
				if(operator2.equals(AND_LOGIC_OPERATOR))
					if (ruleCombo.isGodLesserLesserGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				// segundo AND
				else 
					if (ruleCombo.isGodLesserLesserGreater_AndOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}else {
				
				// primeiro OR
				if(operator2.equals(AND_LOGIC_OPERATOR))
					
					if (ruleCombo.isGodLesserLesserGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				//segundo AND
				else 
					if (ruleCombo.isGodLesserLesserGreater_OrOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}

	
	/**
	 * Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica,um de maior para a segunda e um de maior para a teceira. 
	 * @return ArrayList com resultado de aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterGreater() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro AND
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterGreater_AndAnd(methodWithMetrics)) hasDetection = true;
				// segundo AND
				else 
					if (ruleCombo.isGodLesserGreaterGreater_AndOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}else {
				// primeiro OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterGreater_OrAnd(methodWithMetrics)) hasDetection = true;
				// segundo AND
				else 
					if (ruleCombo.isGodLesserGreaterGreater_OrOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}


	/**
	 * 	Este metodo e invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	as metricas WMC_Class, NOM_Class, LOC_CLASS e para esta detecao define os limites 
	com um sinal de menor para a primeira metrica,um de maior para a segunda e um de maior para a teceira. 
	 * @return ArrayList com resultao de aplicacao da regra
	 */
	public ArrayList<HasCodeSmell> detectGodClassLesserGreaterLesser() {
		for (MethodMetrics methodWithMetrics : results) {
			lastMethod = verifyLastClass(methodWithMetrics);
			if(operator.equals(AND_LOGIC_OPERATOR)) {
				// primeiro operador AND
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterLesser_AndAnd(methodWithMetrics)) hasDetection = true;
				// segundo OR
				else 
					if (ruleCombo.isGodLesserGreaterLesser_AndOr(methodWithMetrics)) hasDetection = true;
				// segundo AND
			}else {
				// primeiro OR
				if(operator2.equals(AND_LOGIC_OPERATOR)) 
					if (ruleCombo.isGodLesserGreaterLesser_OrAnd(methodWithMetrics)) hasDetection = true;
				// segundo AND
				else 
					if (ruleCombo.isGodLesserGreaterLesser_OrOr(methodWithMetrics)) hasDetection = true;
				// segundo OR
			}
			
			checkDetection(hasDetection,methodWithMetrics);
		}
		return readyToShow;
	}
	
	
	
	
}
