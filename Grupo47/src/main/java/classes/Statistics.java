package classes;

import java.util.ArrayList;

/**
 * Classe utilizada para calcular as caracteristicas de um projeto, nomeadamente, numero total de linhas de codigo,
 * numero total de classes, numero total de packages e numero total de metodos
 * @author Tomás Mendes
 * @version
 *
 *
 */
public class Statistics {
	
	ArrayList<MethodMetrics> metrics;
	private static final int ZERO = 0;
	

	/**
	 * construtor
	 * @param array com metricas calculadas para um determinado projeto
	 */
	public Statistics (ArrayList<MethodMetrics> metrics) {
		this.metrics = metrics;
		
	}
	
	/**
	 * Conta o numero de metodos de um projeto
	 * @return numero de metodos de um determinado projeto
	 */
	public int countNumberOfMethods() {
		return metrics.size();
		// O numero de metodos será o numero de elementos no Array
	}
	
	
	/**
	 * conta numero de linhas de codigo de um determinado projeto
	 * @return numero de linhas de codigo de um determinado projeto
	 */
	public int countLinesOfCode() {
		int aux = ZERO;
		for (MethodMetrics m : metrics) {
			aux = aux + m.getLOC_method();
		}
		return aux;
		// O numero total de linha de codigo do projeto é a soma de todas as linhas de codigo de cada metodo
	}


	/**
	 * Conta numero de packages de um determinado projeto
	 * @return numero de packages de um determinado projeto
	 */
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
	
	

	/**
	 * conta numero de classes de um determinado projeto
	 * @return numero de classes de um determinado projeto
	 */
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
