package classes;

/**
 * Classe responsavel por detetar a qualidade de detecao, ou seja, Verdadeiros Positivos, Falsos Postivos, Falsos Negativos
 * e Verdadeiros Negativos
 * @author Vasco Fontoura
 * @version 1
 *
 */
public class QualityCalculator {
	
	/**
	 * 
	 */
	public static final String TRUE_NEGATIVE = "Verdadeiro Negativo";
	public static final String TRUE_POSITIVE = "Verdadeiro Positivo";
	public static final String FALSE_NEGATIVE = "Falso Negativo";
	public static final String FALSE_POSITIVE = "Falso Positivo";
	public static final String QUALITY_POSITIVE = "Positivo";
	public static final String QUALITY_NEGATIVE = "Negativo";
	public static final String DETECTION_FALSE = "FALSE";
	public static final String DETECTION_TRUE = "TRUE";
	
	
	/**
	 * Compara duas detecoes e verifica se se trata de um verdadeiro negativo
	 * @param indicator HasCodeSmell detetado
	 * @param calculated HasCodeSmell calculado
	 * @return boleano que indica a qualidade do HasCodeSmell detetado
	 */
	public boolean isTrueNegative(HasCodeSmell indicator, HasCodeSmell calculated) {
		// Método que deteta verdadeiros negativos
		return calculated.getHasCodeSmell().equals(DETECTION_FALSE)
				&& indicator.getHasCodeSmell().equals(DETECTION_FALSE);
	}
	
	/**
	 * Compara duas detecoes e verifica se se trata de um falso positivo
	 * @param indicator HasCodeSmell detetado
	 * @param calculated HasCodeSmell calculado
	 * @return boleano que indica a qualidade do HasCodeSmell detetado
	 */
	public boolean isFalsePositive(HasCodeSmell indicator, HasCodeSmell calculated) {
		// Método que deteta falsos positivos
		return calculated.getHasCodeSmell().equals(DETECTION_FALSE)
				&& indicator.getHasCodeSmell().equals(DETECTION_TRUE);
	}
	
	/**
	 * Compara duas detecoes e verifica se se trata de um falso negativo
	 * @param indicator HasCodeSmell detetado
	 * @param calculated HasCodeSmell calculado
	 * @return boleano que indica a qualidade do HasCodeSmell detetado
	 */
	public boolean isFalseNegative(HasCodeSmell indicator, HasCodeSmell calculated) {
		// Método que deteta falsos negativos
		return calculated.getHasCodeSmell().equals(DETECTION_TRUE)
				&& indicator.getHasCodeSmell().equals(DETECTION_FALSE);
	}

	/**
	 * Compara duas detecoes e verifica se se trata de um verdadeiro positivo
	* @param indicator HasCodeSmell detetado
	 * @param calculated HasCodeSmell calculado
	 * @return boleano que indica a qualidade do HasCodeSmell detetado
	 */
	public boolean isTruePositive(HasCodeSmell indicator, HasCodeSmell calculated) {
		// Método que deteta verdadeiros positivos
		return calculated.getHasCodeSmell().equals(DETECTION_TRUE) && indicator.getHasCodeSmell().equals(DETECTION_TRUE);
	}
	
	/**
	 * Compara duas detecoes e altera o campo qualidade consoante a qualidade de detecao
	 * @param indicator HasCodeSmell detetado
	 * @param calculated HasCodeSmell calculado
	 */
	public void setQuality(HasCodeSmell indicator, HasCodeSmell calculated) {
		// Compara dois HasCodeSmells e consoante a deteção da qualidade edita a qualidade do "indicator"
		if (isFalsePositive(indicator,calculated)) 
			indicator.setQuality("Falso Positivo");
		else if (isFalsePositive(indicator,calculated))
			indicator.setQuality("Falso Negativo");
		else if(isTrueNegative(indicator,calculated))
			indicator.setQuality("Verdadeiro Negativo");
		else if(isTruePositive(indicator,calculated))
			indicator.setQuality("Verdadeiro Positivo");
	}

}
