package G47.Grupo47;

public class Rules {
	
		private String limit1;
		private String limit2;
		private String operator;
		private String method1;
		private String method2;

		public Rules(String method1,String limit1, String operator, String method2, String limit2) {
			this.method1 = method1;
			this.limit1 = limit1;
			this.operator = operator;
			this.method2 = method2;
			this.limit2 = limit2;

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

		@Override
		public String toString() {
			return  method1 + " > " + " " + limit1+ " " + operator + " " + method2+ " > " + " " + limit2;
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

	}


