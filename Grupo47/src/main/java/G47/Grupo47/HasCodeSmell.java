package G47.Grupo47;

public class HasCodeSmell {
	private String method_name;
	private String hasCodeSmell;
	private String method_id;
	private String package_name;
	private String class_name;
	private String isPositiveOrNegative;
	
	public HasCodeSmell(String hasCodeSmell, String PositiveOrNegative,MethodMetrics method) {
		this.method_name = method.getNome_metodo();
		this.hasCodeSmell = hasCodeSmell;
		this.method_id = String.valueOf(method.getMethod_ID());
		this.package_name = method.getPacote();
		this.class_name = method.getClasse();
		this.isPositiveOrNegative = isPositiveOrNegative;
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
