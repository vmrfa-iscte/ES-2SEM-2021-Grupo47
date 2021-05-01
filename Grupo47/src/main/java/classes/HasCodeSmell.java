package classes;

/**
 * Classe utilizada para identificar um método de um dado projeto, assim como a classificação atribuida consoante determinada regra
 * @author Tomás Mendes
 * @version
 *
 */
public class HasCodeSmell {

	private String method_name;
	private String hasCodeSmell;
	private String method_id;
	private String package_name;
	private String class_name;
	private String isPositiveOrNegative;
	private boolean isMethod;
	
	

	/**
	 * Construtor para o objeto HasCodeSmell, caso o objeto seja um metodo são preenchidos os campos method_name e method_id
	 * caso seja uma classe, estes dois campos nao sao preenchidos
	 * 
	 * @param hasCodeSmell String que indica se foi, ou não, identificado um codeSmell
	 * @param PositiveOrNegative String que indica se a deteção foi positiva ou negativa
	 * @param method objeto que identifica um metodo e as suas metricas
	 * @param isMethod booleano que identifica se e, ou nao, um metodo
	 */
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
	
	/**
	 * Getter para atributo que identifica se é um método
	 * @return booleano que indica se e, ou nao, um metodo
	 */
	public boolean isMethod() {
		return isMethod;
	}

	/**
	 * Setter para booleano isMethod
	 */
	public void setMethod(boolean isMethod) {
		this.isMethod = isMethod;
	}

	/**
	 * getter 
	 * @return ID do metodo
	 */
	public String getMethod_ID() {
		return method_id;
	}

	/**
	 * getter 
	 * @return nome do metodo
	 */
	public String getMethodName() {
		return method_name;
	}

	/**
	 * getter
	 * @return classificação se existe ou não codesmell 
	 */
	public String getHasCodeSmell() {
		return hasCodeSmell;
	}
	
	/**
	 * getter
	 * @return nome da classe de um respetivo metodo
	 */
	public String getClassName() {
		return class_name;
	}
	
	/**
	 * getter
	 * @return package_name de um respetivo metodo
	 */
	public String getPackageName() {
		return package_name;
	}
	
	/**
	 * getter
	 * @return retorna a qualidade de classificação do code smell
	 */
	public String getQuality() {
		return isPositiveOrNegative;
	}
	
	/**
	 * setter
	 * @param qualidade de um método
	 */
	public void setQuality (String a)  {
		this.isPositiveOrNegative = a;
	}

	/**
	 * @param calculated metodo com code smell identificado consoante a regra
	 * @return booleano que indica se um metodo é igual a outro, compara nome do metodo, classe, package e parametros do mesmo
	 */
	public boolean isEqual(HasCodeSmell calculated) {
		return getMethodName().equals(calculated.getMethodName()) && getClassName().equals(calculated.getClassName())
				&& getPackageName().equals(calculated.getPackageName());
	}
	
	@Override
	public String toString() {
		return "HasCodeSmell [method_name=" + method_name + ", hasCodeSmell=" + hasCodeSmell + ", method_id="
				+ method_id + ", package_name=" + package_name + ", class_name=" + class_name
				+ ", isPositiveOrNegative=" + isPositiveOrNegative + ", isMethod=" + isMethod + "]";
	}
	
	

	
	
}
