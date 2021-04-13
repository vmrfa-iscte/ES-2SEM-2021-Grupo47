package G47.Grupo47;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.SashForm;

public class SecondaryGUI extends Shell {
	
	private Table table;
	private TableColumn classmethod,detecao;
	private ScrolledComposite scrolledComposite;
	private Display display;
	private String name;
	private TableColumn tblclmnMethodId;

	/**
	 * Launch the application.
	 * @param args
	 */
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public SecondaryGUI(Display display,String name) {
		
		super(display, SWT.SHELL_TRIM);
		this.name = name;
		this.display = display;
		Combo regras = new Combo(this, SWT.NONE);
		regras.setBounds(10, 377, 425, 28);
			
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
			}
		});
		btnNewButton.setBounds(484, 375, 180, 30);
		btnNewButton.setText("Avaliar codesmell");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		int[] valores = new int[3];
		valores[0] = 1;
		valores[1] = 2;
		valores[2] = 3;
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 10, 480, 350);
		
		tblclmnMethodId = new TableColumn(table, SWT.CENTER);
		tblclmnMethodId.setWidth(100);
		tblclmnMethodId.setText("Method id");
		
		classmethod = new TableColumn(table, SWT.CENTER);
		classmethod.setWidth(229);
		classmethod.setText("Classe/método");
		
		detecao = new TableColumn(table, SWT.CENTER);
		detecao.setWidth(147);
		detecao.setText("Deteção");
		
		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(545, 10, 229, 340);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		createContents(name);
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


	/**
	 * Create contents of the shell.
	 */
	protected void createContents(String name) {
		setText(name);
		setSize(802, 475);

	}
	
	public void addCodeSmellsInfo(HasCodeSmell hcs) {
		TableItem tableItem = new TableItem(table,SWT.NONE);
		tableItem.setText(new String[]{hcs.getMethod_ID(),hcs.getName(), hcs.getHasCodeSmell()});
	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}