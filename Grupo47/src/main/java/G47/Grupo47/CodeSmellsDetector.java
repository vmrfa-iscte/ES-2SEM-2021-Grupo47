package G47.Grupo47;

import java.io.File;
import java.util.ArrayList;

public class CodeSmellsDetector {
	private File selectedFile;
	private int rule1_threshold;
	private int rule2_threshold;
	private String operator;
	private ArrayList<Metrics> results;



	public CodeSmellsDetector (File selectedFile, int rule1, int rule2, String operator, ArrayList<Metrics> results) {
		this.rule1_threshold = rule1;
		this.rule2_threshold = rule2;
		this.operator = operator;
		this.results = results;
		this.selectedFile = selectedFile;

	}

	public ArrayList<HasCodeSmell> detectLongMethod() {
		ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();
		//		System.out.println("O operador é " + operator);
		//		System.out.println("Limite LOC_method + " + rule1_threshold );
		//		System.out.println("Limite Cyclo_method + " +  rule2_threshold);
		String lastclass = "";
		if (operator.equals("AND")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro");
					System.out.println("Existencia de code smell");
					if(!lastclass.equals(metric.getClasse())) {
						lastclass = metric.getClasse();
						HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"A classe tem code smells");
						view.add(positiveClass);
					}
						view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso");
					view.add(negative);
			

				}
			}
		}

		if (operator.equals("OR")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro");
					view.add(positive);
					if(!lastclass.equals(metric.getClasse())) {
						lastclass = metric.getClasse();
						HasCodeSmell positiveClass = new HasCodeSmell(metric.getClasse(),"A classe tem code smells");
						view.add(positiveClass);
					}
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso");
					view.add(negative);
				}
			}
		}
		return view;
	}

	public ArrayList<HasCodeSmell> detectGodClass() {
		ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();

		if (operator.equals("AND")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro");
					view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso");
					view.add(negative);

				}
			}
		}

		if (operator.equals("OR")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),"Verdadeiro");
					view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),"Falso");
					view.add(negative);

				}
			}
		}
		return view;

	}

}
