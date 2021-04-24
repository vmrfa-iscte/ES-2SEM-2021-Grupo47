package G47.Grupo47;

import java.io.File;
import java.util.ArrayList;

public class CodeSmellsDetector {

	private int rule1_threshold,rule2_threshold,rule3_threshold;
	private String operator, operator2;
	private ArrayList<Metrics> results;
	private ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();
	private ArrayList<HasCodeSmell> auxview = new ArrayList<HasCodeSmell>();
	private String lastclass = "";
	private String lastclassview;
	private ArrayList<String> namesClasses = new ArrayList<>();
	private boolean hasDetection = false;
	private static final int ARRAY_FIRST_ELEMENT_INDEX = 0;
	private static final String POSITIVE_CLASS = "Classe: Verdadeiro", NEGATIVE_CLASS = "Classe: Verdadeiro";
	private static final String POSITIVE_METHOD = "TRUE", NEGATIVE_METHOD = "FALSE";


	
	
	// Construtor para o caso em que o utilizador decide limitar 2 métricas
	public CodeSmellsDetector (int rule1, int rule2, String operator, ArrayList<Metrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.operator = operator;
		this.results = results;
		this.lastclassview = results.get(ARRAY_FIRST_ELEMENT_INDEX).getClasse();
	}

	// Construtor para o caso em que o utilizador decide limitar 3 métricas
	public CodeSmellsDetector (int rule1, int rule2, int rule3, String operator, String operator2, ArrayList<Metrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.rule3_threshold = rule3;
		this.operator = operator;
		this.operator2 = operator2;
		this.results = results;
		this.lastclassview = results.get(ARRAY_FIRST_ELEMENT_INDEX).getClasse();
	}


	private void createAndAdd(ArrayList<HasCodeSmell> view, String lastclassview, String detection,String methodId,String classpackage,String className) {
		HasCodeSmell codesmell = new HasCodeSmell(lastclassview,detection,methodId,classpackage,className,null);
		view.add(codesmell);
		// Adicionar objeto HasCodeSmell com a qualidade de deteção já determinada a um array que compõe todos os resultados
	}

	private String verifyLastClass(String lastclassview, Metrics metric,ArrayList<String> namesClasses,ArrayList<HasCodeSmell> view,ArrayList<HasCodeSmell> auxview) {
		if(!lastclassview.equals(metric.getClasse())) {
			if(namesClasses.indexOf(lastclassview) != -1 ) {
				createAndAdd(view,lastclassview,POSITIVE_CLASS,null,null,null);
			}else {
				createAndAdd(view,lastclassview,NEGATIVE_CLASS,null,null,null);
			}
			lastclassview = metric.getClasse();
			view.addAll(auxview);
			auxview.removeAll(auxview);
		}
		return lastclassview;
	}

	private String lastVerification(String lastclassview,ArrayList<Metrics> results,Metrics metric, ArrayList<String> namesClasses,ArrayList<HasCodeSmell> view, ArrayList<HasCodeSmell> auxview) {
		if(results.indexOf(metric) == results.size()-1) {
			if(namesClasses.indexOf(metric.getClasse()) != -1 ) {
				HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),POSITIVE_CLASS,null,null,null,null);
				view.add(positiveClass);
			}else {
				HasCodeSmell negativeClass = new HasCodeSmell(metric.getClasse(),NEGATIVE_CLASS,null,null,null,null);
				view.add(negativeClass);

			}
			lastclassview = metric.getClasse();
			view.addAll(auxview);
			auxview.removeAll(auxview);
		}
		return lastclassview;
	}

	private String changeLastClass(String lastclass,Metrics metric) {
		if(!lastclass.equals(metric.getClasse())) {
			lastclass = metric.getClasse();
			namesClasses.add(metric.getClasse());
		}
		return lastclass;
	}


	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de maior.
	public ArrayList<HasCodeSmell> detectLongMethodBiggerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				// Caso em que o perador é AND
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) hasDetection = true;
			}else{
				// Caso em que o perador é OR
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Bigger para o LONG_METHOD");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectLongMethodBiggerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				// Caso em que o perador é AND
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o perador é OR
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário, faz-se set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para LongMethod");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	
	public ArrayList<HasCodeSmell> detectLongMethodSmallerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getLOC_method() < rule1_threshold && metric.getCYCLO_method() > rule2_threshold) hasDetection = true;
			}else  {
				// Caso em que o operador é OR
				if (metric.getLOC_method() < rule1_threshold || metric.getCYCLO_method() > rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário, faz-se set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri SmallerBigger para Long Method");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de menor para a primeira métrica e um de maior para a segunda. 
	public ArrayList<HasCodeSmell> detectLongMethodSmallerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getLOC_method() < rule1_threshold && metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}else  {
				// Caso em que o operador é OR
				if (metric.getLOC_method() < rule1_threshold || metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
				
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário, faz-se set no atributo a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = true;
		}
		System.out.println("Corri Smaller Smaller para Long Method");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites com o sinal de maior para ambas.
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() > rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() > rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Bigger para WMC_NOM");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}else  {
				if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para WMC_NOM");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário faz-se set do atributo a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Smaller para WMC_NOM");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// 	Caso em que o operador é AND
				if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() > rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário, faz-se set do atributo a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Bigger para WMC_NOM");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de maior para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é ANDA
				if (metric.getWMC_class() > rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getWMC_class() > rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário faz-se set do atriburo a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corru Bigger Bigger para WMC_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getWMC_class() > rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else  {
				// Caso em que o operador é OR
				if (metric.getWMC_class() > rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Se existe Code Smell, faz-se set no atributo hasCodeSmell a TRUE
							
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário faz-se set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para WMC_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getWMC_class() < rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getWMC_class() < rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Caso se detete o codeSmell, set do atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrário set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Smaller para WMC_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getWMC_class() < rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getWMC_class() < rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Caso exista codeSmell, set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso não exista, set a False
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Bigger para WMC_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de maior para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getNOM_class() > rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getNOM_class() > rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Caso exista codeSmell, set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso não exista, Set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Bigger para NOM_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getNOM_class() > rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getNOM_class() > rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Caso exista codeSmell, set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso contrario set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para NOM_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda.
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getNOM_class() < rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getNOM_class() < rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Caso exista codeSmell, set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso não exista, set a FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Bigger para NOM_LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				// Caso em que o operador é AND
				if (metric.getNOM_class() < rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else {
				// Caso em que o operador é OR
				if (metric.getNOM_class() < rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
				// Caso exista codeSmell, set no atributo hasCodeSmell a TRUE
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				// Caso não exista, set FALSE
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Smaller para NOM LOC");
		return view;
		// No fim é retornado um ArrayList com os resultados da aplicação da regra
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() > rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() > rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() > rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() > rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de menor para a segunda e um de menor para a teceira.
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() < rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() < rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() < rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() < rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);

			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de menor para a segunda e um de menor para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() < rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() < rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() < rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() < rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());	
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;

	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de menor para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() < rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() < rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() < rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() < rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}
			
			if (hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica,um de maior para a segunda e um de menor para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() > rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() > rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() > rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() > rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}
			
			if (hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() < rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() < rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() < rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() < rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}
			
			if (hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() > rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() > rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() > rule2_threshold && metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() > rule2_threshold || metric.getLOC_class() > rule3_threshold) hasDetection = true;
				}
			}
			if (hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class, NOM_Class, LOC_CLASS e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica,um de maior para a segunda e um de maior para a teceira. 
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() > rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() > rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}else {
				if(operator2.equals("AND")) {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() > rule2_threshold && metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}else {
					if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() > rule2_threshold || metric.getLOC_class() < rule3_threshold) hasDetection = true;
				}
			}
			if (hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),POSITIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());		
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),NEGATIVE_METHOD,String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}
	
	
	
}
