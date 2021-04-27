package classes;

/**
 * @author Vasco Fontoura
 *
 */
public class MethodIdentity{
	private String methodName;
	private String className;
	private  String packageName;
	private  int method_id;
	
	
	/**
	 * @param nome do método
	 * @param nome da classe
	 * @param nome do pacote
	 * @param número de id do método
	 */
	public MethodIdentity(String methodName, String className, String packageName, int method_id) {
		// Objeto que agrupa os elementos capazes de identificar um único método
		this.methodName = methodName;
		this.className = className;
		this.packageName = packageName;
		this.method_id = method_id;
	}
	
	// Getters
	/**
	 * @return nome do método
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return nome da classe
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return nome do pacote
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return id do método
	 */
	public int getMethod_id() {
		return method_id;
	}
	
	
}