package classes;

/**
 * @author Vasco Fontoura
 *
 */
public class Metrics {
	
	private int LOC_method;
	private int LOC_class;
	private int WMC_class;
	private int CYCLO_method;
	private int NOM_class;
	
	
	/**
	 * @param métrica LOC_method
	 * @param métrica LOC_class
	 * @param métrica CYCLO_method
	 * @param métrica NOM_class
	 * @param métrica WMC_class
	 */
	public Metrics(int LOC_method,int LOC_class, int CYCLO_method,int NOM_class,int WMC_class) {
		// Objeto que agrupa todas as métricas
		this.LOC_method = LOC_method;
		this.LOC_class = LOC_class;
		this.CYCLO_method = CYCLO_method;
		this.NOM_class = NOM_class;
		this.WMC_class = WMC_class;
	}
	
	// Getters e setters
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
	 * @param métrica LOC_method
	 */
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}

	/**
	 * @param métrica LOC_class
	 */
	public void setLOC_class(int lOC_class) {
		this.LOC_class = lOC_class;
	}

	/**
	 * @param métrica WMC_class
	 */
	public void setWMC_class(int wMC_class) {
		this.WMC_class = wMC_class;
	}

	/**
	 * @param métrica CYCLO_method
	 */
	public void setCYCLO_method(int cYCLO_method) {
		this.CYCLO_method = cYCLO_method;
	}
	
	/**
	 * @param métrica NOM_class
	 */
	public void setNOM_class(int NOM_class) {
		this.NOM_class = NOM_class;
	}
	
}
