package gui;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import classes.ChartToShow;
import classes.FillTable;
import classes.HasCodeSmell;
import excel.ExcelManip;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class SecondaryGUI extends Shell {

	private static final int IS_GOD_EXCEL_COLUMN = 7;
	private static final int IS_LONG_EXCEL_COLUMN = 10;
	private static final String ISGOD_CLASS_DETECTION = "IsGod Class Detection";
	private static final String ISLONG_METHOD_DETECTION = "IsLong Method Detection";
	private static final String IMAGE_LOCATION = "/G47/Grupo47/iscte_logo2.jpg";
	private static final String HELP_MENU_TEXT = "Ajuda";
	private static final String SELECT_FILE_BUTTON_TEXT = "Selecionar ficheiro";
	private static final String QUALITY_COLUMN_TEXT = "Qualidade";
	private static final String DETECTION_COLUMN_TEXT = "Deteção";
	private static final String NAME_COLUMN_TEXT = "Classe/método";
	private static final String ID_COLUMN_TEXT = "Method id";
	private static final String EVALUATE_SMELL_BUTTON_TEXT = "Avaliar codesmell";
	private FillTable fillTable;
	private TableColumn nameColumn, detectionColumn;
	private Display display;
	private TableColumn methodIDColumn;
	private Text fileNametext;
	private File selectedFile;
	private TableColumn qualityColumn;
	private HashMap<String, Integer> mapValues;
	private ChartToShow chartToShow;
	private Button evaluateButton;
	private ArrayList<HasCodeSmell> result;
	private String name;
	private Table table;
	private Button selectFileButton_1;
	private FormData fd_table;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */

	/**
	 * Create the shell.
	 * 
	 * @param display display
	 * @param name    nome da GUI
	 * @param result  uma lista de HasCodeSmell
	 */
	public SecondaryGUI(Display display, String name, ArrayList<HasCodeSmell> result) {
		super(display, SWT.SHELL_TRIM);
		this.chartToShow = new ChartToShow(result);
		this.result = result;
		this.display = display;
		this.name = name;
		setImage(SWTResourceManager.getImage(SecondaryGUI.class, IMAGE_LOCATION));
		setLayout(new FormLayout());
		addElements();
		fillTable = new FillTable(table);
		fillTable.fillTable(result, false);
		createContents(name);
	}

	private void addElements() {
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		fd_table();
		FormData fd_evaluateButton = fd_evaluateButton();
		evaluateButtonListener();
		evaluateButton.setLayoutData(fd_evaluateButton);
		evaluateButton.setText(EVALUATE_SMELL_BUTTON_TEXT);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		methodIDColumn = new TableColumn(table, SWT.CENTER);
		methodIDColumn.setWidth(100);
		methodIDColumn.setText(ID_COLUMN_TEXT);

		nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setWidth(312);
		nameColumn.setText(NAME_COLUMN_TEXT);

		detectionColumn = new TableColumn(table, SWT.CENTER);
		detectionColumn.setWidth(147);
		detectionColumn.setText(DETECTION_COLUMN_TEXT);

		qualityColumn = new TableColumn(table, SWT.NONE);
		qualityColumn.setWidth(267);
		qualityColumn.setText(QUALITY_COLUMN_TEXT);

		fileNametext = new Text(this, SWT.BORDER);
		FormData fd_fileNametext = new FormData();
		fd_fileNametext.top = new FormAttachment(evaluateButton, 2, SWT.TOP);
		fd_fileNametext.left = new FormAttachment(evaluateButton, 25);
		fileNametext.setLayoutData(fd_fileNametext);

		fd_fileNametext.right = new FormAttachment(selectFileButton_1, -22);
		FormData fd_selectFileButton_1 = fd_selectFileButton();
		selectFileButton_1.setLayoutData(fd_selectFileButton_1);
		selectFileButtonListener(selectFileButton_1);
		selectFileButton_1.setText(SELECT_FILE_BUTTON_TEXT);

		Menu helpMenu = new Menu(this, SWT.BAR);
		setMenuBar(helpMenu);

		MenuItem helpMenuItem = new MenuItem(helpMenu, SWT.NONE);
		helpMenuItemListener(helpMenuItem);
		helpMenuItem.setText(HELP_MENU_TEXT);
		table.setLayoutData(fd_table);

	}

	private FormData fd_selectFileButton() {
		FormData fd_selectFileButton_1 = new FormData();
		fd_selectFileButton_1.right = new FormAttachment(0, 669);
		fd_selectFileButton_1.top = new FormAttachment(0, 707);
		fd_selectFileButton_1.left = new FormAttachment(0, 512);
		return fd_selectFileButton_1;
	}

	private FormData fd_evaluateButton() {
		FormData fd_evaluateButton = new FormData();
		fd_evaluateButton.right = new FormAttachment(0, 190);
		fd_evaluateButton.top = new FormAttachment(0, 707);
		fd_evaluateButton.left = new FormAttachment(0, 10);
		return fd_evaluateButton;
	}

	private void fd_table() {
		fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.right = new FormAttachment(100, 560);
		evaluateButton = new Button(this, SWT.NONE);
		fd_table.bottom = new FormAttachment(evaluateButton, -18);
		selectFileButton_1 = new Button(this, SWT.NONE);
		fd_table.right = new FormAttachment(selectFileButton_1, 0, SWT.RIGHT);
	}

	private void helpMenuItemListener(MenuItem helpMenuItem) {
		helpMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpSecondaryGUI hsg = new HelpSecondaryGUI(Display.getDefault());
				hsg.loadGUI();
			}
		});
	}

	private void selectFileButtonListener(Button selectFileButton) {
		selectFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
					selectedFile = pathpasta.getSelectedFile();
				fileNametext.setText(selectedFile.getPath());
			}
		});
	}

	private void evaluateButtonListener() {
		evaluateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Entrei no listener!");
				if (!fileNametext.getText().isEmpty()) {
					ExcelManip aux = new ExcelManip(selectedFile);
					ArrayList<HasCodeSmell> trueResults = new ArrayList<HasCodeSmell>();
					if (name.equals(ISLONG_METHOD_DETECTION)) {
						try {
							trueResults = aux.toComparables(IS_LONG_EXCEL_COLUMN);
							ArrayList<HasCodeSmell> toFill = fillTable.calculateQuality(trueResults, result);
							fillSecondaryGUI(toFill, true);
							createPieChartWithResults(trueResults);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (name.equals(ISGOD_CLASS_DETECTION)) {
						try {
							trueResults = aux.toComparables(IS_GOD_EXCEL_COLUMN);
							ArrayList<HasCodeSmell> toFill = fillTable.calculateQuality(trueResults, result);
							fillSecondaryGUI(toFill, true);
							createPieChartWithResults(trueResults);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um ficheiro");
				}

			}

			private void createPieChartWithResults(ArrayList<HasCodeSmell> trueResults) {
				mapValues = chartToShow.setResults(trueResults);
				chartToShow.createPieChart(mapValues);
			}
		});

	}

	/**
	 * Abre a GUI
	 */
	public void loadGUI() {
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
		setSize(905, 829);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void fillSecondaryGUI(ArrayList<HasCodeSmell> detectionResults, boolean withQuality) {
		fillTable.fillTable(detectionResults, withQuality);

	}
}