package detection;

import classes.MethodMetrics;

/**
 * Esta classe retorna um booleano que coincide com o resultado da deteção de um code smell segundo os valores das regras definidas
para cada método varia os sinais utilizados (maior e menor) e os operadores utilizados ("e" e "ou").
 O objetivo da classe é ter todas as combinações de regras possíveis com as métricas possíveis, tendo em conta os operadores e sinais
 * @author Tomás Mendes
 * @version
 *
 */
public class CheckRuleCombinations {
	

	
	private int rule1_threshold;
	private int rule2_threshold;
	private int rule3_threshold;
	
	/** Construtor para 3 thresholds
	 * @param rule1_threshold
	 * @param rule2_threshold
	 * @param rule3_threshold
	 */
	public CheckRuleCombinations(int rule1_threshold,int rule2_threshold, int rule3_threshold) {
		this.rule1_threshold = rule1_threshold;
		this.rule2_threshold = rule2_threshold;
		this.rule3_threshold = rule3_threshold;
	}
	
	/**
	 * Construtor para 2 thresholds
	 * @param rule1_threshold
	 * @param rule2_threshold
	 */
	public CheckRuleCombinations(int rule1_threshold,int rule2_threshold) {
		this.rule1_threshold = rule1_threshold;
		this.rule2_threshold = rule2_threshold;
	}

	
	/**
	  Verfica se o método tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "&&" e "&&"
	  @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	  @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold;
	}
	
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "&&" e "||"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) ;
	}
	
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "||" e "&&"
	 * @param methodWithMetrics identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	
	/**
	 *  Verfica se o método tem code smell isGod segundo os operadores menor, maior e menor e com os sinais "||" e "||"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	

	/**
	 * 	Verfica se o método tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "&&" e "&&"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classficacao
	 */
	public boolean isGodLesserGreaterGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	

	/**
	 * 	Verfica se o método tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "&&" e "||"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	
	/**
	 *  Verfica se o método tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "||" e "&&"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, maior e maior e com os sinais "||" e "||"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserGreaterGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "&&" e "&&"
	 * @param methodWithMetrics
	 * @return
	 */
	public boolean isGodLesserLesserGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "&&" e "||"
	 * @param methodWithMetrics objeto que identifica um metodo e as suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "||" e "&&"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "||" e "||"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodLesserLesserGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);		
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores menor, menor e maior e com os sinais "||" e "&&"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/** Verfica se o método tem code smell isGod segundo os operadores maior, maior e menor e com os sinais "&&" e "||"
	 * @param methodWithMetrics objeto que identifica um metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores maior, maior e menor e com os sinais "||" e "&&"
	 * @param methodWithMetrics onjeto que identifica metodo e suas metricas
	 * @return classificaao
	 */
	public boolean isGodGreaterGreaterLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores maior, maior e menor e com os sinais "||" e "||"
	 * @param methodWithMetrics objeto que identifica metodo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterGreaterLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	/**
	 * Verfica se o método tem code smell isGod segundo os operadores maior, menor e maior e com os sinais "&&" e "&&"
	 * @param methodWithMetrics objeto que identifica metofo e suas metricas
	 * @return classificacao
	 */
	public boolean isGodGreaterLesserGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodGreaterLesserGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodGreaterLesserGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodGreaterLesserGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodGreaterLesserLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodGreaterLesserLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodGreaterLesserLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodGreaterLesserLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodLesserLesserLesser_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodLesserLesserLesser_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodLesserLesserLesser_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodLesserLesserLesser_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold);
	}
	
	public boolean isGodGreaterGreaterGreater_AndAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodGreaterGreaterGreater_AndOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodGreaterGreaterGreater_OrAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodGreaterGreaterGreater_OrOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold);
	}
	
	public boolean isGodLesserLesserNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodLesserLesserNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodLesserGreaterNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodLesserGreaterNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() < rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodGreaterLesserNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterLesserNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);	
	}
	
	public boolean isGodGreaterGreaterNOMLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodGreaterGreaterNOMLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getNOM_class() > rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodLesserGreaterLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodLesserGreaterLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodLesserLesserLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodLesserLesserLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterLesserLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterLesserLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterGreaterLOC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodGreaterGreaterLOC_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold);
	}
	
	public boolean isGodLesserGreaterNOM_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	public boolean isGodLesserGreaterNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	public boolean isGodLesserLesserNOM_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	public boolean isGodLesserLesserNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterLesserWMC_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterLesserNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold);
	}
	
	public boolean isGodGreaterGreaterNOM_And(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	public boolean isGodGreaterGreaterNOM_Or(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold);
	}
	
	public boolean isLongLesserLesserAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold && methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
	public boolean isLongLesserLesserOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold || methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
	public boolean isLongLesserGreaterAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold && methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	public boolean isLongLesserGreaterOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() < rule1_threshold || methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	public boolean isLongGGAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold && methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	public boolean isLongGGOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold || methodWithMetrics.getCYCLO_method() > rule2_threshold);
	}
	
	public boolean isLongGLAnd(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold && methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
	public boolean isLongGLOr(MethodMetrics methodWithMetrics) {
		return (methodWithMetrics.getLOC_method() > rule1_threshold || methodWithMetrics.getCYCLO_method() < rule2_threshold);
	}
	
}
