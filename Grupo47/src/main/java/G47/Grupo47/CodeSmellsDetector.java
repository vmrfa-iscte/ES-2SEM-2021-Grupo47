package G47.Grupo47;

import java.io.File;
import java.util.ArrayList;

public class CodeSmellsDetector {
	
	private int rule1_threshold,rule2_threshold;
	private String operator;
	private ArrayList<Metrics> results;

	public CodeSmellsDetector (int rule1, int rule2, String operator, ArrayList<Metrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.operator = operator;
		this.results = results;

	}

	public ArrayList<HasCodeSmell> detectLongMethod() {
		ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();
		ArrayList<HasCodeSmell> auxview = new ArrayList<HasCodeSmell>();
		String lastclass = "";
		String lastclassview = results.get(0).getClasse();
		ArrayList<String> namesClasses = new ArrayList<>();
		if (operator.equals("AND")) {
			for (Metrics metric : results) {
				if(!lastclassview.equals(metric.getClasse())) {
					if(namesClasses.indexOf(lastclassview) != -1 ) {
						HasCodeSmell positiveClass = new HasCodeSmell(lastclassview,"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(lastclassview,"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
				
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro",String.valueOf(metric.getMethod_ID()));
					auxview.add(positive);
					
					if(!lastclass.equals(metric.getClasse())) {
						lastclass = metric.getClasse();
						namesClasses.add(metric.getClasse());
					}
						
				}else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso",String.valueOf(metric.getMethod_ID()));
					auxview.add(negative);
				}
				if(results.indexOf(metric) == results.size()-1) {
					if(namesClasses.indexOf(metric.getClasse()) != -1 ) {
						HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(metric.getClasse(),"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
			}
			
			
		}

		if (operator.equals("OR")) {
			for (Metrics metric : results) {
				if(!lastclassview.equals(metric.getClasse())) {
					if(namesClasses.indexOf(lastclassview) != -1) {
						HasCodeSmell positiveClass = new HasCodeSmell(lastclassview,"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(lastclassview,"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
				
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro",String.valueOf(metric.getMethod_ID()));
					auxview.add(positive);
					if(!lastclass.equals(metric.getClasse())) {	
						lastclass = metric.getClasse();			
						namesClasses.add(metric.getClasse());
					}
					
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso",String.valueOf(metric.getMethod_ID()));
					auxview.add(negative);
				}
				if(results.indexOf(metric) == results.size()-1) {
					if(namesClasses.indexOf(metric.getClasse()) != -1 ) {
						HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(metric.getClasse(),"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
			}
		}
		return view;
	}

	public ArrayList<HasCodeSmell> detectGodClass() {
		ArrayList<String> namesClasses = new ArrayList<>();
		ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();
		ArrayList<HasCodeSmell> auxview = new ArrayList<HasCodeSmell>();
		String lastclass = "";
		String lastclassview = results.get(0).getClasse();

		if (operator.equals("AND")) {
			for (Metrics metric : results) {
				if(!lastclassview.equals(metric.getClasse())) {
					if(namesClasses.indexOf(lastclassview) != -1) {
						HasCodeSmell positiveClass = new HasCodeSmell(lastclassview,"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(lastclassview,"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
			
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro",String.valueOf(metric.getMethod_ID()));
					if(!lastclass.equals(metric.getClasse()) ) {
						lastclass = metric.getClasse();
						namesClasses.add(metric.getClasse());
					}
	
					auxview.add(positive);
					
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso",String.valueOf(metric.getMethod_ID()));
					auxview.add(negative);

				}
				if(results.indexOf(metric) == results.size()-1) {
					if(namesClasses.indexOf(metric.getClasse()) != -1 ) {
						HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(metric.getClasse(),"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
				

				
			}
		}

		if (operator.equals("OR")) {
			for (Metrics metric : results) {
				if(!lastclassview.equals(metric.getClasse())) {
					if(namesClasses.indexOf(lastclassview) != -1) {
						HasCodeSmell positiveClass = new HasCodeSmell(lastclassview,"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(lastclassview,"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro",String.valueOf(metric.getMethod_ID()));
					if(!lastclass.equals(metric.getClasse()) ) {
					
						lastclass = metric.getClasse();
						namesClasses.add(metric.getClasse());
					}
					auxview.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso",String.valueOf(metric.getMethod_ID()));
					auxview.add(negative);

				}
				if(results.indexOf(metric) == results.size()-1) {
					if(namesClasses.indexOf(metric.getClasse()) != -1 ) {
						HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"Classe: Verdadeiro","");
						view.add(positiveClass);
					}else {
						HasCodeSmell negativeClass = new HasCodeSmell(metric.getClasse(),"Classe: Falso","");
						view.add(negativeClass);
						
					}
					lastclassview = metric.getClasse();
					view.addAll(auxview);
					auxview.removeAll(auxview);
				}
			}
		}

		return view;

	}

}
