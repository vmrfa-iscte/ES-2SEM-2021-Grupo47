package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

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
import org.eclipse.swt.widgets.Text;

public class SecondaryGUI extends Shell {

	private Table table;
	private TableColumn classmethod,detecao;
	private ScrolledComposite scrolledComposite;
	private Display display;
	private String name;
	private TableColumn tblclmnMethodId;
	private Text text;
	private File selectedFile;
	private ArrayList<HasCodeSmell> result;

	/**
	 * Launch the application
	 * @param args
	 */

	/**
	 * Create the shell.
	 * @param display
	 */
	public SecondaryGUI(Display display,String name,ArrayList<HasCodeSmell> result) {

		super(display, SWT.SHELL_TRIM);
		this.name = name;
		this.display = display;
		this.result = result;
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExcelManip aux = new ExcelManip(selectedFile);
				ArrayList<HasCodeSmell> trueResults = new ArrayList<HasCodeSmell>(); 
				if(name.equals("IsLong Method Detection")) {
					try {
						trueResults = aux.toComparables(10);
						setResults(trueResults);
						for (HasCodeSmell a : result) {
							System.out.println("Nome método " + a.getMethodName() + "Classficacao pela regra " + a.getHasCodeSmell() + "Qualidade de deteção " + a.getQuality());
						}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(name.equals("IsGod Class Detection")) {
					try {
						trueResults = aux.toComparables(7);
						setResults(trueResults);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


			}
		});
		btnNewButton.setBounds(10, 376, 180, 30);
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

		text = new Text(this, SWT.BORDER);
		text.setBounds(316, 378, 275, 26);

		Button excell = new Button(this, SWT.NONE);
		excell.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {

					selectedFile = pathpasta.getSelectedFile();
				}
				text.setText(selectedFile.getPath());
			}
		});
		excell.setBounds(608, 376, 166, 30);
		excell.setText("Selecionar ficheiro");

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
		tableItem.setText(new String[]{hcs.getMethod_ID(),hcs.getMethodName(), hcs.getHasCodeSmell()});

	}
	
	public void setResults(ArrayList<HasCodeSmell> trueResults) {
		for (HasCodeSmell indicator : trueResults) {
			for (HasCodeSmell calculated : result) {
				if (indicator.getMethodName().equals(calculated.getMethodName()) && indicator.getClassName().equals(calculated.getClassName()) && indicator.getPackageName().equals(calculated.getPackageName())) {
					if (!indicator.getHasCodeSmell().equals(calculated.getHasCodeSmell())) {
						calculated.setQuality("Negativo");
					}
					else {
						calculated.setQuality("Positivo");
					}
				}
				
			}
		}
		
		
	}
	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}