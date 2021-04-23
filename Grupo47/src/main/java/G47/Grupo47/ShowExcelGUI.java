package G47.Grupo47;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ShowExcelGUI extends Shell{
	
	private Display display;
	private Table tableForMetrics;
	private ArrayList<MethodMetrics> metricsToShow;
	
	public ShowExcelGUI(Display display,String name,ArrayList<MethodMetrics> metricsToShow) {
		this.display = display;
		this.metricsToShow = metricsToShow;
		// Escolher tamanho, imagem e nome da GUI
		setSize(1016, 635);
		setImage(SWTResourceManager.getImage(ShowExcelGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		setText(name);
		setContents();
	}
	
	private void setContents() {
		// Criar tabela para mostrar os metodos e as suas métricas
		tableForMetrics = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableForMetrics.setBounds(10, 10, 978, 568);
		tableForMetrics.setHeaderVisible(true);
		tableForMetrics.setLinesVisible(true);
		
		// Criar coluna para method_id
		TableColumn methodIDColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		methodIDColumn.setWidth(86);
		methodIDColumn.setText("Method ID");
		
		
		// Criar coluna para o nome do pacote a que o método pertence
		TableColumn packageColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		packageColumn.setWidth(120);
		packageColumn.setText("Package");
		
		// Criar coluna para o nome da classe a que o pacote pertence
		TableColumn classNameColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		classNameColumn.setWidth(137);
		classNameColumn.setText("Class");
		
		// Criar coluna para o nome do método
		TableColumn methodNameColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		methodNameColumn.setWidth(152);
		methodNameColumn.setText("Method");
		
		// Criar coluna para a métrica NOM_class
		TableColumn nomClassColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		nomClassColumn.setWidth(93);
		nomClassColumn.setText("NOM_class");
		
		// Criar coluna para a métrica LOC_class
		TableColumn locClassColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		locClassColumn.setWidth(85);
		locClassColumn.setText("LOC_class");
		
		// Criar coluna para a métrica WMC_class
		TableColumn wmcClassColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		wmcClassColumn.setWidth(91);
		wmcClassColumn.setText("WMC_class");
		
		// Criar coluna para a métrica LOC_method
		TableColumn locMethodColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		locMethodColumn.setWidth(96);
		locMethodColumn.setText("LOC_method");
		
		//Criar coluna para a métrica CYCLO_method
		TableColumn cycloMethodColumn = new TableColumn(tableForMetrics, SWT.CENTER);
		cycloMethodColumn.setWidth(116);
		cycloMethodColumn.setText("CYCLO_method");
		
		// Percorrer a lista de métricas recebida aquando da criação do objeto e mostrar os valores na tabela criada
		for(MethodMetrics metric: metricsToShow) {
			TableItem metricToInsert = new TableItem(tableForMetrics,SWT.NONE);
			String methodid = String.valueOf(metric.getMethod_ID());
			String nom_class = String.valueOf(metric.getNOM_class());
			String loc_class = String.valueOf(metric.getLOC_class());
			String wmc_class = String.valueOf(metric.getWMC_class());
			String cyclo_method = String.valueOf(metric.getCYCLO_method());
			String loc_method = String.valueOf(metric.getLOC_method());
			metricToInsert.setText(new String[] {methodid,metric.getPacote(),metric.getClasse(),metric.getNome_metodo(),nom_class,loc_class,wmc_class,loc_method,cyclo_method});
		}
	}
	
	public void loadGUI () {
		// Abrir e mostrar a GUI
		try {
			open();
			layout();
			while (!isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
