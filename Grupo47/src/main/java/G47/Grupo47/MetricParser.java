package G47.Grupo47;


import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;

import java.io.FileNotFoundException;
import java.util.List;

public class MetricParser {
	
	public int getMethodComplexity(MethodDeclaration methodFromClass) {
		// Calcular complexidade ciclomática através da soma da ocorrência de diversos ciclos
		int complex = ExtractMetrics.COMPLEXITY_INITIAL_VALUE;
		int numbif = getCycloComplex(ExtractMetrics.IF_CYCLE, methodFromClass.toString());
		int numbwhile = getCycloComplex(ExtractMetrics.WHILE_CYCLE, methodFromClass.toString());
		int numbfor = getCycloComplex(ExtractMetrics.FOR_CYCLE, methodFromClass.toString());
		int numbelse = getCycloComplex(ExtractMetrics.ELSE, methodFromClass.toString());
		int numbcase = getCycloComplex(ExtractMetrics.CASE_CYCLE, methodFromClass.toString());
		int numbdefault = getCycloComplex(ExtractMetrics.DEFAULT, methodFromClass.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	}

	public int getCycloComplex(String wordToSearch, String data) {
		// Contador de ocorrência de uma palavra dada (wordToSearch) num dado texto (data)
		int count = ExtractMetrics.COUNTER_INITIAL_VALUE;
		for (int index = data.indexOf(wordToSearch); index != -1; index = data.indexOf(wordToSearch, index + 1)) {
			count++;
		}
		return count;
	}

	public int getConstructorComplexity(ConstructorDeclaration md) {
		// Calcular complexidade ciclomática através da soma da ocorrência de diversos ciclos
		int complex = ExtractMetrics.COMPLEXITY_INITIAL_VALUE;
		int numbif = getCycloComplex(ExtractMetrics.IF_CYCLE, md.toString());
		int numbwhile = getCycloComplex(ExtractMetrics.WHILE_CYCLE, md.toString());
		int numbfor = getCycloComplex(ExtractMetrics.FOR_CYCLE, md.toString());
		int numbelse = getCycloComplex(ExtractMetrics.ELSE, md.toString());
		int numbcase = getCycloComplex(ExtractMetrics.CASE_CYCLE, md.toString());
		int numbdefault = getCycloComplex(ExtractMetrics.DEFAULT, md.toString());
		return complex + numbif + numbwhile + numbfor + numbelse + numbcase + numbdefault;
	}

	public int getClassComplexity(List<MethodDeclaration> methodList, List<ConstructorDeclaration> constructorList) {
		// Cálculo da complexidade ciclomática através da soma da complexidade dos métodos e construtores nela existentes
		int complexity = ExtractMetrics.COUNTER_INITIAL_VALUE;
		for (MethodDeclaration currentMethod : methodList)
			complexity += getMethodComplexity(currentMethod);
		for (ConstructorDeclaration currentConstructor : constructorList)
			complexity += getConstructorComplexity(currentConstructor);
		return complexity;
	}
	
	public int getLOC_methodConstructor(ConstructorDeclaration constructorFromParsedClass) {
		// Calcular número de linhas de um construtor, subtração entre a última linha e a primeira linha
		return (constructorFromParsedClass.getEnd().get().line - constructorFromParsedClass.getBegin().get().line)+1;
	}
	
	public int getLOC_methodMethod(MethodDeclaration methodFromParsedClass) {
		// Calcular número de linhas de um método, subtração entre a última linha e a primeira linha
		return (methodFromParsedClass.getEnd().get().line - methodFromParsedClass.getBegin().get().line)+1;
	}
	
	public int getNOM_class(List<MethodDeclaration> allMethodsFromParsedClass, List<ConstructorDeclaration> allConstructorsFromParsedClass) {
		// Obter o número de métodos consiste em contar o número de métodos e número de construtores que existem na classe
		return allMethodsFromParsedClass.size()+ allConstructorsFromParsedClass.size();
	}
	
	public int getLOC_classFromClass(ClassOrInterfaceDeclaration cid) throws FileNotFoundException {
		// Calcular número de linhas de código fazendo uma subtração entre a última linha da classe e primeira linha
		return (cid.getEnd().get().line - cid.getBegin().get().line)+ 1;
	}
	
	public int getLOC_classFromEnum(EnumDeclaration ed) {
		// Calcular número de linhas de código fazendo uma subtração entre a última linha da classe e primeira linha
		return (ed.getEnd().get().line - ed.getBegin().get().line)+ 1;
	}
}