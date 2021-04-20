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
import java.text.NumberFormat;
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
import javax.swing.filechooser.FileNameExtensionFilter;

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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.SelectionListener;
import java.util.function.Consumer;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.GestureEvent;

public class mainGUI extends Shell {

	private int indice;
	private List ficheirosexcel;
	private Text foldername;
	private StyledText styledText;
	private Composite composite;
	private Button btnDefinirRegras;
	private Text limite_1;
	private Text limite_2;
	private Text codesmells;
	private String diretoria = new String();
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
	private File rules;
	private FileWriter fw;
	private BufferedWriter bw;
	private ArrayList<Rules> list = new ArrayList<Rules>();
	private Rules rule, currentRule;
	private int i;
	private String[] selected_rule;
	private ArrayList<Metrics> actualmetrics;
	private Label lblDefinaUmaRegra;
	private Combo metrica2;
	private Combo metrica3;
	private Label validation;
	private Label validation1;
	private Button btnSelecionarFicheirohistrico;
	private Text text_1;
	private File pastaselecionada;
	private Button guardarhistorico;
	private File historico;
	private Text limite_3;
	private Combo sinal;
	private Combo sinal2;
	private Combo sinal3;
	private Combo operador2;
	private String content;
	private String update;

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
		setMinimumSize(new Point(170, 47));
		setImage(SWTResourceManager.getImage(mainGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));

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

		ficheirosexcel = new List(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
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
							DirExplorer dirEx = new DirExplorer(selectedFile1);
							try {
								actualmetrics = dirEx.explore();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				}

			}
		});

		ficheirosexcel.setBounds(10, 77, 345, 164);

		composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 257, 842, 424);

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
					System.out.println("actualmetrics size: " + actualmetrics.size());
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

		Combo metrica1 = new Combo(composite, SWT.READ_ONLY);
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
						for (int y = 0; y < metrica3.getItems().length; y++) {
							if (metrica3.getItems()[y].equals("LOC_class")) {
								metrica3.remove(y);
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
		metrica1.setBounds(10, 65, 117, 28);
		metrica1.setText("");
		metrica1.add("LOC_method");
		metrica1.add("WMC_class");

		Combo operador = new Combo(composite, SWT.READ_ONLY);
		operador.setBounds(243, 65, 59, 28);
		operador.setText("");
		operador.add("OR");
		operador.add("AND");

		metrica2 = new Combo(composite, SWT.BORDER);
		metrica2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("SelectionIndex: " + metrica2.getSelectionIndex());
				if (metrica2.getSelectionIndex() != -1 && metrica3.getSelectionIndex() != -1) {
					System.out.println(metrica2.getItem(metrica2.getSelectionIndex()));
					if (metrica2.getItem(metrica2.getSelectionIndex()).equals("NOM_class")) {
						boolean hasLOC = false;
						for (int i = 0; i < metrica2.getItems().length; i++) {
							if (!metrica3.getItems()[i].equals("LOC_class")) {
								metrica3.remove(i);
							} else {
								hasLOC = true;
							}
						}
						if (!hasLOC) {
							metrica3.add("LOC_class");
						}

					}
				} else {
					metrica3.add("LOC_class");
				}
				if (metrica2.getItem(metrica2.getSelectionIndex()).equals("CYCLO_method")) {
					for (int y = 0; y < metrica3.getItems().length; y++) {
						if (metrica3.getItems()[y].equals("LOC_class")) {
							metrica3.remove(y);
						}
					}

				}
			}
		});
		metrica2.setBounds(313, 65, 94, 28);
		metrica2.setText("");

		metrica3 = new Combo(composite, SWT.BORDER);
		metrica3.setBounds(592, 65, 117, 28);
		metrica3.setText("");

		limite_1 = new Text(composite, SWT.BORDER);

		limite_1.setBounds(178, 65, 59, 30);
		limite_1.setText("Limite");

		limite_2 = new Text(composite, SWT.BORDER);
		limite_2.setBounds(460, 65, 60, 28);
		limite_2.setText("Limite");

		List regras = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		regras.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				i = regras.getSelectionIndex();
				currentRule = list.get(i);
				metrica1.setText(currentRule.getMethod1());
				sinal.setText(currentRule.getSinal1());
				limite_1.setText(currentRule.getLimit1());
				operador.setText(currentRule.getOperator());
				metrica2.setText(currentRule.getMethod2());
				sinal2.setText(currentRule.getSinal2());
				limite_2.setText(currentRule.getLimit2());
				operador2.setText(currentRule.getOperator2());
				metrica3.setText(currentRule.getMethod3());
				sinal3.setText(currentRule.getSinal3());
				limite_3.setText(currentRule.getLimit3());
			}
		});
		regras.setLocation(10, 144);
		regras.setSize(431, 230);

		btnDefinirRegras = new Button(composite, SWT.NONE);
		btnDefinirRegras.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isValid(limite_1.getText()) || !isValid(limite_2.getText()) || !isValid(limite_3.getText())) {
					JOptionPane.showMessageDialog(null, "Limites inválidos!");
				} else {
					boolean v = false;
					if (!metrica1.getText().isEmpty() && !operador.getText().isEmpty() && !metrica2.getText().isEmpty()
							&& !limite_2.getText().isEmpty() && !limite_1.getText().isEmpty()
							&& validation.getText().isEmpty() && validation1.getText().isEmpty()) {
						if (!metrica3.getText().isEmpty() && !operador2.getText().isEmpty()
								&& !limite_3.getText().isEmpty() && !sinal3.getText().isEmpty()) {
							rule = new Rules(metrica1.getText(), sinal.getText(), limite_1.getText(),
									operador.getText(), metrica2.getText(), sinal2.getText(), limite_2.getText(),
									operador2.getText(), metrica3.getText(), sinal3.getText(), limite_3.getText());
							content = rule.toString();
							for (int i = 0; i < list.size(); i++) {
								if (list.get(i).toString().contentEquals(rule.toString())) {
									JOptionPane.showMessageDialog(null, "Regra já imposta.");
									v = true;
									break;

								}
							}
						} else {
							rule = new Rules(metrica1.getText(), sinal.getText(), limite_1.getText(),
									operador.getText(), metrica2.getText(), sinal2.getText(), limite_2.getText(), "",
									"", "", "");
							content = rule.toString();
							for (int i = 0; i < list.size(); i++) {
								if (list.get(i).toString().contentEquals(rule.toString())) {
									JOptionPane.showMessageDialog(null, "Regra já imposta.");
									v = true;
									break;

								}
							}
						}

						System.out.println(content);
						if (v == false) {
							regras.add(content);
							list.add(rule);
							System.out.println(list.size());
						}

					} else {
						JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos.");
					}

				}
			}
		});

		btnDefinirRegras.setBounds(451, 143, 142, 30);
		btnDefinirRegras.setText("Definir regra");

		Button alterarregra = new Button(composite, SWT.NONE);
		alterarregra.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isValid(limite_1.getText()) || !isValid(limite_2.getText())) {
					JOptionPane.showMessageDialog(null, "Limites inválidos!");
				} else {
					if (regras.isSelected(i)) {
						System.out.println(list.get(i).toString());
						list.get(i).setLimit1(limite_1.getText());
						list.get(i).setLimit2(limite_2.getText());
						list.get(i).setLimit3(limite_3.getText());
						list.get(i).setMethod1(metrica1.getText());
						list.get(i).setMethod2(metrica2.getText());
						list.get(i).setMethod3(metrica3.getText());
						list.get(i).setOperator(operador.getText());
						list.get(i).setOperator2(operador2.getText());
						list.get(i).setSinal1(sinal.getText());
						list.get(i).setSinal2(sinal2.getText());
						list.get(i).setSinal3(sinal3.getText());
						System.out.println(list.get(i).toString());
						System.out.println(regras.getItem(i));
						update = list.get(i).toString();
						for (int x = 0; x < list.size(); x++) {
							if (x == i) {
								regras.remove(x);
								regras.add(update, x);

							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nenhuma regra selecionada");
					}

				}
			}
		});
		;

		alterarregra.setBounds(599, 143, 145, 30);
		alterarregra.setText("Alterar regras");

		Button codesmells = new Button(composite, SWT.NONE);
		codesmells.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (regras.isSelected(i)) {
					String method1 = currentRule.getMethod1();
					String signal1 = currentRule.getSinal1();
					int limit1 = Integer.parseInt(currentRule.getLimit1());
					String operator = currentRule.getOperator();
					String signal2 = currentRule.getSinal2();
					int limit2 = Integer.parseInt(currentRule.getLimit2());
//					String operator2 = currentRule.getOperator2();
//					String signal3 = currentRule.getSinal3();
//					int limit3 = Integer.parseInt(currentRule.getLimit3());
					if (method1.equals("LOC_method")) {
						if (signal1.equals(">") && signal2.equals(">")) {
								CodeSmellsDetector detector = new CodeSmellsDetector(limit1, limit2, operator, actualmetrics);
								ArrayList<HasCodeSmell> hcsList = detector.detectLongMethod();
								SecondaryGUI codesmells = new SecondaryGUI(display, "IsLong Method Detection", hcsList);
								for (HasCodeSmell hascodesmell : hcsList) {
									System.out.println("ID: " + hascodesmell.getMethod_ID());
									codesmells.addCodeSmellsInfo(hascodesmell, false);
								}
								codesmells.loadGUI();				
						}
						if (signal1.equals(">") && signal2.equals("<")) {
								CodeSmellsDetector detector = new CodeSmellsDetector(limit1, limit2, operator, actualmetrics);
								ArrayList<HasCodeSmell> hcsList = detector.detectLongMethodBiggerSmaller();
								SecondaryGUI codesmells = new SecondaryGUI(display, "IsLong Method Detection", hcsList);
								for (HasCodeSmell hascodesmell : hcsList) {
									System.out.println("ID: " + hascodesmell.getMethod_ID());
									codesmells.addCodeSmellsInfo(hascodesmell, false);
								}
								codesmells.loadGUI();
						
						}
						if (signal1.equals("<") && signal2.equals(">")) {
								CodeSmellsDetector detector = new CodeSmellsDetector(limit1, limit2, operator, actualmetrics);
								ArrayList<HasCodeSmell> hcsList = detector.detectLongMethodSmallerBigger();
								SecondaryGUI codesmells = new SecondaryGUI(display, "IsLong Method Detection", hcsList);
								for (HasCodeSmell hascodesmell : hcsList) {
									System.out.println("ID: " + hascodesmell.getMethod_ID());
									codesmells.addCodeSmellsInfo(hascodesmell, false);
								}
								codesmells.loadGUI();	
						}

						if (signal1.equals("<") && signal2.equals("<")) {
								CodeSmellsDetector detector = new CodeSmellsDetector(limit1, limit2, operator, actualmetrics);
								ArrayList<HasCodeSmell> hcsList = detector.detectLongMethodSmallerSmaller();
								SecondaryGUI codesmells = new SecondaryGUI(display, "IsLong Method Detection", hcsList);
								for (HasCodeSmell hascodesmell : hcsList) {
									System.out.println("ID: " + hascodesmell.getMethod_ID());
									codesmells.addCodeSmellsInfo(hascodesmell, false);
								}
								codesmells.loadGUI();
						}
//						if (method1.equals("WMC_class")) {
//							ArrayList<HasCodeSmell> hcslist2 = detector.detectGodClass();
//							System.out.println("hcslist2 size: " + hcslist2.size());
//							try {
//								a.fillWithCodeSmellResults(hcslist2, false);
//							} catch (IOException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							SecondaryGUI codesmells2 = new SecondaryGUI(display, "IsGod Class Detection", hcslist2);
//							codesmells2.loadGUI();
//
//						}
//					} else {
//						JOptionPane.showMessageDialog(null, "Nenhuma regra selecionada");
//
					}

					
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
				JFileChooser pathpasta = new JFileChooser(".txt");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				pathpasta.setFileFilter(filter);
				pathpasta.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnValue = pathpasta.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {

					historico = pathpasta.getSelectedFile();

				}
				if (historico.getPath().endsWith(".txt")) {
					try {
						FileReader reader = new FileReader(new File(historico.getPath()));
						BufferedReader bufferedReader = new BufferedReader(reader);
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							System.out.println(line);
							String[] rules = line.split(" ");
							for (int i = 0; i < rules.length; i++) {
								System.out.println(rules[i]);
							}
							if (rules.length > 9) {
								Rules x = new Rules(rules[0], rules[1], rules[2], rules[3], rules[4], rules[5],
										rules[6], rules[7], rules[8], rules[9], rules[10]);
								list.add(x);
								regras.add(line);
							} else {
								Rules x = new Rules(rules[0], rules[1], rules[2], rules[3], rules[4], rules[5],
										rules[6], "", "", "", "");
								list.add(x);
								regras.add(line);
							}

						}
						reader.close();

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ficheiro inválido");
				}

			}
		});

		carregarhist.setBounds(10, 380, 212, 30);
		carregarhist.setText("Carregar histórico de regras");

		codesmells.setBounds(451, 380, 293, 30);
		codesmells.setText("Deteção de codesmells");

		Label lblRegrasGuardadas = new Label(composite, SWT.NONE);
		lblRegrasGuardadas.setBounds(10, 118, 155, 20);
		lblRegrasGuardadas.setText("Regras guardadas:");

		lblDefinaUmaRegra = new Label(composite, SWT.NONE);
		lblDefinaUmaRegra.setText("Defina/altere uma regra para a deteção de codesmells: ");
		lblDefinaUmaRegra.setBounds(10, 23, 397, 20);

		validation = new Label(composite, SWT.NONE);
		validation.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		validation.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		validation.setBounds(197, 99, 84, 20);
		validation.setText("");

		validation1 = new Label(composite, SWT.NONE);
		validation1.setText("");
		validation1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		validation1.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		validation1.setBounds(599, 99, 145, 20);

		guardarhistorico = new Button(composite, SWT.NONE);
		guardarhistorico.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!list.isEmpty()) {
					Historico hist = new Historico(display, regras, list);
					//					hist.loadGUI();
					for (int y = 0; y < list.size(); y++) {
						FileWriter fw;
						try {
							fw = new FileWriter(new File(text_1.getText()), true);
							BufferedWriter bw = new BufferedWriter(fw);
							System.out.println(rules.length());
							bw.write(list.get(y).toString());
							bw.newLine();
							bw.close();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				} else {
					JOptionPane.showMessageDialog(null, "Lista de regras vazia");
				}
			}
		});
		guardarhistorico.setBounds(228, 380, 214, 30);
		guardarhistorico.setText("Guardar histórico");

		sinal = new Combo(composite, SWT.NONE);
		sinal.setBounds(139, 65, 33, 28);
		sinal.add(">");
		sinal.add("<");

		operador2 = new Combo(composite, SWT.BORDER);
		operador2.setBounds(526, 65, 60, 28);
		operador2.setText("");
		operador2.add("OR");
		operador2.add("AND");

		sinal3 = new Combo(composite, SWT.NONE);
		sinal3.setBounds(726, 65, 40, 28);
		sinal3.add(">");
		sinal3.add("<");

		limite_3 = new Text(composite, SWT.BORDER);
		limite_3.setText("");
		limite_3.setBounds(772, 65, 60, 28);

		sinal2 = new Combo(composite, SWT.NONE);
		sinal2.setBounds(414, 65, 40, 28);
		sinal2.add(">");
		sinal2.add("<");

		Label lblProjetoJavaescolha = new Label(this, SWT.NONE);
		lblProjetoJavaescolha.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblProjetoJavaescolha.setBounds(10, 9, 528, 20);
		lblProjetoJavaescolha.setText("Escolha o projeto java que pretende analisar:");

		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);

		MenuItem mntmAjuda = new MenuItem(menu, SWT.CASCADE);
		mntmAjuda.setText("Ajuda");

		Menu menu_1 = new Menu(mntmAjuda);
		mntmAjuda.setMenu(menu_1);

		MenuItem mntmUtilizaoDaInterface = new MenuItem(menu_1, SWT.NONE);
		mntmUtilizaoDaInterface.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpInterface hi = new HelpInterface(display);
				hi.loadGUI();

			}
		});
		mntmUtilizaoDaInterface.setText("Utilização da interface");

		MenuItem mntmInformaoSobreMtricas = new MenuItem(menu_1, SWT.NONE);
		mntmInformaoSobreMtricas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpGUIMetrics hgm = new HelpGUIMetrics(display);
				hgm.loadGUI();

			}
		});
		mntmInformaoSobreMtricas.setText("Informação sobre métricas");
		createContents();
	}

	private boolean isValid(String text) {
		System.out.println("text: " + text);
		for (int i = 0; i < text.length(); i++) {
			System.out.println(text.charAt(i));
			if (!(text.charAt(i) == '0' || text.charAt(i) == '1' || text.charAt(i) == '2' || text.charAt(i) == '3'
					|| text.charAt(i) == '4' || text.charAt(i) == '5' || text.charAt(i) == '6' || text.charAt(i) == '7'
					|| text.charAt(i) == '8' || text.charAt(i) == '9')) {
				System.out.println("false");
				return false;
			}
		}
		return true;
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Interface gráfica- Grupo 47");
		setSize(880, 768);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
