package G47.Grupo47;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
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
import org.eclipse.wb.swt.SWTResourceManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


import org.jfree.chart.plot.PiePlot;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class SecondaryGUI extends Shell {

	private Table table;
	private TableColumn classmethod,detecao;
	private Display display;
	private TableColumn tblclmnMethodId;
	private Text text;
	private File selectedFile;
	private ArrayList<HasCodeSmell> result;
	private TableColumn tblclmnQualidade;
	private HashMap<String,Integer> mapValues;
	private Composite composite;
	private Frame frame;


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
		setImage(SWTResourceManager.getImage(SecondaryGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		this.display = display;
		this.result = result;
		setLayout(new FormLayout());
		
		Button btnNewButton = new Button(this, SWT.NONE);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.right = new FormAttachment(0, 190);
		fd_btnNewButton.top = new FormAttachment(0, 707);
		fd_btnNewButton.left = new FormAttachment(0, 10);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				ExcelManip aux = new ExcelManip(selectedFile);
				ArrayList<HasCodeSmell> trueResults = new ArrayList<HasCodeSmell>(); 
				if(name.equals("IsLong Method Detection")) {
					try {
						trueResults = aux.toComparables(10);
						mapValues = setResults(trueResults);
						table.removeAll();
						for (HasCodeSmell a : result) {
							addCodeSmellsInfo(a,true);
						}
						createPieChart(mapValues);
						
						
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(name.equals("IsGod Class Detection")) {
					try {
						trueResults = aux.toComparables(7);
						mapValues = setResults(trueResults);
						table.removeAll();
						for (HasCodeSmell a : result) {

							addCodeSmellsInfo(a,true);
						}
						createPieChart(mapValues);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


			}
		});
		btnNewButton.setText("Avaliar codesmell");

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(0, 689);
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		int[] valores = new int[3];
		valores[0] = 1;
		valores[1] = 2;
		valores[2] = 3;
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		tblclmnMethodId = new TableColumn(table, SWT.CENTER);
		tblclmnMethodId.setWidth(100);
		tblclmnMethodId.setText("Method id");

		classmethod = new TableColumn(table, SWT.CENTER);
		classmethod.setWidth(260);
		classmethod.setText("Classe/método");

		detecao = new TableColumn(table, SWT.CENTER);
		detecao.setWidth(147);
		detecao.setText("Deteção");
		
		tblclmnQualidade = new TableColumn(table, SWT.NONE);
		tblclmnQualidade.setWidth(150);
		tblclmnQualidade.setText("Qualidade");

		text = new Text(this, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(btnNewButton, 2, SWT.TOP);
		fd_text.left = new FormAttachment(btnNewButton, 6);
		fd_text.right = new FormAttachment(0, 471);
		text.setLayoutData(fd_text);

		Button excell = new Button(this, SWT.NONE);
		fd_table.right = new FormAttachment(excell, 0, SWT.RIGHT);
		FormData fd_excell = new FormData();
		fd_excell.right = new FormAttachment(0, 669);
		fd_excell.top = new FormAttachment(0, 707);
		fd_excell.left = new FormAttachment(0, 512);
		excell.setLayoutData(fd_excell);
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
		excell.setText("Selecionar ficheiro");
		
//		composite = new Composite(this, SWT.EMBEDDED);
//		FormData fd_composite = new FormData();
//		fd_composite.right = new FormAttachment(table, 631, SWT.RIGHT);
//		fd_composite.top = new FormAttachment(0, 10);
//		fd_composite.left = new FormAttachment(table, 19);
//		fd_composite.bottom = new FormAttachment(0, 370);
//		composite.setLayoutData(fd_composite);
		
		
		
		
		

		


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
		setSize(697, 805);

	}

	public void addCodeSmellsInfo(HasCodeSmell hcs,boolean withQuality) {
		
		TableItem tableItem = new TableItem(table,SWT.NONE);
		if(withQuality) {
			tableItem.setText(new String[] {hcs.getMethod_ID(),hcs.getMethodName(),hcs.getHasCodeSmell(),hcs.getQuality()});
		}else {
		tableItem.setText(new String[]{hcs.getMethod_ID(),hcs.getMethodName(), hcs.getHasCodeSmell(),""});
		}

	}
	
	public HashMap<String,Integer> setResults(ArrayList<HasCodeSmell> trueResults) {
		HashMap<String,Integer> mapValues = new HashMap<>();
		int falsepositive = 0;
		int falsenegative = 0;
		int truepositive = 0;
		int truenegative = 0;
		for (HasCodeSmell indicator : trueResults) {
			for (HasCodeSmell calculated : result) {
				if (indicator.getMethodName().equals(calculated.getMethodName()) && indicator.getClassName().equals(calculated.getClassName()) && indicator.getPackageName().equals(calculated.getPackageName())) {
					if(calculated.getHasCodeSmell().equals("TRUE") && indicator.getHasCodeSmell().equals("TRUE")) {
						truepositive++;
					}else if(calculated.getHasCodeSmell().equals("TRUE") && indicator.getHasCodeSmell().equals("FALSE")) {
						falsepositive++;
					}else if(calculated.getHasCodeSmell().equals("FALSE") && indicator.getHasCodeSmell().equals("TRUE")) {
						falsenegative++;
					}else if(calculated.getHasCodeSmell().equals("FALSE") && indicator.getHasCodeSmell().equals("FALSE")) {
						truenegative++;
					}
					if (!indicator.getHasCodeSmell().equals(calculated.getHasCodeSmell())) {
						calculated.setQuality("Negativo");
					}
					else {
						calculated.setQuality("Positivo");
					}
				}
				
			}
		}
		
		mapValues.put("falsepositive", falsepositive);
		mapValues.put("falsenegative", falsenegative);
		mapValues.put("truepositive", truepositive);
		mapValues.put("truenegative", truenegative);
		return mapValues;
		
		
	}
	
	private void createPieChart(HashMap<String,Integer> mapValues) {
//	    // Create dataset
	    PieDataset dataset = createDataset(mapValues);
//	    // Create chart
	    JFreeChart chart = ChartFactory.createPieChart(
	        "Quality of Detection Pie Chart",
	        dataset,
	        true, 
	        true,
	        false);
	    
//	
//	  
//	    //Format Label
	    PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
	        "Quality {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
	    ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
//	    
//	    // Create Panel
//	    
	    ChartPanel panel = new ChartPanel(chart); 
   
	    System.out.println(panel.getSize());

	   
	    JFrame jframe = new JFrame();
	    jframe.add(panel);
	    jframe.setVisible(true);
	    jframe.pack();
	    

	  }

	  private PieDataset createDataset(HashMap<String,Integer> mapValues) {

	    DefaultPieDataset dataset=new DefaultPieDataset();
	    Iterator<?> iterador = mapValues.entrySet().iterator();
		while(iterador.hasNext()) {
			Map.Entry pair = (Map.Entry)iterador.next();
			System.out.println(pair.getKey());
			dataset.setValue((String)pair.getKey(),(Integer)pair.getValue());
		}
	    return dataset;
	  }

	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}