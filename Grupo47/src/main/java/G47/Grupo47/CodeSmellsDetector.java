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

	public void detectLongMethod() {
//		ArrayList<HasCodeSmell> view = new ArrayList<HasCodeSmell>();
//		for (Metrics metric : results) {
////			if (operator.equals("AND")) {
////			if (metric.getLOC_method() > rule1 && metric.getCYCLO_method() > rule2) {
////		}
			

	}

	public void detectGodClass() {



	}




}
