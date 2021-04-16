package G47.Grupo47;

public class HasCodeSmell {
	private String name;
	private String hasCodeSmell;
	private String method_id;

	public HasCodeSmell(String name, String hasCodeSmell,String method_id) {
		this.name = name;
		this.hasCodeSmell = hasCodeSmell;
		this.method_id = method_id;

	}
	
	public String getMethod_ID() {
		return method_id;
	}

	public String getName() {
		return name;
	}

	public String getHasCodeSmell() {
		return hasCodeSmell;
	}

	
	
}
