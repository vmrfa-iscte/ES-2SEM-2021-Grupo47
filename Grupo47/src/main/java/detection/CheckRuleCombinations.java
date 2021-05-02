package detection;

import classes.MethodMetrics;

/**
 * Esta classe retorna um booleano que coincide com o resultado da detecao de um code smell segundo os valores das regras definidas
para cada metodo varia os sinais utilizados (maior e menor) e os operadores utilizados ("e" e "ou").
 O objetivo da classe e ter todas as combinacoes de regras possíveis com as metricas possiveis, tendo em conta os operadores e sinais

 * @author Tomas Mendes
 * @author Vasco Fontoura
 * @version 4.0


 *
 */
public class CheckRuleCombinations {
	

	
	private int rule1_threshold;
	private int rule2_threshold;
	private int rule3_threshold;
	
	/** Construtor para 3 thresholds
	 * @param rule1_threshold valor que limita a primeira metrica
	 * @param rule2_threshold valor que limita a segunda metrica
	 * @param rule3_threshold valor que limita o terceira metrica
	 */
	public CheckRuleCombinations(int rule1_threshold,int rule2_threshold, int rule3_threshold) {
		this.rule1_threshold = rule1_threshold;
		this.rule2_threshold = rule2_threshold;
		this.rule3_threshold = rule3_threshold;
	}
	
	/**
	 * Construtor para 2 thresholds
	 * @param rule1_threshold valor que limita a primeira metrica
	 * @param rule2_threshold valor que limita a segunda metrica
	 */
	public CheckRuleCombinations(int rule1_threshold,int rule2_threshold) {
		this.rule1_threshold = rule1_threshold;
		this.rule2_threshold = rule2_threshold;
	}

	
	/**
	  Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "e" e "e"
	  @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	  @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold;
	}
	
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) ;
	}
	
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "ou" e "e"
	 * @param methodWithMetrics identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	

	/**
	 * 	Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classficacao
	 */
	public boolean isGodLesserGreaterGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	

	/**
	 * 	Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);		
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/** Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e menor e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e menor e com os sinais "ou" e "e"
	 * @param methodWithMetrics onjeto que identifica metodo e suas metricas
	 * @return classificaao
	 */
	public boolean isGodGreaterGreaterLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e menor e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e maior e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica metofo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e maior e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e maior e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e maior e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e menor e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e menor e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e menor e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e menor e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "e" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "e" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e menor e com os sinais "ou" e "e"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e maior e com os sinais "ou" e "ou"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 *  Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as sua metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que indentifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);	
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica metodo e as suas metricas
	 * @return classficacao
	 */
	public boolean isGodGreaterGreaterNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que indentifica metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterNOM_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classficacao
	 */
	public boolean isGodLesserGreaterNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserNOM_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserWMC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterNOM_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isLongLesserLesserAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold && methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isLongLesserLesserOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold || methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isLongLesserGreaterAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold && methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores menor, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isLongLesserGreaterOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold || methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "e" 
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isLongGreaterGreaterAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold && methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isLongGreaterGreaterOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold || methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, maior e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isLongGreaterLesserAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold && methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
	/**
	 * Verfica se o metodo tem code smell isGod segundo os operadores maior, menor e com os sinais "ou" 
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isLongGreaterLesserOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold || methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
}
