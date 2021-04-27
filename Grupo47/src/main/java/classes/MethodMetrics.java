package classes;

/**
 * @author Vasco Fontoura
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
	 * @param methodIdentity identidade do método
	 * @param extractedMetrics métricas do método
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
	 * @return nome da classe
	 */
	public String getClasse() {
		return className;
	}
	
	/**
	 * @return métrica NOM_class
	 */
	public int getNOM_class() {
		return NOM_class;
	}

	/**
	 * @return métrica LOC_method
	 */
	public int getLOC_method() {
		return LOC_method;
	}

	/**
	 * @return nome do pacote
	 */
	public String getPacote() {
		return packageName;
	}

	/**
	 * @return nome do método
	 */
	public String getNome_metodo() {
		return methodName;
	}
	
	/**
	 * @return métrica LOC_class
	 */
	public int getLOC_class() {
		return LOC_class;
	}
	
	/**
	 * @return métrica WMC_class
	 */
	public int getWMC_class() {
		return WMC_class;
	}
	
	/**
	 * @return métrica CYCLO_method
	 */
	public int getCYCLO_method() {
		return CYCLO_method;
	}
	
	/**
	 * @return id do método
	 */
	public int getMethod_ID() {
		return method_id;
	}

	/**
	 * @param lOC_method métrica LOC_method
	 */
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}

	/**
	 * @param pacote nome do pacote
	 */
	public void setPacote(String pacote) {
		this.packageName = pacote;
	}

	/**
	 * @param classe nome da classe
	 */
	public void setClasse(String classe) {
		this.className = classe;
	}

	/**
	 * @param nome_metodo nome do método
	 */
	public void setNome_metodo(String nome_metodo) {
		this.methodName = nome_metodo;
	}

	/**
	 * @param lOC_class métrica LOC_class
	 */
	public void setLOC_class(int lOC_class) {
		this.LOC_class = lOC_class;
	}

	/**
	 * @param wMC_class métrica WMC_class
	 */
	public void setWMC_class(int wMC_class) {
		this.WMC_class = wMC_class;
	}

	/**
	 * @param cYCLO_method métrica CYCLO_method
	 */
	public void setCYCLO_method(int cYCLO_method) {
		this.CYCLO_method = cYCLO_method;
	}
	
	/**
	 * @param NOM_class métrica NOM_class
	 */
	public void setNOM_class(int NOM_class) {
		this.NOM_class = NOM_class;
	}
	
	/**
	 * @param method_id id do método
	 */
	public void setMethod_ID(int method_id) {
		this.method_id = method_id;
	}
	
	public String toString() {
		return packageName+"  ||  "+className+"  ||  "+methodName+"  ||  LOC_class: "+LOC_class+"  ||  LOC_method: "+LOC_method + "  ||  NOM_class: "+NOM_class+"  ||  WMC: "+WMC_class+"  ||  CYCLO: "+CYCLO_method;
		
	}
}
