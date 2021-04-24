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
	
	private ShowExcelGUIElements showExcelGUIElement = new ShowExcelGUIElements();
	private Display display;
	public ShowExcelGUI(Display display,String name,ArrayList<MethodMetrics> metricsToShow) {
		this.display = display;
		showExcelGUIElement.setMetricsToShow(metricsToShow);
		// Escolher tamanho, imagem e nome da GUI
		setSize(1016, 635);
		setImage(SWTResourceManager.getImage(ShowExcelGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		setText(name);
		showExcelGUIElement.setContents(this);
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
