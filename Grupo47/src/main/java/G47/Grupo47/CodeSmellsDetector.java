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

		if (operator.equals("AND")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold && metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),true);
					view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),false);
					view.add(negative);

				}
			}
		}

		if (operator.equals("OR")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),true);
					view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),false);
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
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),true);
					view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),false);
					view.add(negative);

				}
			}
		}

		if (operator.equals("OR")) {
			for (Metrics metric : results) {
				if (metric.getLOC_method() > rule1_threshold || metric.getCYCLO_method() > rule2_threshold) {
					HasCodeSmell positive = new HasCodeSmell(metric.getNome_metodo(),true);
					view.add(positive);
				}
				else {
					HasCodeSmell negative = new HasCodeSmell(metric.getNome_metodo(),false);
					view.add(negative);

				}
			}
		}
		return view;

	}




}
