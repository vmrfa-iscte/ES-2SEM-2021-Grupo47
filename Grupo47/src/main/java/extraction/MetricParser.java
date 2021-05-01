package extraction;


import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Tomas Mendes
 * @author Vasco Fontoura
 *
 */
public class MetricParser {
	private static final int COUNTER_INITIAL_VALUE = 0, COMPLEXITY_INITIAL_VALUE = 1; 
	private static final String IF_CYCLE = "if",
			FOR_CYCLE = "for", WHILE_CYCLE = "while", CASE_CYCLE = "case", ELSE = "else", DEFAULT_CYCLE = "default";
	
	/** Calcular complexidade ciclomática através da soma da ocorrência de diversos ciclos
	 * @param methodFromClass objeto que cujo toString corresponde ao metodo que queremos analisar
	 * @return soma do numero total de ocorrencias de determinadas palavras chave mais 1, o valor default da complexidade de um metodo
	 */
	public int getMethodComplexity(MethodDeclaration methodFromClass) {
		int complex = COMPLEXITY_INITIAL_VALUE;
		int numbif = getCycloComplex(IF_CYCLE, methodFromClass.toString());
		int numbwhile = getCycloComplex(WHILE_CYCLE, methodFromClass.toString());
		int numbfor = getCycloComplex(FOR_CYCLE, methodFromClass.toString());
		int numbelse = getCycloComplex(ELSE, methodFromClass.toString());
		int numbcase = getCycloComplex(CASE_CYCLE, methodFromClass.toString());
		int numbdefault = getCycloComplex(DEFAULT_CYCLE, methodFromClass.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	}

	/**
	 * 	Contador de ocorrência de uma palavra dada (wordToSearch) num dado texto (data)
	 * @param wordToSearch palavra a procurar numa determinada string
	 * @param data String de onde vai ser procurada a palavra referida anteriormente
	 * @return numero de vezes que se contou a string wordTosearch na String data
	 */
	public int getCycloComplex(String wordToSearch, String data) {
		int count = COUNTER_INITIAL_VALUE;
		for (int index = data.indexOf(wordToSearch); index != -1; index = data.indexOf(wordToSearch, index + 1)) {
			count++;
		}
		return count;
	}

	/**
	 * Calcular complexidade ciclomática através da soma da ocorrência de diversos ciclos
	 * @param md String que contem um determinado construtor
	 * @return soma do numero total de ocorrencias de determinadas palavras chave mais 1, o valor default da complexidade de um metodo
	 */
	public int getConstructorComplexity(ConstructorDeclaration md) {

		int complex = COMPLEXITY_INITIAL_VALUE;
		int numbif = getCycloComplex(IF_CYCLE, md.toString());
		int numbwhile = getCycloComplex(WHILE_CYCLE, md.toString());
		int numbfor = getCycloComplex(FOR_CYCLE, md.toString());
		int numbelse = getCycloComplex(ELSE, md.toString());
		int numbcase = getCycloComplex(CASE_CYCLE, md.toString());
		int numbdefault = getCycloComplex(DEFAULT_CYCLE, md.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	}

	/**
	 * Calculo da complexidade ciclomatica através da soma da complexidade dos metodos e construtores nela existentes
	 * @param methodList Lista que contem metodos
	 * @param constructorList Lista que contem construtores
	 * @return complexidade ciclomatica de um determinado construtor somado da complexidade ciclomatica de um dado metodo
	 */
	public int getClassComplexity(List<MethodDeclaration> methodList, List<ConstructorDeclaration> constructorList) {
		int complexity = COUNTER_INITIAL_VALUE;
		for (MethodDeclaration currentMethod : methodList)
			complexity += getMethodComplexity(currentMethod);
		for (ConstructorDeclaration currentConstructor : constructorList)
			complexity += getConstructorComplexity(currentConstructor);
		return complexity;
	}
	
	/**
	 * Calcular numero de linhas de um construtor, subtraçao entre a ultima linha e a primeira linha
	 * @param constructorFromParsedClass construtor de uma determinada classe
	 * @return numero de linhas do construtor
	 */
	public int getLOC_methodConstructor(ConstructorDeclaration constructorFromParsedClass) {
		return (constructorFromParsedClass.getEnd().get().line - constructorFromParsedClass.getBegin().get().line)+1;
	}
	
	/**
	 * Calcular numero de linhas de um metodo, subtraçao entre a ultima linha e a primeira linha
	 * @param methodFromParsedClass metodo de uma determinada classe
	 * @return numero de linhas do metodo
	 */
	public int getLOC_methodMethod(MethodDeclaration methodFromParsedClass) {
		return (methodFromParsedClass.getEnd().get().line - methodFromParsedClass.getBegin().get().line)+1;
	}
	
	
	/**
	 * 	Obter o numero de metodos consiste em contar o numero de métodos e numero de construtores que existem na classe
	 * @param allMethodsFromParsedClass Lista que contem todos os metodos da classe
	 * @param allConstructorsFromParsedClass Lista que contem todos os construtores da classe
	 * @return
	 */
	public int getNOM_class(List<MethodDeclaration> allMethodsFromParsedClass, List<ConstructorDeclaration> allConstructorsFromParsedClass) {
		return allMethodsFromParsedClass.size()+ allConstructorsFromParsedClass.size();
	}
	
	/**
	 * Calcular numero de linhas de codigo fazendo uma subtração entre a ultima linha da classe e primeira linha
	 * @param cid declaracao de uma determinada classe ou interface
	 * @return numero de linhas da classe ou interface
	 * @throws FileNotFoundException exceccao
	 */
	public int getLOC_classFromClass(ClassOrInterfaceDeclaration cid) throws FileNotFoundException {
		return (cid.getEnd().get().line - cid.getBegin().get().line)+ 1;
	}
	
	/**
	 * Calcular numero de linhas de codigo fazendo uma subtração entre a ultima linha da classe e primeira linha
	 * @param ed declaracao de um determinado enumerado 
	 * @return numero de linhas do enumerado
	 */
	public int getLOC_classFromEnum(EnumDeclaration ed) {
		return (ed.getEnd().get().line - ed.getBegin().get().line)+ 1;
	}
}