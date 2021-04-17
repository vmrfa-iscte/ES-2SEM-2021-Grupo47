package G47.Grupo47;

import java.util.ArrayList;

public class Statistics {
	
	ArrayList<Metrics> metrics;
	
	public Statistics (ArrayList<Metrics> metrics) {
		this.metrics = metrics;
		
	}
	
	public int countNumberOfMethods() {
		return metrics.size();
	}

	public int countLinesOfCode() {
		int aux = 0;
		for (Metrics m : metrics) {
			aux = aux + m.getLOC_method();
		}
		return aux;
	}

	public int countPackages() {
		ArrayList<String> aux = new ArrayList<String>();
		for (Metrics m : metrics) {
			if (aux.contains(m.getPacote())) {
				
			}
			else {
				aux.add(m.getPacote());
			}
			
		}
		return aux.size();
	}
	
	public int countClasses() {
		ArrayList<String> aux = new ArrayList<String>();
		for (Metrics m : metrics) {
			if (aux.contains(m.getClasse())) {
				
			}
			else {
				aux.add(m.getClasse());
			}
			
		}
		return aux.size();
	}
	

}
