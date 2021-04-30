package classes;

public class Rule {

	// CLASSE RULES, OBJETO USADO PARA CRIAR REGRAS DEFINIDAS PELO UTILIZADOR ATRAVÉS DA GUI.CLASSE COMPOSTA POR GETTERS E SETTERS;
	/**
	 * Classe rules: objeto criado com base nas escolhas do utilizador, através da GUI
	 * 
	 * @author Guy Turpin
	 *  
	 */
	private String method1;
	private String signal1;
	private String limit1;
	private String operator;
	private String method2;
	private String signal2;
	private String limit2;
	private String operator2;
	private String method3;
	private String signal3;
	private String limit3;

	// CONSTRUTOR DO OBJETO RULES;
	/**
	 * @param method1
	 * @param sinal1
	 * @param limit1
	 * @param operator
	 * @param method2
	 * @param sinal2
	 * @param limit2
	 * @param operator2
	 * @param method3
	 * @param sinal3
	 * @param limit3
	 */
	public Rule(String method1, String sinal1, String limit1, String operator, String method2, String sinal2,
			String limit2, String operator2, String method3, String sinal3, String limit3) {
		this.method1 = method1;
		this.signal1 = sinal1;
		this.limit1 = limit1;
		this.operator = operator;
		this.method2 = method2;
		this.signal2 = sinal2;
		this.limit2 = limit2;
		this.operator2 = operator2;
		this.method3 = method3;
		this.signal3 = sinal3;
		this.limit3 = limit3;
	}

	/**
	 * Retorna o Limite da metrica 3
	 * 
	 * @return limit3 
	 */
	public String getLimit3() {
		return limit3;
	}

	/**
	 * Define o limite da metrica 3
	 * 
	 * @param limit3 
	 */
	public void setLimit3(String limit3) {
		this.limit3 = limit3;
	}

	/**
	 * Devolve o valor do 2º operador
	 * 
	 * @return operator2 
	 */
	public String getOperator2() {
		return operator2;
	}

	/**
	 * Define o operador 2
	 * 
	 * @param operator2 
	 */
	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}

	/**
	 * Sinal da primeira metrica
	 * 
	 * @return signal1 
	 */
	public String getSinal1() {
		return signal1;
	}

	/**
	 * Define o sinal da metrica 1
	 * 
	 * @param sinal1 
	 */
	public void setSinal1(String sinal1) {
		this.signal1 = sinal1;
	}

	/**
	 * Devolve o sinal da metrica 2
	 * 
	 * @return signal2 
	 */
	public String getSinal2() {
		return signal2;
	}

	/**
	 * Define o sinal de metrica 2
	 * 
	 * @param sinal2 
	 */
	public void setSinal2(String sinal2) {
		this.signal2 = sinal2;
	}

	/**
	 * Devolve o sinal da metrica 3
	 * 
	 * @return signal3 
	 */
	public String getSinal3() {
		return signal3;
	}

	/**
	 * Define o sinal da metrica 3
	 * 
	 * @param sinal3 
	 */
	public void setSinal3(String sinal3) {
		this.signal3 = sinal3;
	}

	/**
	 * Define o limite da metrica 1
	 * 
	 * @param limit1 
	 */
	public void setLimit1(String limit1) {
		this.limit1 = limit1;
	}

	/**
	 * Define o limite da metrica 2
	 * 
	 * @param limit2 
	 */
	public void setLimit2(String limit2) {
		this.limit2 = limit2;
	}

	/**
	 * Define o operador que relaciona as metricas 1 e 2
	 * 
	 * @param operator 
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	//MÉTODO BASTANTE IMPORANTE NO FORMATO EM QUE SE ENCONTRA, VISTO QUE É ATRAVÉS DELE QUE AS REGRAS SÃO DEMONSTRADAS AO UTILIZADOR NA GUI.
	public String toString() {
		return method1 + " " + signal1 + " " + limit1 + " " + operator + " " + method2 + " " + signal2 + " " + limit2
				+ " " + operator2 + " " + method3 + " " + signal3 + " " + limit3;
	}

	/**
	 * Define a metrica 1
	 * 
	 * @param method1 
	 */
	public void setMethod1(String method1) {
		this.method1 = method1;
	}

	/**
	 * Devolve o limite da metrica 1 
	 * 
	 * @return limit1 
	 */
	public String getLimit1() {
		return limit1;
	}

	/**
	 *  Devolve o limite da metrica 2
	 * 
	 * @return limit2 
	 */
	public String getLimit2() {
		return limit2;
	}

	/**
	 * Devolve o operador que relaciona as metricas 1 e 2
	 * 
	 * @return operator 
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Devolve a metrica 1
	 * 
	 * @return method1 
	 */
	public String getMethod1() {
		return method1;
	}

	/**
	 * Devolve a metrica 2
	 * 
	 * @return method2 
	 */
	public String getMethod2() {
		return method2;
	}

	/**
	 *  Define a metrica 2
	 * 
	 * @param method2
	 */
	public void setMethod2(String method2) {
		this.method2 = method2;
	}

	/**
	 * Devolve a metrica 3
	 * 
	 * @return method3 
	 */
	public String getMethod3() {
		return method3;
	}

	/**
	 * Define a metrica 3
	 * 
	 * @param method3 
	 */
	public void setMethod3(String method3) {
		this.method3 = method3;
	}

}
