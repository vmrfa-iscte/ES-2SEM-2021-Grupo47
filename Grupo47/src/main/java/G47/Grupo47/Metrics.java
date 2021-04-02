package G47.Grupo47;

public class Metrics {
	
	private int LOC_method;
	private String packageName;
	private String className;
	private String methodName;
	private int LOC_class;
	private int WMC_class;
	private int CYCLO_method;
	private int NOM_class;
	
	public Metrics(String methodName,String className,String packageName,int LOC_method) {
		this.methodName = methodName;
		this.packageName = packageName;
		this.className = className;
		this.LOC_method = LOC_method;
	}
	
	public Metrics(String classeName, String packageName) {
		this.className = classeName;
		this.packageName = packageName;
	}
	
	public Metrics(String methodName,String className,String packageName, int LOC_method,int LOC_class, int CYCLO_method,int NOM_class,int WMC_class) {
		this.methodName = methodName;
		this.className = className;
		this.packageName = packageName;
		this.LOC_method = LOC_method;
		this.LOC_class = LOC_class;
		this.CYCLO_method = CYCLO_method;
		this.NOM_class = NOM_class;
		this.WMC_class = WMC_class;
	}
	
	
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
	
	public String toString() {
		return packageName+"  ||  "+className+"  ||  "+methodName+"  ||  LOC_class: "+LOC_class+"  ||  LOC_method: "+LOC_method + "  ||  NOM_class: "+NOM_class+"  ||  WMC: "+WMC_class+"  ||  CYCLO: "+CYCLO_method;
		
	}
	
	

}
