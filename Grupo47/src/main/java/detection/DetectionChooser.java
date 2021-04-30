package detection;

import java.util.ArrayList;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Rule;

/**
 *  Esta classe tem por objetivo decidir qual a deteção adequada segundo uma regra recebida, após decisão vai efetuar a deteção
	 e retornar uma lista com os resultados
 * @author Tomás Mendes
 * @version
 *
 */
public class DetectionChooser {
	 
	
	private static final String GREATER_THAN = ">", LESSER_THAN = "<";
	
	/** Decide qual a vertente da regra a aplicar consoante os sinais, aplica a vertente correta e retorna os resultados
	 * @param ruleReceived regra recebida
	 * @param actualmetrics Array com metodos e suas respetivas metricas
	 * @return	ArrayList com resultados de detecao
	 */
	public ArrayList<HasCodeSmell> chooseDetectionLocMethod(Rule ruleReceived,ArrayList<MethodMetrics> actualmetrics) {
		CodeSmellsDetector detector = new CodeSmellsDetector(actualmetrics,ruleReceived);
		ArrayList<HasCodeSmell> hasCodeSmellResult = new ArrayList<>();
		String firstSignal = ruleReceived.getSinal1();
		String secondSignal = ruleReceived.getSinal2();
		if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector.detectLongMethodGreaterGreater();
		else if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector.detectLongMethodGreaterLesser();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector.detectLongMethodSmallerBigger();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector.detectLongMethodLesserLesser();
		return hasCodeSmellResult;
	}
	
	/**
 	Decide qual a vertente da regra a aplicar consoante os sinais, aplica a vertente correta e retorna os resultados
	  @param ruleReceived regra recebida
	  @param actualmetrics Array com metodos e suas respetivas metricas
	  @return	ArrayList com resultados de detecao
	 */
	public ArrayList<HasCodeSmell> chooseDetectionWMC_NOM(Rule ruleReceived,ArrayList<MethodMetrics> actualmetrics) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = new ArrayList<>();
		CodeSmellsDetector detector2 = new CodeSmellsDetector(actualmetrics,ruleReceived);
		String firstSignal = ruleReceived.getSinal1();
		String secondSignal = ruleReceived.getSinal2();
		if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector2.detectGodClassGreaterGreaterWMC_NOM();
		else if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector2.detectGodClassGreaterLesserWMC_NOM();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector2.detectGodClassLesserGreaterWMC_NOM();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector2.detectGodClassLesserLesserWMC_NOM();
		return hasCodeSmellResult;
	}
	
	/**
	 Decide qual a vertente da regra a aplicar consoante os sinais, aplica a vertente correta e retorna os resultados
	  @param ruleReceived regra recebida
	  @param actualmetrics Array com metodos e suas respetivas metricas
	  @return	ArrayList com resultados de detecao
	 */
	public ArrayList<HasCodeSmell> chooseDetectionWMC_LOC(Rule ruleReceived,ArrayList<MethodMetrics> actualmetrics) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = new ArrayList<>();
		CodeSmellsDetector detector2 = new CodeSmellsDetector(actualmetrics,ruleReceived);
		String firstSignal = ruleReceived.getSinal1();
		String secondSignal = ruleReceived.getSinal2();
		if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector2.detectGodClassGreaterGreaterWMC_LOC();
		else if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector2.detectGodClassGreaterLesserWMC_LOC();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector2.detectGodClassLesserGreaterWMC_LOC();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector2.detectGodClassLesserLesserWMC_LOC();
		return hasCodeSmellResult;
	}
	
	/**
	 Decide qual a vertente da regra a aplicar consoante os sinais, aplica a vertente correta e retorna os resultados
	  @param ruleReceived regra recebida
	  @param actualmetrics Array com metodos e suas respetivas metricas
	  @return	ArrayList com resultados de detecao
	 */
	public ArrayList<HasCodeSmell> chooseDetectionNOM_LOC(Rule ruleReceived,ArrayList<MethodMetrics> actualmetrics) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = new ArrayList<>();
		CodeSmellsDetector detector2 = new CodeSmellsDetector(actualmetrics,ruleReceived);
		String firstSignal = ruleReceived.getSinal1();
		String secondSignal = ruleReceived.getSinal2();
		if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector2.detectGodClassGreaterGreaterNOM_LOC();
		else if (firstSignal.equals(GREATER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector2.detectGodClassGreaterLesserNOM_LOC();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(GREATER_THAN)) hasCodeSmellResult = detector2.detectGodClassLesserGreaterNOM_LOC();
		else if (firstSignal.equals(LESSER_THAN) && secondSignal.equals(LESSER_THAN)) hasCodeSmellResult = detector2.detectGodClassLesserLesserNOM_LOC();
		return hasCodeSmellResult;
	}

	/**
 Decide qual a vertente da regra a aplicar consoante os sinais, aplica a vertente correta e retorna os resultados
	  @param ruleReceived regra recebida
	  @param actualmetrics Array com metodos e suas respetivas metricas
	  @return	ArrayList com resultados de detecao
	 */
	public ArrayList<HasCodeSmell> chooseDetectionWMC_NOM_LOC(Rule ruleReceived,ArrayList<MethodMetrics> actualmetrics) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = new ArrayList<>();
		CodeSmellsDetector detector = new CodeSmellsDetector(actualmetrics, ruleReceived);
		String firstSignal = ruleReceived.getSinal1();
		String secondSignal = ruleReceived.getSinal2();
		String thirdSignal = ruleReceived.getSinal3();
		if (isGreaterGreaterGreater(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassGreaterGreaterGreater();
		else if (isLesserLesserLesser(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassLesserLesserLesser();
		else if (isGreaterLesserLesser(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassGreaterLesserLesser();
		else if (isGreaterLesserGreater(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassGreaterLesserGreater();
		else if (isGreaterGreaterLesser(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassGreaterGreaterLesser();
		else if (isLesserLesserGreater(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassLesserLesserGreater();
		else if (isLesserGreaterGreater(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassLesserGreaterGreater();
		else if (isLesserGreaterLesser(firstSignal,secondSignal,thirdSignal))
			hasCodeSmellResult = detector.detectGodClassLesserGreaterLesser();
		return hasCodeSmellResult;
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a >,>,>
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isGreaterGreaterGreater(String signal1, String signal2, String signal3) {
		return (signal1.equals(GREATER_THAN) && signal2.equals(GREATER_THAN) && signal3.equals(GREATER_THAN)) ;
		
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a <,<,<
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isLesserLesserLesser(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(LESSER_THAN) && secondSignal.equals(LESSER_THAN) && thirdSignal.equals(LESSER_THAN)) ;
		
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a >,<,<
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isGreaterLesserLesser(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(GREATER_THAN) && secondSignal.equals(LESSER_THAN) && thirdSignal.equals(LESSER_THAN)) ;
		
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a >,>,<
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isGreaterGreaterLesser(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(GREATER_THAN) && secondSignal.equals(GREATER_THAN) && thirdSignal.equals(LESSER_THAN)) ;
		
	}
	
	/**
		 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a >,<,>
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isGreaterLesserGreater(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(GREATER_THAN) && secondSignal.equals(LESSER_THAN) && thirdSignal.equals(GREATER_THAN)) ;
		
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a <,<,>
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isLesserLesserGreater(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(LESSER_THAN) && secondSignal.equals(LESSER_THAN) && thirdSignal.equals(GREATER_THAN)) ;
		
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a <,>,>
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isLesserGreaterGreater(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(LESSER_THAN) && secondSignal.equals(GREATER_THAN) && thirdSignal.equals(GREATER_THAN)) ;
		
	}
	
	/**
	 * Retorna se um conjunto de 3 sinais de uma dada regra correspondem a <,>,<
	 * @param signal1
	 * @param signal2
	 * @param signal3
	 * @return booleano que indica se sim ou se nao
	 */
	private boolean isLesserGreaterLesser(String firstSignal,String secondSignal, String thirdSignal) {
		return (firstSignal.equals(LESSER_THAN) && secondSignal.equals(GREATER_THAN) && thirdSignal.equals(LESSER_THAN)) ;
		
	}
	
	
}
