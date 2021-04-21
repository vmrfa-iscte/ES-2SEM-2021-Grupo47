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
	private Table table;
	
	private ArrayList<Metrics> metricsToShow;
	
	public ShowExcelGUI(Display display,String name,ArrayList<Metrics> metricsToShow) {
		this.metricsToShow = metricsToShow;
		setSize(1016, 635);
		setImage(SWTResourceManager.getImage(ShowExcelGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		this.display = display;
		setText(name);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 10, 978, 568);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn.setWidth(86);
		tblclmnNewColumn.setText("Method ID");
		
		TableColumn tblclmnPackage = new TableColumn(table, SWT.CENTER);
		tblclmnPackage.setWidth(120);
		tblclmnPackage.setText("Package");
		
		TableColumn tblclmnClass = new TableColumn(table, SWT.CENTER);
		tblclmnClass.setWidth(137);
		tblclmnClass.setText("Class");
		
		TableColumn tblclmnMethod = new TableColumn(table, SWT.CENTER);
		tblclmnMethod.setWidth(152);
		tblclmnMethod.setText("Method");
		
		TableColumn tblclmnNomclass = new TableColumn(table, SWT.CENTER);
		tblclmnNomclass.setWidth(93);
		tblclmnNomclass.setText("NOM_class");
		
		TableColumn tblclmnLocclass = new TableColumn(table, SWT.CENTER);
		tblclmnLocclass.setWidth(85);
		tblclmnLocclass.setText("LOC_class");
		
		TableColumn tblclmnWmcclass = new TableColumn(table, SWT.CENTER);
		tblclmnWmcclass.setWidth(91);
		tblclmnWmcclass.setText("WMC_class");
		
		TableColumn tblclmnLocmethod = new TableColumn(table, SWT.CENTER);
		tblclmnLocmethod.setWidth(96);
		tblclmnLocmethod.setText("LOC_method");
		
		TableColumn tblclmnCyclomethod = new TableColumn(table, SWT.CENTER);
		tblclmnCyclomethod.setWidth(116);
		tblclmnCyclomethod.setText("CYCLO_method");
		
		
		
		for(Metrics metric: metricsToShow) {
			TableItem tableItem = new TableItem(table,SWT.NONE);
			String methodid = String.valueOf(metric.getMethod_ID());
			String nom_class = String.valueOf(metric.getNOM_class());
			String loc_class = String.valueOf(metric.getLOC_class());
			String wmc_class = String.valueOf(metric.getWMC_class());
			String cyclo_method = String.valueOf(metric.getCYCLO_method());
			String loc_method = String.valueOf(metric.getLOC_method());
			tableItem.setText(new String[] {methodid,metric.getPacote(),metric.getClasse(),metric.getNome_metodo(),nom_class,loc_class,wmc_class,loc_method,cyclo_method});
		}
	}
	
	public void loadGUI () {
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
