package G47.Grupo47;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class mainGUI extends Shell {

	private int indice, i;
	private List excelfiles;
	private Text foldername, txtNmeroDeMtodos, NumClasses, txtNmeroDeClasses, txtNmeroDeLinhas, txtNmeroDePackages,
			NumPackages, NumMethods, NumLines, limit_1, limit_2, limit_3;
	private Composite composite;
	private Button btnDefineRule, savehistory;
	private String pathname = new String();
	private File selectedFile1 = null;
	private String pathToExtract = "";
	private HashMap<String, ArrayList<String>> mapStats = new HashMap<>();
	private File history;
	private ArrayList<Rule> list = new ArrayList<Rule>();
	private Rule rule, currentRule;
	private ArrayList<MethodMetrics> actualmetrics;
	private Label lbldefinerule;
	private Combo metric3, signal, sinal2, signal3, operator2;
	private String content, update;
	private Combo metric2;
	private Text folderToExtract;
	private File folderextraction = null;
	private static final char ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6, SEVEN = 7, EIGHT = 8,
			NINE = 9;
	private static final String LOGO = "/G47/Grupo47/iscte_logo2.jpg", GUI_NAME = "Interface gráfica- Grupo 47";
	private static final String NO_METRICS_EXTRACTED_ERROR_MESSAGE = "Não foram extraidas métricas ou não existe nenhum projeto selecionado",
	NO_RULE_SELECTED_ERROR_MESSAGE = "Nenhuma regra selecionada";
	private DetectionChooser chooser = new DetectionChooser();

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
		setImage(SWTResourceManager.getImage(mainGUI.class, LOGO));
		addElements(display);
		setLayout(null);
		createContents();
	}

	private void addElements(Display display) {
		foldername = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		foldername.setBounds(10, 67, 345, 26);

		Button folder = new Button(this, SWT.NONE);
		folder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					selectedFile1 = pathpasta.getSelectedFile();
					pathname = selectedFile1.getAbsolutePath();
				}
				if (selectedFile1 != null) {
					foldername.setText(selectedFile1.getPath());
				}
			}
		});
		folder.setBounds(372, 67, 166, 28);
		folder.setText("Selecionar projeto (src)");

		excelfiles = new List(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		excelfiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				indice = excelfiles.getSelectionIndex();
				System.out.println("indice: " + indice);

				if (indice != -1) {
					for (Entry<String, ArrayList<String>> entry : mapStats.entrySet()) {
						System.out.println(excelfiles.getItem(indice));
						if (entry.getKey().equals(excelfiles.getItem(indice))) {
							ArrayList<String> statsToWrite = entry.getValue();
							NumLines.setText(statsToWrite.get(0));
							NumClasses.setText(statsToWrite.get(2));
							NumMethods.setText(statsToWrite.get(1));
							NumPackages.setText(statsToWrite.get(3));
							DirExplorer dirEx = new DirExplorer(selectedFile1);
							try {
								actualmetrics = dirEx.exploreAndExtract();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				}

			}
		});

		excelfiles.setBounds(10, 108, 345, 164);

		composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 314, 670, 465);

		txtNmeroDeMtodos = new Text(this, SWT.BORDER);
		txtNmeroDeMtodos.setEditable(false);
		txtNmeroDeMtodos.setText("Número de Métodos");
		txtNmeroDeMtodos.setBounds(372, 198, 166, 26);

		NumClasses = new Text(this, SWT.BORDER);
		NumClasses.setEditable(false);
		NumClasses.setBounds(544, 109, 136, 26);

		txtNmeroDeClasses = new Text(this, SWT.BORDER);
		txtNmeroDeClasses.setEditable(false);
		txtNmeroDeClasses.setText("Número de Classes");
		txtNmeroDeClasses.setBounds(372, 109, 166, 26);

		txtNmeroDeLinhas = new Text(this, SWT.BORDER);
		txtNmeroDeLinhas.setEditable(false);
		txtNmeroDeLinhas.setText("Número de Linhas");
		txtNmeroDeLinhas.setBounds(372, 246, 166, 26);

		txtNmeroDePackages = new Text(this, SWT.BORDER);
		txtNmeroDePackages.setEditable(false);
		txtNmeroDePackages.setText("Número de Packages");
		txtNmeroDePackages.setBounds(372, 152, 166, 26);

		NumPackages = new Text(this, SWT.BORDER);
		NumPackages.setEditable(false);
		NumPackages.setBounds(544, 152, 136, 26);

		NumMethods = new Text(this, SWT.BORDER);
		NumMethods.setEditable(false);
		NumMethods.setBounds(544, 198, 136, 26);

		NumLines = new Text(this, SWT.BORDER);
		NumLines.setEditable(false);
		NumLines.setBounds(544, 246, 136, 26);

		Button extract = new Button(this, SWT.NONE);
		extract.setBounds(544, 48, 136, 30);
		extract.setText("Extrair métricas");
		extract.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedFile1 == null || folderextraction == null) {
					JOptionPane.showMessageDialog(null, "Escolha uma pasta");
				} else {
					DirExplorer dirEx = new DirExplorer(selectedFile1);
					try {
						if (folderextraction.exists() && selectedFile1.exists()) {
							NameByFile excelFileName = new NameByFile();
							excelFileName.setFileToExtract(selectedFile1);
							actualmetrics = dirEx.exploreAndExtract();
							ExcelManip em = new ExcelManip(selectedFile1);
							em.createExcel(actualmetrics, folderToExtract.getText());
							Statistics stats = new Statistics(actualmetrics);
							ArrayList<String> StringStats = new ArrayList<>();
							StringStats.add(String.valueOf(stats.countLinesOfCode()));
							StringStats.add(String.valueOf(stats.countNumberOfMethods()));
							StringStats.add(String.valueOf(stats.countClasses()));
							StringStats.add(String.valueOf(stats.countPackages()));
							mapStats.put(excelFileName.getFileName(), StringStats);
							excelfiles.add(excelFileName.getFileName());
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		Combo metric1 = new Combo(composite, SWT.READ_ONLY);
		metric1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (metric1.getSelectionIndex() != -1) {
					if (metric1.getItem(metric1.getSelectionIndex()).equals("LOC_method")) {
						operator2.setVisible(false);
						signal3.setVisible(false);
						metric3.setVisible(false);
						limit_3.setVisible(false);
						metric2.removeAll();
						metric2.add("CYCLO_method");
					} else if (metric1.getItem(metric1.getSelectionIndex()).equals("WMC_class")) {
						metric2.removeAll();
						metric2.add("NOM_class");
						metric2.add("LOC_class");
						operator2.setVisible(true);
						signal3.setVisible(true);
						metric3.setVisible(true);
						limit_3.setVisible(true);
					} else if (metric1.getItem(metric1.getSelectionIndex()).equals("NOM_class")) {
						operator2.setVisible(false);
						signal3.setVisible(false);
						metric3.setVisible(false);
						limit_3.setVisible(false);
						metric2.removeAll();
						metric2.add("LOC_class");
					}
				}
			}
		});
		metric1.setBounds(10, 58, 155, 28);
		metric1.setText("");
		metric1.add("LOC_method");
		metric1.add("WMC_class");
		metric1.add("NOM_class");

		Combo operador = new Combo(composite, SWT.READ_ONLY);
		operador.setBounds(440, 58, 117, 28);
		operador.setText("");
		operador.add("OR");
		operador.add("AND");

		metric2 = new Combo(composite, SWT.READ_ONLY);
		metric2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (metric2.getSelectionIndex() != -1) {
					if (metric2.getItem(metric2.getSelectionIndex()).equals("LOC_class")) {
						operator2.setVisible(false);
						signal3.setVisible(false);
						metric3.setVisible(false);
						limit_3.setVisible(false);
					} else if (metric2.getItem(metric2.getSelectionIndex()).equals("NOM_class")) {
						operator2.setVisible(true);
						signal3.setVisible(true);
						metric3.setVisible(true);
						limit_3.setVisible(true);
					}
				}
			}
		});
		metric2.setBounds(10, 92, 155, 28);
		metric2.setText("");

		metric3 = new Combo(composite, SWT.READ_ONLY);
		metric3.setBounds(10, 126, 155, 28);
		metric3.setText("");
		metric3.add("");
		metric3.add("LOC_class");
		metric3.setVisible(false);

		limit_1 = new Text(composite, SWT.BORDER);
		limit_1.setBounds(313, 58, 94, 30);
		limit_1.setText("Limite");

		limit_2 = new Text(composite, SWT.BORDER);
		limit_2.setBounds(313, 92, 94, 28);
		limit_2.setText("Limite");

		List listrulestoshow = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		listrulestoshow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				i = listrulestoshow.getSelectionIndex();
				currentRule = list.get(i);
				for (int l = 0; l < metric1.getItemCount(); l++) {
					if (metric1.getItem(l).contentEquals(currentRule.getMethod1())) {
						metric1.select(l);
					}
				}
				if (metric1.getItem(metric1.getSelectionIndex()).equals("LOC_method")) {
					metric2.removeAll();
					metric2.add("CYCLO_method");
				} else if (metric1.getItem(metric1.getSelectionIndex()).equals("WMC_class")) {
					metric2.removeAll();
					metric2.add("NOM_class");
					metric2.add("LOC_class");
				} else if (metric1.getItem(metric1.getSelectionIndex()).equals("NOM_class")) {
					metric2.removeAll();
					metric2.add("LOC_class");
				}
				for (int k = 0; k < metric2.getItemCount(); k++) {
//					System.out.println(metric2.getItem(k) + "kkkkk");
					if (metric2.getItem(k).contentEquals(currentRule.getMethod2())) {
						metric2.select(k);
					}
				}
				if (!currentRule.getLimit3().isEmpty()) {

					metric3.setVisible(true);
					limit_3.setVisible(true);
					operator2.setVisible(true);
					signal3.setVisible(true);
					for (int z = 0; z < metric3.getItemCount(); z++) {
						if (metric3.getItem(z).contentEquals(currentRule.getMethod3())) {
							metric3.select(z);
						}
					}
				} else {
					operator2.setVisible(false);
					signal3.setVisible(false);
					metric3.setVisible(false);
					limit_3.setVisible(false);
				}
//				metric1.setText(currentRule.getMethod1());
				signal.setText(currentRule.getSinal1());
				limit_1.setText(currentRule.getLimit1());
				operador.setText(currentRule.getOperator());
//				metric2.setText(currentRule.getMethod2());
				sinal2.setText(currentRule.getSinal2());
				limit_2.setText(currentRule.getLimit2());
				operator2.setText(currentRule.getOperator2());
				signal3.setText(currentRule.getSinal3());
				limit_3.setText(currentRule.getLimit3());
			}
		});
		listrulestoshow.setLocation(10, 186);
		listrulestoshow.setSize(431, 230);

		btnDefineRule = new Button(composite, SWT.NONE);
		btnDefineRule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isValid(limit_1.getText()) || !isValid(limit_2.getText())) {
					JOptionPane.showMessageDialog(null, "Limites inválidos!");
				} else {
					if ((metric3.getText().isEmpty() && operator2.getText().isEmpty()
							&& (limit_3.getText().isEmpty() || limit_3.getText().equals("Limite"))
							&& signal3.getText().isEmpty())
							|| (!metric3.getText().isEmpty() && !operator2.getText().isEmpty()
									&& !(limit_3.getText().isEmpty() || limit_3.getText().equals("Limite"))
									&& !signal3.getText().isEmpty())) {

						boolean v = false;
						if (!metric1.getText().isEmpty() && !operador.getText().isEmpty()
								&& !metric2.getText().isEmpty() && !limit_2.getText().isEmpty()
								&& !limit_1.getText().isEmpty()) {
							if (!metric3.getText().isEmpty() && !operator2.getText().isEmpty()
									&& !limit_3.getText().isEmpty() && !signal3.getText().isEmpty()) {
								rule = new Rule(metric1.getText(), signal.getText(), limit_1.getText(),
										operador.getText(), metric2.getText(), sinal2.getText(), limit_2.getText(),
										operator2.getText(), metric3.getText(), signal3.getText(), limit_3.getText());
								content = rule.toString();
								for (int i = 0; i < list.size(); i++) {
									if (list.get(i).toString().contentEquals(rule.toString())) {
										JOptionPane.showMessageDialog(null, "Regra já imposta.");
										v = true;
										break;

									}
								}
							} else {
								rule = new Rule(metric1.getText(), signal.getText(), limit_1.getText(),
										operador.getText(), metric2.getText(), sinal2.getText(), limit_2.getText(), "",
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
								listrulestoshow.add(content);
								list.add(rule);
								System.out.println(list.size());
							}

						} else {
							JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos");
					}
				}

			}

		});

		btnDefineRule.setBounds(447, 185, 142, 30);
		btnDefineRule.setText("Definir regra");

		Button changerule = new Button(composite, SWT.NONE);
		changerule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isValid(limit_1.getText()) || !isValid(limit_2.getText())) {
					JOptionPane.showMessageDialog(null, "Limites inválidos!");
				} else {
					if ((metric3.getText().isEmpty() && operator2.getText().isEmpty()
							&& (limit_3.getText().isEmpty() || limit_3.getText().equals("Limite"))
							&& signal3.getText().isEmpty())
							|| (!metric3.getText().isEmpty() && !operator2.getText().isEmpty()
									&& !(limit_3.getText().isEmpty() || limit_3.getText().equals("Limite"))
									&& !signal3.getText().isEmpty())) {
						if (listrulestoshow.isSelected(i)) {
//							limit_3.setSelection(1);
							metric2.setText("TESTEEEE");
							metric2.deselectAll();
							metric2.select(0);
							System.out.println(list.get(i).toString());
							list.get(i).setLimit1(limit_1.getText());
							list.get(i).setLimit2(limit_2.getText());
							list.get(i).setLimit3(limit_3.getText());
							list.get(i).setMethod1(metric1.getText());
							list.get(i).setMethod2(metric2.getText());
							list.get(i).setMethod3(metric3.getText());
							list.get(i).setOperator(operador.getText());
							list.get(i).setOperator2(operator2.getText());
							list.get(i).setSinal1(signal.getText());
							list.get(i).setSinal2(sinal2.getText());
							list.get(i).setSinal3(signal3.getText());
							System.out.println(list.get(i).toString());
							System.out.println(listrulestoshow.getItem(i));
							update = list.get(i).toString();
							for (int x = 0; x < list.size(); x++) {
								if (x == i) {
									listrulestoshow.remove(x);
									listrulestoshow.add(update, x);

								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Nenhuma regra selecionada");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos");
					}

				}
			}
		});
		;

		changerule.setBounds(447, 224, 145, 30);
		changerule.setText("Alterar regras");

		Button codesmells = new Button(composite, SWT.NONE);
		codesmells.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (actualmetrics == null) {
					// Caso não tenham sido extraídas métricas de um projeto, mensagem de erro
					JOptionPane.showMessageDialog(null, NO_METRICS_EXTRACTED_ERROR_MESSAGE);

				} else {
					// Caso contrário a existencia de code smells no projeto é averiguada 
					if (listrulestoshow.isSelected(i)) {
						// Apenas é averiguada caso exista uma regra selecionada
						String method1 = currentRule.getMethod1();
						String method2 = currentRule.getMethod2();
						evaluationChooser(method1,method2);

					} else {
						// Caso contário é mostrada uma mensagem de erro
						JOptionPane.showMessageDialog(null, NO_RULE_SELECTED_ERROR_MESSAGE);
					}

				}
			}

		});

		Button loadhistoric = new Button(composite, SWT.NONE);
		loadhistoric.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listrulestoshow.removeAll();
				list.clear();
				System.out.println(list.size());
				JFileChooser pathpasta = new JFileChooser(".txt");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				pathpasta.setFileFilter(filter);
				pathpasta.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnValue = pathpasta.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {

					history = pathpasta.getSelectedFile();

				}
				if (history == null) {
				} else {
					if (history.getPath().endsWith(".txt")) {
						try {
							FileReader reader = new FileReader(new File(history.getPath()));
							BufferedReader bufferedReader = new BufferedReader(reader);
							String line;
							while ((line = bufferedReader.readLine()) != null) {
								System.out.println(line);
								String[] rules = line.split(" ");
								for (int i = 0; i < rules.length; i++) {
									System.out.println(rules[i]);
								}
								if (rules.length > 9) {
									Rule x = new Rule(rules[0], rules[1], rules[2], rules[3], rules[4], rules[5],
											rules[6], rules[7], rules[8], rules[9], rules[10]);
									list.add(x);
									listrulestoshow.add(line);
								} else {
									Rule x = new Rule(rules[0], rules[1], rules[2], rules[3], rules[4], rules[5],
											rules[6], "", "", "", "");
									list.add(x);
									listrulestoshow.add(line);
								}

							}
							reader.close();
						} catch (FileNotFoundException e1) {

							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Ficheiro inválido");
					}
				}

			}

		});

		loadhistoric.setBounds(10, 422, 212, 30);
		loadhistoric.setText("Carregar histórico de regras");

		codesmells.setBounds(451, 422, 167, 30);
		codesmells.setText("Deteção de codesmells");

		Label lblrulessaved = new Label(composite, SWT.NONE);
		lblrulessaved.setBounds(10, 160, 155, 20);
		lblrulessaved.setText("Regras guardadas:");

		lbldefinerule = new Label(composite, SWT.NONE);
		lbldefinerule.setText("Defina/altere uma regra para a deteção de codesmells: ");
		lbldefinerule.setBounds(10, 32, 397, 20);

		savehistory = new Button(composite, SWT.NONE);
		savehistory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!list.isEmpty()) {
					SaveHistoryGUI hist = new SaveHistoryGUI(display, listrulestoshow, list);
					hist.loadGUI();
				} else {
					JOptionPane.showMessageDialog(null, "Lista de regras vazia");
				}
			}
		});
		savehistory.setBounds(227, 422, 214, 30);
		savehistory.setText("Guardar histórico");

		signal = new Combo(composite, SWT.READ_ONLY);
		signal.setBounds(196, 58, 80, 28);
		signal.add(">");
		signal.add("<");

		operator2 = new Combo(composite, SWT.READ_ONLY);
		operator2.setBounds(440, 92, 117, 28);
		operator2.setText("");
		operator2.add("OR");
		operator2.add("AND");
		operator2.add(" ");
		operator2.setVisible(false);

		signal3 = new Combo(composite, SWT.READ_ONLY);
		signal3.setBounds(196, 126, 80, 28);
		signal3.add(">");
		signal3.add("<");
		signal3.add(" ");
		signal3.setVisible(false);

		limit_3 = new Text(composite, SWT.BORDER);
		limit_3.setText("Limite");
		limit_3.setBounds(313, 126, 94, 28);
		limit_3.setVisible(false);

		sinal2 = new Combo(composite, SWT.READ_ONLY);
		sinal2.setBounds(195, 92, 81, 28);
		sinal2.add(">");
		sinal2.add("<");

		Button cleanHistoryList = new Button(composite, SWT.NONE);
		cleanHistoryList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(listrulestoshow.getItemCount());

				if (listrulestoshow.getItemCount() != 0) {
					listrulestoshow.removeAll();

				} else {
					JOptionPane.showMessageDialog(null, "Lista de regras já vazia");
				}
			}
		});
		cleanHistoryList.setBounds(449, 267, 140, 30);
		cleanHistoryList.setText("Limpar lista");

		Label lblchoosejavaproject = new Label(this, SWT.NONE);
		lblchoosejavaproject.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblchoosejavaproject.setBounds(10, 9, 528, 20);
		lblchoosejavaproject.setText("Escolha o projeto java que pretende analisar:");

		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);

		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Ajuda");

		Menu menu_1 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_1);

		MenuItem mntmUtilizaoDaInterface = new MenuItem(menu_1, SWT.NONE);
		mntmUtilizaoDaInterface.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpInterface helpinterface = new HelpInterface(display);
				helpinterface.loadGUI();

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

		Button btnViewFile = new Button(this, SWT.NONE);
		btnViewFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (actualmetrics != null) {
					ShowExcelGUI showGUI = new ShowExcelGUI(display, "Visualizar ficheiro", actualmetrics);
					showGUI.loadGUI();
				} else {
					JOptionPane.showMessageDialog(null, "Escolha um projeto e extraia métricas");
				}
			}
		});
		btnViewFile.setBounds(10, 278, 109, 30);
		btnViewFile.setText("Ver ficheiro");

		folderToExtract = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		folderToExtract.setBounds(10, 35, 345, 26);

		Button choosePathToExtract = new Button(this, SWT.NONE);
		choosePathToExtract.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					folderextraction = pathpasta.getSelectedFile();

				}
				if (folderextraction != null) {
					folderToExtract.setText(folderextraction.getPath());
					pathToExtract = folderextraction.getPath();

				}

			}
		});
		choosePathToExtract.setBounds(372, 35, 166, 28);
		choosePathToExtract.setText("Selecionar destino");
	}

	private boolean isValid(String text) {
		for (int i = 0; i < text.length(); i++)
			if (isNumber(text.charAt(i)))
				return true;
		return false;
	}

	// Avaliar se um determinado char é um número
	private boolean isNumber(char charAt) {
		return charAt == '0' || charAt == '1' || charAt == '2' || charAt == '3' || charAt == '4' || charAt == '5'
				|| charAt == '6' || charAt == '7' || charAt == '8' || charAt == '9';
	}

	// Deteção de CodeSmell Long Method
	private void evaluateLocMethod(Rule ruleReceived) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionLocMethod(ruleReceived, actualmetrics);
		createSecondaryGUI("IsLoc Method Class Detection", hasCodeSmellResult);
	}

	// Deteção de CodeSmell God Class, para a combinação de métricas WMC_Class e Nom_Class
	private void evaluateGodClassWithWMC_NOM(Rule ruleReceived) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_NOM(ruleReceived, actualmetrics);
		createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}

	// Deteção de CodeSmell God Class, para o combinação de métricas WMC_Class e LOC_Class
	private void evaluateGodClassWithWMC_LOC(Rule ruleReceived) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_LOC(ruleReceived, actualmetrics);
		createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}
	
	// Deteção de CodeSmell God Class, para o combinação de métricas NOM_Class e LOC_Class
	private void evaluateGodClassWithNOM_LOC(Rule ruleReceived) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionNOM_LOC(ruleReceived, actualmetrics);
		createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}

	// Deteção de CodeSmell God Class, para o combinação de métricas WMC_Class, NOM_Class e LOC_Class
	private void evaluateGodClassWithWMC_NOM_LOC(Rule ruleReceived) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_NOM_LOC(ruleReceived, actualmetrics);
		createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}

	//Criar GUI secundária, importar os resultados da aplicação da regra e lançar a GUI
	private void createSecondaryGUI(String name, ArrayList<HasCodeSmell> detectionResults) {
		SecondaryGUI codesmells = new SecondaryGUI(getDisplay(), name, detectionResults);
		codesmells.fillSecondaryGUI(detectionResults);
		codesmells.loadGUI();
	}
	
	// Consoante a regra selecionada, o método invocado para detetar CodeSmells é distinto
	private void evaluationChooser(String method1, String method2) {
		if (method1.equals("LOC_method")) {
			evaluateLocMethod(currentRule);
		}
		if (method1.equals("WMC_class") && method2.equals("NOM_class")
				&& !currentRule.getMethod3().contains("a")) {
			evaluateGodClassWithWMC_NOM(currentRule);
		}

		if (method1.equals("WMC_class") && method2.equals("LOC_class")
				&& !currentRule.getMethod3().contains("a")) {
			evaluateGodClassWithWMC_LOC(currentRule);
		}

		if (method1.equals("NOM_class") && method2.equals("LOC_class")
				&& !currentRule.getMethod3().contains("a")) {
			evaluateGodClassWithNOM_LOC(currentRule);
		}

		if (method1.equals("WMC_class") && currentRule.getMethod3().contains("a")) {
			evaluateGodClassWithWMC_NOM_LOC(currentRule);
		}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText(GUI_NAME);
		setSize(726, 861);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
