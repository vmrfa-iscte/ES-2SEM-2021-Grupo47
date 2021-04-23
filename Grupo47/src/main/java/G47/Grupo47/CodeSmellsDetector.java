package G47.Grupo47;

import java.io.File;
import java.util.ArrayList;

public class CodeSmellsDetector {

	private int rule1_threshold,rule2_threshold,rule3_threshold;
	private String operator, operator2;
	private ArrayList<MethodMetrics> results;
	private ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();
	private ArrayList<HasCodeSmell> auxview = new ArrayList<HasCodeSmell>();
	private String lastclass = "";
	private String lastclassview;
	private ArrayList<String> namesClasses = new ArrayList<>();
	private boolean hasDetection = false;


	// two metrics
	public CodeSmellsDetector (int rule1, int rule2, String operator, ArrayList<MethodMetrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.operator = operator;
		this.results = results;
		this.lastclassview = results.get(0).getClasse();
	}

	//three metrics
	public CodeSmellsDetector (int rule1, int rule2, int rule3, String operator, String operator2, ArrayList<MethodMetrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.rule3_threshold = rule3;
		this.operator = operator;
		this.operator2 = operator2;
		this.results = results;
		this.lastclassview = results.get(0).getClasse();
	}

	private void createAndAdd(ArrayList<HasCodeSmell> view, String lastclassview, String detection,String methodId,String classpackage,String className) {
		HasCodeSmell codesmell = new HasCodeSmell(lastclassview,detection,methodId,classpackage,className,null);
		view.add(codesmell);
	}

	private String verifyLastClass(String lastclassview, MethodMetrics metric,ArrayList<String> namesClasses,ArrayList<HasCodeSmell> view,ArrayList<HasCodeSmell> auxview) {
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

	private String lastVerification(String lastclassview,ArrayList<MethodMetrics> results,MethodMetrics metric, ArrayList<String> namesClasses,ArrayList<HasCodeSmell> view, ArrayList<HasCodeSmell> auxview) {
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

	private String changeLastClass(String lastclass,MethodMetrics metric) {
		if(!lastclass.equals(metric.getClasse())) {
			lastclass = metric.getClasse();
			namesClasses.add(metric.getClasse());
		}
		return lastclass;
	}
	
	public ArrayList<HasCodeSmell> detectLongMethodBiggerBigger() {
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectLongMethodBiggerSmaller() {
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectLongMethodSmallerSmaller() {
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerWMC_NOM() {
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_NOM() {
		for (MethodMetrics metric : results) {
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
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerWMC_NOM() {
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerWMC_NOM() {
		for (MethodMetrics metric : results) {
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
	
	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerWMC_LOC() {
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerWMC_LOC() {
		for (MethodMetrics metric : results) {
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
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerWMC_LOC() {
		for (MethodMetrics metric : results) {
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
	
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerWMC_LOC() {
		for (MethodMetrics metric : results) {
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

	public ArrayList<HasCodeSmell> detectGodClassBiggerBiggerNOM_LOC() {
		for (MethodMetrics metric : results) {
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
	
	public ArrayList<HasCodeSmell> detectGodClassBiggerSmallerNOM_LOC() {
		for (MethodMetrics metric : results) {
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
	public ArrayList<HasCodeSmell> detectGodClassSmallerBiggerNOM_LOC() {
		for (MethodMetrics metric : results) {
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
	public ArrayList<HasCodeSmell> detectGodClassSmallerSmallerNOM_LOC() {
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
		for (MethodMetrics metric : results) {
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
