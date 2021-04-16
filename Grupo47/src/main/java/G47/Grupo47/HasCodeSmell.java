package G47.Grupo47;

public class HasCodeSmell {
	private String method_name;
	private String hasCodeSmell;
	private String method_id;

	public HasCodeSmell(String name, String hasCodeSmell,String method_id) {
		this.method_name = name;
		this.hasCodeSmell = hasCodeSmell;
		this.method_id = method_id;

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

}
