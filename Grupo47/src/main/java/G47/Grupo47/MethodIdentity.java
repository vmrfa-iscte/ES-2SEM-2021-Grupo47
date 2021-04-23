package G47.Grupo47;

public class MethodIdentity{
	private String methodName;
	private String className;
	private  String packageName;
	private  int method_id;
	
	// Objeto que agrupa os elementos capazes de identificar um único método
	public MethodIdentity(String methodName, String className, String packageName, int method_id) {
		this.methodName = methodName;
		this.className = className;
		this.packageName = packageName;
		this.method_id = method_id;
	}
	
	// Getters
	public String getMethodName() {
		return methodName;
	}

	public String getClassName() {
		return className;
	}

	public String getPackageName() {
		return packageName;
	}

	public int getMethod_id() {
		return method_id;
	}
	
	
}