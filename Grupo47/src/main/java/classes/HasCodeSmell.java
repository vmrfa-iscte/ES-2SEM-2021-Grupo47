package classes;

/**
 * Classe utilizada para identificar um método de um dado projeto, assim como a classificação atribuida consoante determinada regra
 * @author Tomás Mendes
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
	 * Construtor
	 * @param hasCodeSmell
	 * @param PositiveOrNegative
	 * @param method
	 * @param isMethod
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
	 * @return booleano isMethod
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
	 * @return package de um respetivo metodo
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
	 * @param calculated, metodo com code smell identificado consoante a regra
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
