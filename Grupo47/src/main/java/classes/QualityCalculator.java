package classes;

public class QualityCalculator {
	
	public static final String TRUE_NEGATIVE = "Verdadeiro Negativo";
	public static final String TRUE_POSITIVE = "Verdadeiro Positivo";
	public static final String FALSE_NEGATIVE = "Falso Negativo";
	public static final String FALSE_POSITIVE = "Falso Positivo";
	public static final String QUALITY_POSITIVE = "Positivo";
	public static final String QUALITY_NEGATIVE = "Negativo";
	public static final String DETECTION_FALSE = "FALSE";
	public static final String DETECTION_TRUE = "TRUE";
	
	// Método que deteta verdadeiros negativos
	public boolean isTrueNegative(HasCodeSmell indicator, HasCodeSmell calculated) {
		return calculated.getHasCodeSmell().equals(DETECTION_FALSE)
				&& indicator.getHasCodeSmell().equals(DETECTION_FALSE);
	}
	
	// Método que deteta falsos positivos
	public boolean isFalsePositive(HasCodeSmell indicator, HasCodeSmell calculated) {
		return calculated.getHasCodeSmell().equals(DETECTION_FALSE)
				&& indicator.getHasCodeSmell().equals(DETECTION_TRUE);
	}
	
	// Método que deteta falsos negativos
	public boolean isFalseNegative(HasCodeSmell indicator, HasCodeSmell calculated) {
		return calculated.getHasCodeSmell().equals(DETECTION_TRUE)
				&& indicator.getHasCodeSmell().equals(DETECTION_FALSE);
	}

	// Método que deteta verdadeiros positivos
	public boolean isTruePositive(HasCodeSmell indicator, HasCodeSmell calculated) {
		return calculated.getHasCodeSmell().equals(DETECTION_TRUE) && indicator.getHasCodeSmell().equals(DETECTION_TRUE);
	}
	
	// Compara dois HasCodeSmells e consoante a deteção da qualidade edita a qualidade do "indicator"
	public void setQuality(HasCodeSmell indicator, HasCodeSmell calculated) {
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
