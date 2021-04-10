package G47.Grupo47;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class GuiClass extends Shell {


	//	private Color c = new Color (211,211,211);
	private int indice;
	private List ficheirosexcel;
	private Text foldername;
	private StyledText styledText;
	private Composite composite;
	private Button btnDefinirRegras;
	private Text limite_1;
	private Text limite_2;
	private Text codesmells;
	String nomepath = new String();
	File selectedFile1 = null;
	String nameFile = "";
	private Text txtNmeroDeMtodos;
	private Text NumClasses;
	private Text txtNmeroDeClasses;
	private Text txtNmeroDeLinhas;
	private Text txtNmeroDePackages;
	private Text NumPackages;
	private Text NumMethods;
	private Text NumLines;
	private HashMap<String,ArrayList<String>> mapStats = new HashMap<>();
	private File rules;
	private FileWriter fw;
	private BufferedWriter bw;
	private ArrayList<Metrics> actual_metrics;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public GuiClass(Display display) {
		super(display, SWT.SHELL_TRIM);

		setLayout(null);

		foldername = new Text(this, SWT.BORDER);
		foldername.setBounds(10, 14, 345, 26);

		Button pasta = new Button(this, SWT.NONE);
		pasta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					selectedFile1 = pathpasta.getSelectedFile();
					nomepath = selectedFile1.getAbsolutePath();
				}
				foldername.setText(selectedFile1.getPath());
			}
		});
		pasta.setBounds(372, 12, 166, 30);
		pasta.setText("Selecionar pasta");

		ficheirosexcel = new List(this, SWT.BORDER);
		ficheirosexcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				indice = ficheirosexcel.getSelectionIndex();
				System.out.println("indice: "+indice);

				if (indice != -1) {
					for (Entry<String, ArrayList<String>> entry : mapStats.entrySet()) {
						System.out.println(ficheirosexcel.getItem(indice));
						if(entry.getKey().equals(ficheirosexcel.getItem(indice))) {
							ArrayList<String> statsToWrite = entry.getValue();
							NumLines.setText(statsToWrite.get(0));
							NumClasses.setText(statsToWrite.get(2));
							NumMethods.setText(statsToWrite.get(1));
							NumPackages.setText(statsToWrite.get(3));
						}
					}

				}


			}
		});

		ficheirosexcel.setBounds(10, 57, 345, 188);

		composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 261, 713, 146);

		txtNmeroDeMtodos = new Text(this, SWT.BORDER);
		txtNmeroDeMtodos.setText("Número de Métodos");
		txtNmeroDeMtodos.setBounds(372, 160, 152, 26);

		NumClasses = new Text(this, SWT.BORDER);
		NumClasses.setBounds(544, 58, 78, 26);

		txtNmeroDeClasses = new Text(this, SWT.BORDER);
		txtNmeroDeClasses.setText("Número de Classes");
		txtNmeroDeClasses.setBounds(372, 58, 152, 26);

		txtNmeroDeLinhas = new Text(this, SWT.BORDER);
		txtNmeroDeLinhas.setText("Número de Linhas");
		txtNmeroDeLinhas.setBounds(372, 219, 152, 26);

		txtNmeroDePackages = new Text(this, SWT.BORDER);
		txtNmeroDePackages.setText("Número de Packages");
		txtNmeroDePackages.setBounds(372, 107, 152, 26);

		NumPackages = new Text(this, SWT.BORDER);
		NumPackages.setBounds(544, 107, 78, 26);

		NumMethods = new Text(this, SWT.BORDER);
		NumMethods.setBounds(544, 160, 78, 26);

		NumLines = new Text(this, SWT.BORDER);
		NumLines.setBounds(544, 219, 78, 26);


		Button extrair = new Button(this, SWT.NONE);
		extrair.setBounds(544, 12, 179, 30);
		extrair.setText("Extrair métricas");
		extrair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirExplorer dirEx = new DirExplorer(selectedFile1);
				try {
					actual_metrics = dirEx.explore();
					ExcelManip em = new ExcelManip(selectedFile1);
					em.createExcel(em.extractHeaders(), actual_metrics);
					Statistics stats = new Statistics(actual_metrics);
					ArrayList<String> StringStats = new ArrayList<>();
					StringStats.add(String.valueOf(stats.countLinesOfCode()));
					StringStats.add(String.valueOf(stats.countNumberOfMethods()));
					StringStats.add(String.valueOf(stats.countClasses()));
					StringStats.add(String.valueOf(stats.countPackages()));
					System.out.println("em.getFileName(): "+em.getFileName());
					mapStats.put(em.getFileName(), StringStats);
					ficheirosexcel.add(em.getFileName());


				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});




		Combo metrica1 = new Combo(composite, SWT.NONE);
		metrica1.setBounds(10, 20, 181, 28);
		metrica1.setText("Escolher método");
		metrica1.add("LOC_method");
		metrica1.add("WMC_class");


		Combo operador = new Combo(composite, SWT.NONE);
		operador.setBounds(309, 20, 84, 28);
		operador.setText("Operador");
		operador.add("OR");
		operador.add("AND");


		Combo metrica2 = new Combo(composite, SWT.NONE);
		metrica2.setBounds(412, 20, 180, 28);
		metrica2.setText("Escolher método");
		metrica2.add("CYCLO_method");
		metrica2.add("NOM_class");


		limite_1 = new Text(composite, SWT.BORDER);
		limite_1.setText("Limite");
		limite_1.setBounds(208, 20, 84, 30);


		limite_2 = new Text(composite, SWT.BORDER);
		limite_2.setBounds(612, 20, 91, 28);
		limite_2.setText("Limite");

		btnDefinirRegras = new Button(composite, SWT.NONE);
		btnDefinirRegras.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int count=0;
				count++;
				String username = System.getProperty("user.name");
				System.out.println("username: " + username);
				rules = new File("C:\\Users\\" + username + "\\Documents\\", "rules.txt");
				try {
					fw = new FileWriter(rules);
					bw = new BufferedWriter(fw);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (!rules.exists()) {
					try {
						rules.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				String content = new String("Rule" + count + ": " + metrica1.getText() + " > " + " " + limite_1.getText() + " "
						+ operador.getText() + " " + metrica2.getText() + " > " + " " + limite_2.getText());
				System.out.println(content);
				try {
					System.out.println("here4");
					bw.write(content);
					bw.close();
					System.out.println("Here5");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnDefinirRegras.setBounds(224, 68, 115, 30);
		btnDefinirRegras.setText("Definir regra");

		Button alterarregra = new Button(composite, SWT.NONE);
		alterarregra.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

		alterarregra.setBounds(364, 68, 115, 30);
		alterarregra.setText("Alterar regras");

		Button codesmells = new Button(composite, SWT.NONE);
		codesmells.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int limit1  = Integer.parseInt(limite_1.getText());
				int limit2 = Integer.parseInt(limite_2.getText());
				CodeSmellsDetector detector = new CodeSmellsDetector(selectedFile1,limit1,limit2,operador.getText(),actual_metrics);
				if (metrica1.getText().equals("LOC_method")) {
					detector.detectLongMethod();
				}
				if (metrica1.getText().equals("WMC_Class")) {
					detector.detectGodClass();
				}

				DetecaoCodeSmells codesmells = new DetecaoCodeSmells(display);
				codesmells.main(null);
			}
		});
		codesmells.setBounds(266, 106, 180, 30);
		codesmells.setText("Deteção de codesmells");






		//		}

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Interface gráfica- grupo 47");
		setSize(751, 464);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
