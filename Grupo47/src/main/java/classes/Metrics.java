package classes;

/**
 * Objeto que agrupa todas as metricas extraidas
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
	 * Construtor
	 * @param LOC_method metrica LOC_method
	 * @param LOC_class metrica LOC_class
	 * @param CYCLO_method metrica CYCLO_method
	 * @param NOM_class metrica NOM_class
	 * @param WMC_class metrica WMC_class
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
	 * Getter para a metrica NOM_class
	 * @return metrica NOM_class
	 */
	public int getNOM_class() {
		return NOM_class;
	}

	/**
	 * Getter par a metrica LOC_method
	 * @return metrica LOC_method
	 */
	public int getLOC_method() {
		return LOC_method;
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
	 * Setter para a metrica LOC_method
	 * @param lOC_method metrica LOC_method
	 */
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
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
	 * @param NOM_class metrica NOM_class
	 */
	public void setNOM_class(int NOM_class) {
		this.NOM_class = NOM_class;
	}
	
}
