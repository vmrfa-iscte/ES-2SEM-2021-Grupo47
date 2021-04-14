package G47.Grupo47;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.SelectionListener;
import java.util.function.Consumer;
import org.eclipse.swt.widgets.TabFolder;

public class mainGUI extends Shell {

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
	private HashMap<String, ArrayList<String>> mapStats = new HashMap<>();
	private String username = System.getProperty("user.name");
	private File rules = new File("C:\\Users\\" + username + "\\Documents\\", "rules.txt");;
	private FileWriter fw;
	private BufferedWriter bw;

	private ArrayList<Rules> list = new ArrayList<Rules>();
	private Rules rule, currentRule;
	private int i;
	private String[] selected_rule;
	private ArrayList<Metrics> actualmetrics;
	private Label lblAvisoFaaUm;
	private Label lblDefinaUmaRegra;
	private Combo metrica2;

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
	public mainGUI(Display display) {
		super(display, SWT.SHELL_TRIM);

		setLayout(null);

		foldername = new Text(this, SWT.BORDER);
		foldername.setBounds(10, 35, 345, 26);

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
		pasta.setBounds(372, 33, 166, 30);
		pasta.setText("Selecionar pasta");

		ficheirosexcel = new List(this, SWT.BORDER);
		ficheirosexcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				indice = ficheirosexcel.getSelectionIndex();
				System.out.println("indice: " + indice);

				if (indice != -1) {
					for (Entry<String, ArrayList<String>> entry : mapStats.entrySet()) {
						System.out.println(ficheirosexcel.getItem(indice));
						if (entry.getKey().equals(ficheirosexcel.getItem(indice))) {
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

		ficheirosexcel.setBounds(10, 77, 345, 164);

		composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 247, 713, 358);

		txtNmeroDeMtodos = new Text(this, SWT.BORDER);
		txtNmeroDeMtodos.setEditable(false);
		txtNmeroDeMtodos.setText("Número de Métodos");
		txtNmeroDeMtodos.setBounds(372, 172, 166, 26);

		NumClasses = new Text(this, SWT.BORDER);
		NumClasses.setEditable(false);
		NumClasses.setBounds(544, 78, 78, 26);

		txtNmeroDeClasses = new Text(this, SWT.BORDER);
		txtNmeroDeClasses.setEditable(false);
		txtNmeroDeClasses.setText("Número de Classes");
		txtNmeroDeClasses.setBounds(372, 78, 166, 26);

		txtNmeroDeLinhas = new Text(this, SWT.BORDER);
		txtNmeroDeLinhas.setEditable(false);
		txtNmeroDeLinhas.setText("Número de Linhas");
		txtNmeroDeLinhas.setBounds(372, 215, 166, 26);

		txtNmeroDePackages = new Text(this, SWT.BORDER);
		txtNmeroDePackages.setEditable(false);
		txtNmeroDePackages.setText("Número de Packages");
		txtNmeroDePackages.setBounds(372, 127, 166, 26);

		NumPackages = new Text(this, SWT.BORDER);
		NumPackages.setEditable(false);
		NumPackages.setBounds(544, 127, 78, 26);

		NumMethods = new Text(this, SWT.BORDER);
		NumMethods.setEditable(false);
		NumMethods.setBounds(544, 172, 78, 26);

		NumLines = new Text(this, SWT.BORDER);
		NumLines.setEditable(false);
		NumLines.setBounds(544, 215, 78, 26);

		Button extrair = new Button(this, SWT.NONE);
		extrair.setBounds(544, 33, 179, 30);
		extrair.setText("Extrair métricas");
		extrair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirExplorer dirEx = new DirExplorer(selectedFile1);
				try {
					actualmetrics = dirEx.explore();
					ExcelManip em = new ExcelManip(selectedFile1);
					em.createExcel(actualmetrics);
					Statistics stats = new Statistics(actualmetrics);
					ArrayList<String> StringStats = new ArrayList<>();
					StringStats.add(String.valueOf(stats.countLinesOfCode()));
					StringStats.add(String.valueOf(stats.countNumberOfMethods()));
					StringStats.add(String.valueOf(stats.countClasses()));
					StringStats.add(String.valueOf(stats.countPackages()));
					System.out.println("em.getFileName(): " + em.getFileName());
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
		metrica1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("SelectionIndex: " + metrica1.getSelectionIndex());
				if (metrica1.getSelectionIndex() != -1) {
					System.out.println(metrica1.getItem(metrica1.getSelectionIndex()));
					if (metrica1.getItem(metrica1.getSelectionIndex()).equals("LOC_method")) {
						boolean hasCyclo = false;
						for (int i = 0; i < metrica2.getItems().length; i++) {
							if (!metrica2.getItems()[i].equals("CYCLO_method")) {
								metrica2.remove(i);
							} else {
								hasCyclo = true;
							}
						}
						if (!hasCyclo) {
							metrica2.add("CYCLO_method");
						}

					} else {
						boolean hasNOM = false;
						for (int i = 0; i < metrica2.getItems().length; i++) {
							if (!metrica2.getItems()[i].equals("NOM_class")) {
								metrica2.remove(i);
							} else {
								hasNOM = true;
							}
						}
						if (!hasNOM) {
							metrica2.add("NOM_class");
						}
					}
				}
			}
		});
		metrica1.setBounds(10, 65, 181, 28);
		metrica1.setText("");
		metrica1.add("LOC_method");
		metrica1.add("WMC_class");

		Combo operador = new Combo(composite, SWT.NONE);
		operador.setBounds(304, 65, 84, 28);
		operador.setText("");
		operador.add("OR");
		operador.add("AND");

		metrica2 = new Combo(composite, SWT.NONE);
		metrica2.setBounds(410, 65, 180, 28);
		metrica2.setText("");

		limite_1 = new Text(composite, SWT.BORDER);
		limite_1.setBounds(207, 65, 84, 30);
		limite_1.setText("Limite");

		limite_2 = new Text(composite, SWT.BORDER);
		limite_2.setBounds(612, 65, 91, 28);
		limite_2.setText("Limite");

		List regras = new List(composite, SWT.BORDER);
		regras.setBounds(10, 125, 435, 175);
		regras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				i = regras.getSelectionIndex();
				currentRule = list.get(i);
				metrica1.setText(currentRule.getMethod1());
				limite_1.setText(currentRule.getLimit1());
				metrica2.setText(currentRule.getMethod2());
				limite_2.setText(currentRule.getLimit2());
				operador.setText(currentRule.getOperator());

			}
		});

		btnDefinirRegras = new Button(composite, SWT.NONE);
		btnDefinirRegras.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean v = false;
				System.out.println("username: " + username);
				if (!rules.exists()) {
					try {
						System.out.println("A criar ficheiro");
						rules.createNewFile();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (!metrica1.getText().isEmpty() && !operador.getText().isEmpty() && !metrica2.getText().isEmpty()
						&& !limite_2.getText().isEmpty() && !limite_1.getText().isEmpty()) {

					rule = new Rules(metrica1.getText(), limite_1.getText(), operador.getText(), metrica2.getText(),
							limite_2.getText());
					String content = rule.toString();
					System.out.println(content);
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).toString().contentEquals(rule.toString())) {
							JOptionPane.showMessageDialog(null, "Regra já imposta");
							v = true;
							break;

						}
					}
					if (v == false) {
						regras.add(content);
						list.add(rule);
						System.out.println(list.size());
						try {
							FileWriter fw = new FileWriter(rules, true);
							BufferedWriter bw = new BufferedWriter(fw);
							System.out.println(rules.length());
							bw.write(content);
							bw.newLine();
							bw.close();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}

			}

		});

		btnDefinirRegras.setBounds(475, 124, 104, 30);
		btnDefinirRegras.setText("Definir regra");

		Button alterarregra = new Button(composite, SWT.NONE);
		alterarregra.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(list.get(i).toString());
				list.get(i).setLimit1(limite_1.getText());
				list.get(i).setLimit2(limite_2.getText());
				list.get(i).setMethod1(metrica1.getText());
				list.get(i).setMethod2(metrica2.getText());
				list.get(i).setOperator(operador.getText());
				System.out.println(list.get(i).toString());
				System.out.println(regras.getItem(i));
				String update = list.get(i).toString();
				for (int x = 0; x < list.size(); x++) {
					if (x == i) {
						regras.remove(x);
						regras.add(update, x);
						FileWriter fw;
						try {
							fw = new FileWriter(rules, true);
							BufferedWriter bw = new BufferedWriter(fw);
							System.out.println(rules.length());
							bw.write(update);
							bw.newLine();
							bw.close();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
				System.out.println(regras.getItem(i));

			}
		});

		alterarregra.setBounds(599, 124, 104, 30);
		alterarregra.setText("Alterar regra");

		Button codesmells = new Button(composite, SWT.NONE);
		codesmells.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String method1 = currentRule.getMethod1();
				int limit1 = Integer.parseInt(currentRule.getLimit1());
				String operator = currentRule.getOperator();
				String method2 = currentRule.getMethod2();
				int limit2 = Integer.parseInt(currentRule.getLimit2());
				CodeSmellsDetector detector = new CodeSmellsDetector(selectedFile1, limit1, limit2, operator,
						actualmetrics);
				if (method1.equals("LOC_method")) {
					ArrayList<HasCodeSmell> hcsList = detector.detectLongMethod();
					SecondaryGUI codesmells = new SecondaryGUI(display);
					for (HasCodeSmell hascodesmell : hcsList) {
						codesmells.addCodeSmellsInfo(hascodesmell);
					}
					codesmells.loadGUI();
				}
				if (method1.equals("WMC_class")) {
					ArrayList<HasCodeSmell> hcslist = detector.detectGodClass();
					SecondaryGUI codesmells2 = new SecondaryGUI(display);
					for (HasCodeSmell hascodesmell : hcslist) {
						codesmells2.addCodeSmellsInfo(hascodesmell);
						;
					}
					codesmells2.loadGUI();

				}

			}
		});

		Button carregarhist = new Button(composite, SWT.NONE);
		carregarhist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				regras.removeAll();
				list.clear();
				System.out.println(list.size());
				try {
					FileReader reader = new FileReader(rules);
					BufferedReader bufferedReader = new BufferedReader(reader);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						System.out.println(line);
						String[] rules = line.split(" ");
						for (int i = 0; i < rules.length; i++) {
							System.out.println(rules[i]);
						}
						Rules x = new Rules(rules[0], rules[2], rules[3], rules[4], rules[6]);
						list.add(x);
						regras.add(line);
					}
					reader.close();

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		carregarhist.setBounds(10, 306, 435, 30);
		carregarhist.setText("Carregar histórico de regras");

		codesmells.setBounds(475, 306, 228, 30);
		codesmells.setText("Deteção de codesmells");

		Label lblRegrasGuardadas = new Label(composite, SWT.NONE);
		lblRegrasGuardadas.setBounds(10, 99, 155, 20);
		lblRegrasGuardadas.setText("Regras guardadas:");

		lblAvisoFaaUm = new Label(composite, SWT.NONE);
		lblAvisoFaaUm.setAlignment(SWT.CENTER);
		lblAvisoFaaUm.setBounds(475, 211, 228, 89);
		lblAvisoFaaUm.setText(
				"Aviso: faça um duplo-clique na regra que pretende utilizar antes de prosseguir para \"Deteção de codesmells\"");

		lblDefinaUmaRegra = new Label(composite, SWT.NONE);
		lblDefinaUmaRegra.setText("Defina/altere uma regra para a deteção de codesmells: ");
		lblDefinaUmaRegra.setBounds(10, 21, 397, 20);

		Label lblProjetoJavaescolha = new Label(this, SWT.NONE);
		lblProjetoJavaescolha.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblProjetoJavaescolha.setBounds(10, 9, 528, 20);
		lblProjetoJavaescolha.setText("Escolha o projeto java que pretende analisar:");

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Interface gráfica- grupo 47");
		setSize(751, 652);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
