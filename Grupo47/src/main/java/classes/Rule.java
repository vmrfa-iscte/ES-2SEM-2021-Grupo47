package classes;

public class Rule {

	// CLASSE RULES, OBJETO USADO PARA CRIAR REGRAS DEFINIDAS PELO UTILIZADOR ATRAVÉS DA GUI.CLASSE COMPOSTA POR GETTERS E SETTERS;
	/**
	 * @author Guy Turpin
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
	 * @return Limite da terceira métrica
	 */
	public String getLimit3() {
		return limit3;
	}

	/**
	 * @param Define o limite da terceira métrica
	 */
	public void setLimit3(String limit3) {
		this.limit3 = limit3;
	}

	/**
	 * @return 2º Operador definido pelo utilizador na GUI
	 */
	public String getOperator2() {
		return operator2;
	}

	/**
	 * @param Altera o 2º operador definido pelo utilizador na GUI
	 */
	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}

	/**
	 * @return 1º sinal definido pelo utilizador na GUI
	 */
	public String getSinal1() {
		return signal1;
	}

	/**
	 * @param Define/altera o 1º sinal da regra definida pelo utilizador na GUI
	 */
	public void setSinal1(String sinal1) {
		this.signal1 = sinal1;
	}

	public String getSinal2() {
		return signal2;
	}

	public void setSinal2(String sinal2) {
		this.signal2 = sinal2;
	}

	public String getSinal3() {
		return signal3;
	}

	public void setSinal3(String sinal3) {
		this.signal3 = sinal3;
	}

	public void setLimit1(String limit1) {
		this.limit1 = limit1;
	}

	public void setLimit2(String limit2) {
		this.limit2 = limit2;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	//MÉTODO BASTANTE IMPORANTE NO FORMATO EM QUE SE ENCONTRA, VISTO QUE É ATRAVÉS DELE QUE AS REGRAS SÃO DEMONSTRADAS AO UTILIZADOR NA GUI.
	public String toString() {
		return method1 + " " + signal1 + " " + limit1 + " " + operator + " " + method2 + " " + signal2 + " " + limit2
				+ " " + operator2 + " " + method3 + " " + signal3 + " " + limit3;
	}

	public void setMethod1(String method1) {
		this.method1 = method1;
	}

	public String getLimit1() {
		return limit1;
	}

	public String getLimit2() {
		return limit2;
	}

	public String getOperator() {
		return operator;
	}

	public String getMethod1() {
		return method1;
	}

	public String getMethod2() {
		return method2;
	}

	public void setMethod2(String method2) {
		this.method2 = method2;
	}

	public String getMethod3() {
		return method3;
	}

	public void setMethod3(String method3) {
		this.method3 = method3;
	}

}
