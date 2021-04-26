package G47.Grupo47;

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
	
	// Objeto que agrupa a identificação de um método e as suas métricas
	public MethodMetrics(MethodIdentity methodIdentity,Metrics extractedMetrics) {
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
	public String getClasse() {
		return className;
	}
	
	public int getNOM_class() {
		return NOM_class;
	}

	public int getLOC_method() {
		return LOC_method;
	}

	public String getPacote() {
		return packageName;
	}

	public String getNome_metodo() {
		return methodName;
	}
	
	public int getLOC_class() {
		return LOC_class;
	}
	
	public int getWMC_class() {
		return WMC_class;
	}
	
	public int getCYCLO_method() {
		return CYCLO_method;
	}
	
	public int getMethod_ID() {
		return method_id;
	}

	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}

	public void setPacote(String pacote) {
		this.packageName = pacote;
	}

	public void setClasse(String classe) {
		this.className = classe;
	}

	public void setNome_metodo(String nome_metodo) {
		this.methodName = nome_metodo;
	}

	public void setLOC_class(int lOC_class) {
		this.LOC_class = lOC_class;
	}

	public void setWMC_class(int wMC_class) {
		this.WMC_class = wMC_class;
	}

	public void setCYCLO_method(int cYCLO_method) {
		this.CYCLO_method = cYCLO_method;
	}
	
	public void setNOM_class(int NOM_class) {
		this.NOM_class = NOM_class;
	}
	
	public void setMethod_ID(int method_id) {
		this.method_id = method_id;
	}
	
	public String toString() {
		return packageName+"  ||  "+className+"  ||  "+methodName+"  ||  LOC_class: "+LOC_class+"  ||  LOC_method: "+LOC_method + "  ||  NOM_class: "+NOM_class+"  ||  WMC: "+WMC_class+"  ||  CYCLO: "+CYCLO_method;
		
	}
}
