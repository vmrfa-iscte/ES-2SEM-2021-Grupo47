package classes;

/**
 * Objeto MethodIdentity contém informacao que distingue um metodo, ou seja, nome do metodo (contem parametros),
 * nome da classe a que pertence, nome do pacote a que pertence e id do metodo.
 * @author Vasco Fontoura
 *
 */
public class MethodIdentity{
	private String methodName;
	private String className;
	private  String packageName;
	private  int method_id;
	
	
	/**
	 * Construtor
	 * @param methodName nome do metodo
	 * @param className nome da classe
	 * @param packageName nome do pacote
	 * @param method_id número de id do metodo
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
	 * Getter para o nome do metodo
	 * @return nome do metodo
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Getter para o nome da classe do metodo
	 * @return nome da classe
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Getter para o nome do pacote do metodo
	 * @return nome do pacote
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Getter para o id do metodo
	 * @return id do metodo
	 */
	public int getMethod_id() {
		return method_id;
	}
	
	
}