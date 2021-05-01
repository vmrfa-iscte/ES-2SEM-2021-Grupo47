package classes;

/**
 * Objeto que agrupa a identidade de um metodo e as metricas extraidas para esse mesmo metodo
 * @author Vasco Fontoura
 * @version 2
 *
 */
public class MethodMetrics {
	
	private int LOC_method;
	private String packageName;
	private String className;
	private String methodName;
	private int LOC_class;
	private int WMC_class;
	private int CYCLO_method;
	private int NOM_class;
	private int method_id;
	

	/**
	 * Construtor
	 * @param methodIdentity identidade do metodo
	 * @param extractedMetrics metricas do metodo
	 */
	public MethodMetrics(MethodIdentity methodIdentity,Metrics extractedMetrics) {
		// Objeto que agrupa a identificação de um método e as suas métricas
		this.methodName = methodIdentity.getMethodName();
		this.className = methodIdentity.getClassName();
		this.packageName = methodIdentity.getPackageName();
		if(extractedMetrics != null) {
			this.LOC_method = extractedMetrics.getLOC_method();
			this.LOC_class = extractedMetrics.getLOC_class();
			this.CYCLO_method = extractedMetrics.getCYCLO_method();
			this.NOM_class = extractedMetrics.getNOM_class();
			this.WMC_class = extractedMetrics.getWMC_class();
		}
		this.method_id = methodIdentity.getMethod_id();
	}

	// Getters
	/**
	 * Getter para o nome da classe
	 * @return nome da classe
	 */
	public String getClasse() {
		return className;
	}
	
	/**
	 * Getter para a metrica NOM_class
	 * @return metrica NOM_class
	 */
	public int getNOM_class() {
		return NOM_class;
	}

	/**
	 * Getter para a metrica LOC_method
	 * @return metrica LOC_method
	 */
	public int getLOC_method() {
		return LOC_method;
	}

	/**
	 * Getter para o nome do pacote do metodo
	 * @return nome do pacote
	 */
	public String getPacote() {
		return packageName;
	}

	/**
	 * Getter para o nome do metodo
	 * @return nome do metodo
	 */
	public String getNome_metodo() {
		return methodName;
	}
	
	/**
	 * Getter para a metrica LOC_class
	 * @return metrica LOC_class
	 */
	public int getLOC_class() {
		return LOC_class;
	}
	
	/**
	 * Getter para a metrica WMC_class
	 * @return metrica WMC_class
	 */
	public int getWMC_class() {
		return WMC_class;
	}
	
	/**
	 * Getter para a metrica CYCLO_method
	 * @return metrica CYCLO_method
	 */
	public int getCYCLO_method() {
		return CYCLO_method;
	}
	
	/**
	 * Getter para o id do metodo
	 * @return id do metodo
	 */
	public int getMethod_ID() {
		return method_id;
	}

	/**
	 * Setter para a metrica LOC_method
	 * @param lOC_method metrica LOC_method
	 */
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}

	/**
	 * Setter para o nome do pacote
	 * @param pacote nome do pacote
	 */
	public void setPacote(String pacote) {
		this.packageName = pacote;
	}

	/**
	 * Setter para o nome da classe do metodo
	 * @param classe nome da classe
	 */
	public void setClasse(String classe) {
		this.className = classe;
	}

	/**
	 * Setter para o nome do metodo
	 * @param nome_metodo nome do método
	 */
	public void setNome_metodo(String nome_metodo) {
		this.methodName = nome_metodo;
	}

	/**
	 * Setter para a metrica LOC_class
	 * @param lOC_class metrica LOC_class
	 */
	public void setLOC_class(int lOC_class) {
		this.LOC_class = lOC_class;
	}

	/**
	 * Setter para a metrica WMC_class
	 * @param wMC_class metrica WMC_class
	 */
	public void setWMC_class(int wMC_class) {
		this.WMC_class = wMC_class;
	}

	/**
	 * Setter para a metrica CYCLO_method
	 * @param cYCLO_method metrica CYCLO_method
	 */
	public void setCYCLO_method(int cYCLO_method) {
		this.CYCLO_method = cYCLO_method;
	}
	
	/**
	 * Setter para a metrica NOM_class
	 * @param NOM_class métrica NOM_class
	 */
	public void setNOM_class(int NOM_class) {
		this.NOM_class = NOM_class;
	}
	
	/**
	 * Setter para o id do metodo
	 * @param method_id id do metodo
	 */
	public void setMethod_ID(int method_id) {
		this.method_id = method_id;
	}
	
	/**
	 * Metodo toString() para visualizar os conteudos do objeto
	 */
	public String toString() {
		return packageName+"  ||  "+className+"  ||  "+methodName+"  ||  LOC_class: "+LOC_class+"  ||  LOC_method: "+LOC_method + "  ||  NOM_class: "+NOM_class+"  ||  WMC: "+WMC_class+"  ||  CYCLO: "+CYCLO_method;
		
	}
}
