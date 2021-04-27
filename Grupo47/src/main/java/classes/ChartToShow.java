package classes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * @author Vasco Fontoura
 *
 */
public class ChartToShow {
	
	private ArrayList<HasCodeSmell> result;
	private QualityCalculator calculator = new QualityCalculator();
	
	/**
	 * @param uma lista de HasCodeSmell resultantes da deteção
	 */
	public ChartToShow(ArrayList<HasCodeSmell> result) {
		this.result = result;
	}
	
	
	/**
	 * @param um mapa com os tipos de qualidade e o número de ocurrências de cada
	 */
	public void createPieChart(HashMap<String,Integer> mapValues) {
		// Cria o PieChart numa nova janela (JFrame)
	    PieDataset dataset = createDataset(mapValues);
	    JFreeChart chart = ChartFactory.createPieChart(
	        "Quality of Detection Pie Chart",
	        dataset,
	        true, 
	        true,
	        false);
	    PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
	        "Quality {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
	    ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
	    ChartPanel panel = new ChartPanel(chart); 
	    JFrame jframe = new JFrame();
	    jframe.add(panel);
	    jframe.setVisible(true);
	    jframe.pack();
	  }

	
	  /**
	 * @param um mapa com os tipos de qualidade e o número de ocorrências de cada
	 * @return uma base de dados para criação de um PieChart
	 */
	@SuppressWarnings("rawtypes")
	private PieDataset createDataset(HashMap<String,Integer> mapValues) {
		// Cria os dados para o PieChart 
	    DefaultPieDataset dataset=new DefaultPieDataset();
	    Iterator<?> iterador = mapValues.entrySet().iterator();
		while(iterador.hasNext()) {
			Map.Entry pair = (Map.Entry)iterador.next();
			dataset.setValue((String)pair.getKey(),(Integer)pair.getValue());
		}
	    return dataset;
	  }
	  
	
	  /**
	 * @param uma lista de HasCodeSmell's vinda da leitura do excel fornecido
	 * @return um mapa com os tipos de qualidade e número de ocorrências
	 */
	public HashMap<String, Integer> setResults(ArrayList<HasCodeSmell> trueResults) {
		  // Compara a deteção de code smells atrvés da regra definida com a deteção presente no ficheiro excel escolhido
			// retorna os resultados para posteriormente ser criado o PieChart 
			HashMap<String, Integer> mapValues = new HashMap<>();
			int falsepositive = 0;
			int falsenegative = 0;
			int truepositive = 0;
			int truenegative = 0;
			for (HasCodeSmell indicator : result) {
				if(indicator.isMethod()) {
					for (HasCodeSmell calculated : trueResults) {
							if (indicator.isEqual(calculated)) {
								if (calculator.isTruePositive(indicator, calculated)) truepositive++;
								else if (calculator.isFalsePositive(indicator, calculated)) falsepositive++;
								else if (calculator.isFalseNegative(indicator, calculated)) falsenegative++;
								else if (calculator.isTrueNegative(indicator, calculated)) truenegative++;
							}
					}
				}
			}
			mapValues.put(QualityCalculator.FALSE_POSITIVE, falsepositive);
			mapValues.put(QualityCalculator.FALSE_NEGATIVE, falsenegative);
			mapValues.put(QualityCalculator.TRUE_POSITIVE, truepositive);
			mapValues.put(QualityCalculator.TRUE_NEGATIVE, truenegative);
			return mapValues;
		}

	  

}
