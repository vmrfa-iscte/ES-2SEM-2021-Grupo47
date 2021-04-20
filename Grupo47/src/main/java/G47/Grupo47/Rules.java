package G47.Grupo47;

public class Rules {
	private String method1;
	private String sinal1;
	private String limit1;
	private String operator;
	private String method2;
	private String sinal2;
	private String limit2;

	private String operator2;
	private String method3;
	private String sinal3;
	private String limit3;
//
//	public Rules(String method1, String sinal1, String limit1, String operator, String method2, String sinal2,
//			String limit2) {
//		this.sinal1 = sinal1;
//		this.sinal2 = sinal2;
//		this.method1 = method1;
//		this.limit1 = limit1;
//		this.operator = operator;
//		this.method2 = method2;
//		this.limit2 = limit2;
//
//	}

	public Rules(String method1, String sinal1, String limit1, String operator, String method2, String sinal2,
			String limit2, String operator2, String method3, String sinal3, String limit3) {
		this.method1 = method1;
		this.sinal1 = sinal1;
		this.limit1 = limit1;
		this.operator = operator;
		this.method2 = method2;
		this.sinal2 = sinal2;
		this.limit2 = limit2;
		this.operator2 = operator2;
		this.method3 = method3;
		this.sinal3 = sinal3;
		this.limit3 = limit3;
	}

	public String getLimit3() {
		return limit3;
	}

	public void setLimit3(String limit3) {
		this.limit3 = limit3;
	}

	public String getOperator2() {
		return operator2;
	}

	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}

	public String getSinal1() {
		return sinal1;
	}

	public void setSinal1(String sinal1) {
		this.sinal1 = sinal1;
	}

	public String getSinal2() {
		return sinal2;
	}

	public void setSinal2(String sinal2) {
		this.sinal2 = sinal2;
	}

	public String getSinal3() {
		return sinal3;
	}

	public void setSinal3(String sinal3) {
		this.sinal3 = sinal3;
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

//	@Override
//	public String toString() {
//		return method1 + " " + sinal1 + " " + limit1 + " " + operator + " " + method2 + " " + sinal2 + " " + limit2;
//	}

	public String toString() {
		return method1 + " " + sinal1 + " " + limit1 + " " + operator + " " + method2 + " " + sinal2 + " " + limit2
				+ " " + operator2 + " " + method3 + " " + sinal3 + " " + limit3;
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
