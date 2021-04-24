package G47.Grupo47;

import java.util.ArrayList;

public class Statistics {
	
	ArrayList<MethodMetrics> metrics;
	private static final int ZERO = 0;
	
	// construtor
	// o objeto receve apenas um arrayList<Metrics> contendo as métricas calculadas previamente
	public Statistics (ArrayList<MethodMetrics> metrics) {
		this.metrics = metrics;
		
	}
	
	public int countNumberOfMethods() {
		return metrics.size();
		// O numero de metodos será o numero de elementos no Array
	}
	
	
	public int countLinesOfCode() {
		int aux = ZERO;
		for (MethodMetrics m : metrics) {
			aux = aux + m.getLOC_method();
		}
		return aux;
		// O numero total de linha de codigo do projeto é a soma de todas as linhas de codigo de cada metodo
	}


	public int countPackages() {
		ArrayList<String> aux = new ArrayList<String>();
		// Criação de Array auxiliar
		for (MethodMetrics m : metrics) {
			if (aux.contains(m.getPacote()));//Caso a String com o pacote já esteja contida no Array aux, não adiciona 
			else aux.add(m.getPacote());
				//Caso a String com o pacote não esteja contida no Array aux,  adiciona-a
		}
		return aux.size();
		// Retorna o tamanho do array List após percorrer todos os métodos do Array metrics 
	}
	
	

	public int countClasses() {
		ArrayList<String> aux = new ArrayList<String>();
		// Criação de Array auxiliar
		for (MethodMetrics m : metrics) {
			if (aux.contains(m.getClasse())); //Caso a String com a classe já esteja contida no Array aux, não adiciona	
			else  aux.add(m.getClasse()); //Caso a String com a classe não esteja contida no Array aux,  adiciona-a
		}
		return aux.size();
		// Retorna o tamanho do array List após percorrer todos os métodos do Array metrics 
			}
	
	

}
