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

	//Os objetos poderão ser criados com 2 construtores diferentes. Por exemplo, caso o user insira a regra "WMC_class > 12 OR LOC_class > 12" e tendo
	// em conta que esta apenas limita dos métricas, é usado o construtor para duas métricas.
	// Caso o user insira, por exemplo, a regra "WMC_class > 12 OR LOC_class > 12 AND NOM_class < 12" e tendo em conta que esta limita três métricas,
	// é usado o construtor para três métricas.
	// Os atributos passados como argumento no construtor são portanto, os limites definidos, o operador escolhido e o ArrayList com métricas
	// que irá ser analisado.
	
	
	// Construtor para duas metricas
	public CodeSmellsDetector (int rule1, int rule2, String operator, ArrayList<Metrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.operator = operator;
		this.results = results;
		this.lastclassview = results.get(0).getClasse();
	}

	// Construtor para três métricas
	public CodeSmellsDetector (int rule1, int rule2, int rule3, String operator, String operator2, ArrayList<Metrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.rule3_threshold = rule3;
		this.operator = operator;
		this.operator2 = operator2;
		this.results = results;
		this.lastclassview = results.get(0).getClasse();
	}

	// Esta função tem como objetivo, criar um objeto HasCodeSmell, e adicioná-lo a um arrayList de resultados que eventualmente
	// seram integrados na SecondaryGUI.
	// De notar que quando o objeto é adicionado ao ArrayList, já lhe foi atribuida a classificação de Verdadeiro/Falso Positivo ou Negativo
	// Esta classificação é representada pelo atributo detection.
	private void createAndAdd(ArrayList<HasCodeSmell> view, String lastclassview, String detection,String methodId,String classpackage,String className) {
		HasCodeSmell codesmell = new HasCodeSmell(lastclassview,detection,methodId,classpackage,className,null);
		view.add(codesmell);
	}

	private String verifyLastClass(String lastclassview, Metrics metric,ArrayList<String> namesClasses,ArrayList<HasCodeSmell> view,ArrayList<HasCodeSmell> auxview) {
		if(!lastclassview.equals(metric.getClasse())) {
			if(namesClasses.indexOf(lastclassview) != -1 ) {
				createAndAdd(view,lastclassview,"Classe: Verdadeiro",null,null,null);
			}else {
				createAndAdd(view,lastclassview,"Classe: Falso",null,null,null);
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
				HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"Classe: Verdadeiro",null,null,null,null);
				view.add(positiveClass);
			}else {
				HasCodeSmell negativeClass = new HasCodeSmell(metric.getClasse(),"Classe: Falso",null,null,null,null);
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
	// das métricas com um sinal de maior. Por exemplo, quando insere uma regra do género "LOC_Method > 12 OR CYCLO_Method > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra

	public ArrayList<HasCodeSmell> detectLongMethodBiggerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) hasDetection = true;
			}else{
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Bigger para o LONG_METHOD");
		return view;
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de maior para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "LOC_Method > 12 OR CYCLO_Method < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectLongMethodBiggerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if(operator.equals("AND")) {
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}else {
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para LongMethod");
		return view;
	}

	
	public ArrayList<HasCodeSmell> detectLongMethodSmallerBigger() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getLOC_method() < rule1_threshold && metric.getCYCLO_method() > rule2_threshold) hasDetection = true;
			}else  {
				if (metric.getLOC_method() < rule1_threshold || metric.getCYCLO_method() > rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri SmallerBigger para Long Method");
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell is Long Method e para esta deteção define os limites
	// das métricas com um sinal de menor para a primeira métrica e um de maior para a segunda. Por exemplo, quando insere uma regra do género "LOC_Method < 12 OR CYCLO_Method > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectLongMethodSmallerSmaller() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getLOC_method() < rule1_threshold && metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}else  {
				if (metric.getLOC_method() < rule1_threshold || metric.getCYCLO_method() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = true;
		}
		System.out.println("Corri Smaller Smaller para Long Method");
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites com o sinal de maior para ambas.
	// Por exemplo, quando insere uma regra do género "WMC_Class > 12 OR NOM_Class > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() > rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() > rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Bigger para WMC_NOM");
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class > 12 OR NOM_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() > rule1_threshold && metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}else  {
				if (metric.getWMC_class() > rule1_threshold || metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para WMC_NOM");
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class < 12 OR NOM_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Smaller para WMC_NOM");
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e NOM_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class < 12 OR NOM_Class > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerWMC_NOM() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() < rule1_threshold && metric.getNOM_class() > rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() < rule1_threshold || metric.getNOM_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Bigger para WMC_NOM");
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de maior para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class > 12 OR LOC_Class > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() > rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() > rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corru Bigger Bigger para WMC_LOC");
		return view;
	}

	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class > 12 OR LOC_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() > rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else  {
				if (metric.getWMC_class() > rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para WMC_LOC");
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class < 12 OR LOC_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() < rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() < rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Smaller para WMC_LOC");
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas WMC_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. Por exemplo, quando insere uma regra do género "WMC_Class < 12 OR LOC_Class > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerWMC_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getWMC_class() < rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				if (metric.getWMC_class() < rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Bigger para WMC_LOC");
		return view;
	}
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de maior para a segunda. Por exemplo, quando insere uma regra do género "NOM_Class > 12 OR LOC_Class > 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getNOM_class() > rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				if (metric.getNOM_class() > rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Bigger para NOM_LOC");
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de maior para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "NOM_Class > 12 OR LOC_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getNOM_class() > rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else {
				if (metric.getNOM_class() > rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Bigger Smaller para NOM_LOC");
		return view;
	}
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de maior para a segunda. Por exemplo, quando insere uma regra do género "NOM_Class > 12 OR LOC_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getNOM_class() < rule1_threshold && metric.getLOC_class() > rule2_threshold) hasDetection = true;
			}else {
				if (metric.getNOM_class() < rule1_threshold || metric.getLOC_class() > rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Bigger para NOM_LOC");
		return view;
	}
	
	
	// Este método é invocado sempre que o utilizador pretende detetar o Code_Smell isGodClass,conjugando as
	// as métricas NOM_Class e LOC_Class, e para esta deteção define os limites 
	// com um sinal de menor para a primeira métrica e um de menor para a segunda. Por exemplo, quando insere uma regra do género "NOM_Class < 12 OR LOC_Class < 12".
	// De notar que o método está preparado para para operar, quer o sinal lógico seja OR ou AND.
	// O método vai portanto percorrer o ArrayList<HasCodeSmell> results passado como argumento no construtor e aplicar a regra definida pelo
	// utilizador a cada elemento deste ArrayList. Após aplicada a regra, o elemento é adicionado a um ArrayList auxiliar através da 
	// função createAndAdd, com a única diferença de que o seu atributo hasCodeSmell está preenchido com a classificação "TRUE" ou "FALSE"
	// derivada da aplicação da regra
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerNOM_LOC() {
		for (Metrics metric : results) {
			lastclassview = verifyLastClass(lastclassview,metric,namesClasses,view,auxview);
			if (operator.equals("AND")) {
				if (metric.getNOM_class() < rule1_threshold && metric.getLOC_class() < rule2_threshold) hasDetection = true;
			}else {
				if (metric.getNOM_class() < rule1_threshold || metric.getLOC_class() < rule2_threshold) hasDetection = true;

			}
			if(hasDetection) {
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		System.out.println("Corri Smaller Smaller para NOM LOC");
		return view;
	}


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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);

			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());	
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;

	}
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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}
			else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}

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
				createAndAdd(auxview,metric.getNome_metodo(),"TRUE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());		
				lastclass = changeLastClass(lastclass,metric);
			}else {
				createAndAdd(auxview,metric.getNome_metodo(),"FALSE",String.valueOf(metric.getMethod_ID()),metric.getPacote(),metric.getClasse());
			}
			lastclassview = lastVerification(lastclassview,results,metric,namesClasses,view,auxview);
			hasDetection = false;
		}
		return view;
	}
	
	
	
}
