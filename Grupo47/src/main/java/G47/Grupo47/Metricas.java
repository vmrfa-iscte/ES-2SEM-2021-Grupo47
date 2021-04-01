package G47.Grupo47;

public class Metricas {
	
	private int LOC_method;
	private String pacote;
	private String classe;
	private String nome_metodo;
	private int LOC_class;
	private int WMC_class;
	private int CYCLO_method;
	private int NOM_class;
	
	public Metricas(String nome_metodo,String classe,String pacote,int LOC_method) {
		this.nome_metodo = nome_metodo;
		this.pacote = pacote;
		this.classe = classe;
		this.LOC_method = LOC_method;
	}
	
	public Metricas(String classe, String pacote) {
		this.classe = classe;
		this.pacote = pacote;
	}
	
	
	public String getClasse() {
		return classe;
	}

	public int getLOC_method() {
		return LOC_method;
	}

	public String getPacote() {
		return pacote;
	}

	public String getNome_metodo() {
		return nome_metodo;
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
		this.pacote = pacote;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public void setNome_metodo(String nome_metodo) {
		this.nome_metodo = nome_metodo;
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
		return pacote+"  ||  "+classe+"  ||  "+nome_metodo+"  ||  LOC_class: "+LOC_class+"  ||  LOC_method: "+LOC_method + "  ||  NOM_class: "+NOM_class+"  ||  WMC: "+WMC_class+"  ||  CYCLO: "+CYCLO_method;
		
	}
	
	

}
