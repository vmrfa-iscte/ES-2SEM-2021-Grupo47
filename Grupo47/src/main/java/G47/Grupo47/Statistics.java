package G47.Grupo47;

import java.util.ArrayList;

public class Statistics {
	
	ArrayList<Metrics> metrics;
	private static final int ZERO = 0;
	
	// construtor
	// o objeto receve apenas um arrayList<Metrics> contendo as métricas calculadas previamente
	public Statistics (ArrayList<Metrics> metrics) {
		this.metrics = metrics;
		
	}
	//Tendo em conta a forma como as métricas estão organizadas, para saber o numero de métodos do projeto, basta saber o numero de elementos
	// do ArrayList que é passado no construtor
	public int countNumberOfMethods() {
		return metrics.size();
	}
	
	// Tendo em conta que os objeto metrics irá conter um atributo Lines of Code, representando as linhas de código do método, basta apenas somar
	// todas as lines of code dos objetos que constituem o ArrayList de Metrics
	public int countLinesOfCode() {
		int aux = ZERO;
		for (Metrics m : metrics) {
			aux = aux + m.getLOC_method();
		}
		return aux;
	}

	// Tendo em conta que os objetos metrics contêm um atributo String package, basta-nos contar todas as packages diferentes
	// para isto é utilizado um array auxiliar e a função contains
	// caso a package do objeto metrics já esteja contida neste array, não é adicionada, caso contrário é adicionada
	// no fim é retornado o tamanho do array list auxiliar
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
	
	
	// Tendo em conta que os objetos metrics contêm um atributo String classe, basta-nos contar todas as classes diferentes
	// para isto é utilizado um array auxiliar e a função contains
	// caso a classe do objeto metrics já esteja contida neste array, não é adicionada, caso contrário é adicionada
	// no fim é retornado o tamanho do array list auxiliar
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
