package G47.Grupo47;

public class HasCodeSmell {
	private String method_name;
	private String hasCodeSmell;
	private String method_id;
	private String package_name;
	private String class_name;
	private String isPositiveOrNegative;
	private boolean isMethod;
	
	public HasCodeSmell(String hasCodeSmell, String PositiveOrNegative,MethodMetrics method,boolean isMethod) {
		this.isMethod = isMethod;
		if(isMethod) {
			this.method_name = method.getNome_metodo();
			this.method_id = String.valueOf(method.getMethod_ID());
		}
		this.hasCodeSmell = hasCodeSmell;
		this.package_name = method.getPacote();
		this.class_name = method.getClasse();
		this.isPositiveOrNegative = isPositiveOrNegative;
	}
	
	public boolean isMethod() {
		return isMethod;
	}

	public void setMethod(boolean isMethod) {
		this.isMethod = isMethod;
	}

	public String getMethod_ID() {
		return method_id;
	}

	public String getMethodName() {
		return method_name;
	}

	public String getHasCodeSmell() {
		return hasCodeSmell;
	}
	
	public String getClassName() {
		return class_name;
	}
	
	public String getPackageName() {
		return package_name;
	}
	
	public String getQuality() {
		return isPositiveOrNegative;
	}
	
	public void setQuality (String a)  {
		this.isPositiveOrNegative = a;
	}
	
	

	
	
}
