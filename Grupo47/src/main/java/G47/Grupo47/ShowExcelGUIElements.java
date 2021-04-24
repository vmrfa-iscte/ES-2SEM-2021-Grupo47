package G47.Grupo47;


import org.eclipse.swt.widgets.Table;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ShowExcelGUIElements {
	private Table tableForMetrics;
	private ArrayList<MethodMetrics> metricsToShow;

	public void setMetricsToShow(ArrayList<MethodMetrics> metricsToShow) {
		this.metricsToShow = metricsToShow;
	}

	public void setContents(ShowExcelGUI showExcelGUI) {
		tableForMetrics = new Table(showExcelGUI, SWT.BORDER | SWT.FULL_SELECTION);
		tableForMetrics.setBounds(10, 10, 978, 568);
		tableForMetrics.setHeaderVisible(true);
		tableForMetrics.setLinesVisible(true);
		TableColumn methodIDColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		methodIDColumn.setWidth(86);
		methodIDColumn.setText("Method ID");
		TableColumn packageColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		packageColumn.setWidth(120);
		packageColumn.setText("Package");
		TableColumn classNameColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		classNameColumn.setWidth(137);
		classNameColumn.setText("Class");
		TableColumn methodNameColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		methodNameColumn.setWidth(152);
		methodNameColumn.setText("Method");
		TableColumn nomClassColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		nomClassColumn.setWidth(93);
		nomClassColumn.setText("NOM_class");
		TableColumn locClassColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		locClassColumn.setWidth(85);
		locClassColumn.setText("LOC_class");
		TableColumn wmcClassColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		wmcClassColumn.setWidth(91);
		wmcClassColumn.setText("WMC_class");
		TableColumn locMethodColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		locMethodColumn.setWidth(96);
		locMethodColumn.setText("LOC_method");
		TableColumn cycloMethodColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		cycloMethodColumn.setWidth(116);
		cycloMethodColumn.setText("CYCLO_method");
		for (MethodMetrics metric : metricsToShow) {
			TableItem metricToInsert = new TableItem(tableForMetrics, SWT.NONE);
			String methodid = String.valueOf(metric.getMethod_ID());
			String nom_class = String.valueOf(metric.getNOM_class());
			String loc_class = String.valueOf(metric.getLOC_class());
			String wmc_class = String.valueOf(metric.getWMC_class());
			String cyclo_method = String.valueOf(metric.getCYCLO_method());
			String loc_method = String.valueOf(metric.getLOC_method());
			metricToInsert.setText(new String[] { methodid, metric.getPacote(), metric.getClasse(),
					metric.getNome_metodo(), nom_class, loc_class, wmc_class, loc_method, cyclo_method });
		}
	}
}