package gui;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import classes.HasCodeSmell;
import classes.NameByFile;
import classes.Rule;
import classes.Statistics;
import detection.EvaluateAndDetect;
import excel.ExcelManip;
import extraction.DirExplorer;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author Guy Turpin, Tomas Mendes, Vasco Fontoura,Rita Silva
 *
 */
public class mainGUI extends Shell {

	private static final String SELECT_SRC_MESSAGE = "Selecione a pasta 'src'";
	private static final String EXTRACT_PROJECT_MESSAGE = "Escolha um projeto e extraia métricas",
			ALREADY_EMPTY_MESSAGE = "Lista de regras já vazia", INVALID_FILE_MESSAGE = "Ficheiro inválido",
			EMPTY_RULE_LIST_MESSAGE = "Lista de regras vazia",
			INCORRECT_FIELDS = "Preencha corretamente todos os campos", NO_RULE_SELECTED = "Nenhuma regra selecionada",
			REPEATED_RULE = "Regra já imposta.", FIELDS_INCORRECT_MESSAGE = "Preencha corretamente todos os campos.",
			DEFAULT_LIMIT_TEXT = "Limite", INVALID_LIMITS = "Limites inválidos!",
			FOLDER_SELECTION_ERROR = "Escolha uma pasta", CYCLO_METHOD = "CYCLO_method",
			LOGO = "/G47/Grupo47/iscte_logo2.jpg", GUI_NAME = "Interface gráfica- Grupo 47";
	private static final String NO_METRICS_EXTRACTED_ERROR_MESSAGE = "Não foram extraidas métricas ou não existe nenhum projeto selecionado",
			NO_RULE_SELECTED_ERROR_MESSAGE = NO_RULE_SELECTED;
	private static final int NUMBER_OF_PACKAGES_INDEX = 3, NOM_INDEX = 1, NUMBER_OF_CLASSES_INDEX = 2, LOC_INDEX = 0;
	public static final String LOC_CLASS = "LOC_class", NOM_CLASS = "NOM_class", WMC_CLASS = "WMC_class",
			LOC_METHOD = "LOC_method";
	private EvaluateAndDetect evaluateAndDetect = new EvaluateAndDetect();
	private int rulesToShowSelectedIndex, index;
	private Menu menuWithButtons;
	private MenuItem guiInstructionsButton, metricInfoButton;
	private List excelfiles, listrulestoshow;
	private Text projectFolderPath, numOfClasses, numOfPackages, numOfMethods, numOfLines, firstLimit, secondLimit,
			thirdLimit, folderToSavePath;
	private Composite composite;
	private Button defineRuleButton, savehistory, projectSelection, extractMetricsButton, changeRuleButton,
			detectSmellsButton, loadHistoryButton, cleanHistoryList, viewFileButton, choosePathToExtract;
	private File selectedFile = null, folderextraction = null, history;
	private HashMap<String, ArrayList<String>> mapStats = new HashMap<>();
	private ArrayList<Rule> list = new ArrayList<Rule>();
	private ArrayList<File> fileList = new ArrayList<File>();
	private Rule rule, currentRule;
	private Combo thirdMetric, firstSignal, secondSignal, thirdSignal, secondOperator, firstMetric, secondMetric,
			firstOperator;
	private String ruleToShowInList, update;

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

	/**
	 * METODO QUE ADICIONA AS FUNCIONALIDADES A GUI (BOTOES, TEXTFIELDS ETC)
	 * 
	 * @param display
	 */
	private void addElements(Display display) {
		projectFolderPath = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		projectFolderPath.setBounds(10, 67, 345, 26);

		projectSelection = new Button(this, SWT.NONE);
		addProjectSelectionListener();
		projectSelection.setBounds(372, 67, 166, 28);
		projectSelection.setText("Selecionar projeto (src)");

		excelfiles = new List(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		excelfilesListener();

		excelfiles.setBounds(10, 108, 345, 164);

		composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 314, 670, 465);

		Text numOfMethodText = new Text(this, SWT.BORDER);
		numOfMethodText.setEditable(false);
		numOfMethodText.setText("Número de Métodos");
		numOfMethodText.setBounds(372, 198, 166, 26);

		numOfClasses = new Text(this, SWT.BORDER);
		numOfClasses.setEditable(false);
		numOfClasses.setBounds(544, 109, 136, 26);

		Text numOfClassesText = new Text(this, SWT.BORDER);
		numOfClassesText.setEditable(false);
		numOfClassesText.setText("Número de Classes");
		numOfClassesText.setBounds(372, 109, 166, 26);

		Text numOfLinesText = new Text(this, SWT.BORDER);
		numOfLinesText.setEditable(false);
		numOfLinesText.setText("Número de Linhas");
		numOfLinesText.setBounds(372, 246, 166, 26);

		Text numOfPackagesText = new Text(this, SWT.BORDER);
		numOfPackagesText.setEditable(false);
		numOfPackagesText.setText("Número de Packages");
		numOfPackagesText.setBounds(372, 152, 166, 26);

		numOfPackages = new Text(this, SWT.BORDER);
		numOfPackages.setEditable(false);
		numOfPackages.setBounds(544, 152, 136, 26);

		numOfMethods = new Text(this, SWT.BORDER);
		numOfMethods.setEditable(false);
		numOfMethods.setBounds(544, 198, 136, 26);

		numOfLines = new Text(this, SWT.BORDER);
		numOfLines.setEditable(false);
		numOfLines.setBounds(544, 246, 136, 26);

		extractMetricsButton = new Button(this, SWT.NONE);
		extractMetricsButton.setBounds(544, 48, 136, 30);
		extractMetricsListener();
		extractMetricsButton.setText("Extrair métricas");

		firstMetric = new Combo(composite, SWT.READ_ONLY);
		firstMetricListener();
		firstMetric.setBounds(10, 58, 155, 28);
		firstMetric.setText("");
		firstMetric.add(LOC_METHOD);
		firstMetric.add(WMC_CLASS);
		firstMetric.add(NOM_CLASS);

		firstOperator = new Combo(composite, SWT.READ_ONLY);
		firstOperator.setBounds(440, 58, 117, 28);
		firstOperator.setText("");
		firstOperator.add("OR");
		firstOperator.add("AND");

		secondMetric = new Combo(composite, SWT.READ_ONLY);
		secondMetricListener();
		secondMetric.setBounds(10, 92, 155, 28);
		secondMetric.setText("");

		thirdMetric = new Combo(composite, SWT.READ_ONLY);
		thirdMetric.setBounds(10, 126, 155, 28);
		thirdMetric.setText("");
		thirdMetric.add("");
		thirdMetric.add(LOC_CLASS);
		thirdMetric.setVisible(false);

		firstLimit = new Text(composite, SWT.BORDER);
		firstLimit.setBounds(313, 58, 94, 30);
		firstLimit.setText(DEFAULT_LIMIT_TEXT);

		secondLimit = new Text(composite, SWT.BORDER);
		secondLimit.setBounds(313, 92, 94, 28);
		secondLimit.setText(DEFAULT_LIMIT_TEXT);

		listrulestoshow = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		listRulesToShowListener();
		listrulestoshow.setLocation(10, 186);
		listrulestoshow.setSize(431, 230);

		defineRuleButton = new Button(composite, SWT.NONE);
		defineRuleButtonListener();
		defineRuleButton.setBounds(447, 185, 142, 30);
		defineRuleButton.setText("Definir regra");

		changeRuleButton = new Button(composite, SWT.NONE);
		changeRuleButtonListener();
		changeRuleButton.setBounds(447, 224, 145, 30);
		changeRuleButton.setText("Alterar regras");

		detectSmellsButton = new Button(composite, SWT.NONE);
		detectSmellsButtonListener();
		detectSmellsButton.setBounds(451, 422, 167, 30);
		detectSmellsButton.setText("Deteção de codesmells");

		loadHistoryButton = new Button(composite, SWT.NONE);
		loadHistoryButtonListener();
		loadHistoryButton.setBounds(10, 422, 212, 30);
		loadHistoryButton.setText("Carregar histórico de regras");

		Label savedRulesLabel = new Label(composite, SWT.NONE);
		savedRulesLabel.setBounds(10, 160, 155, 20);
		savedRulesLabel.setText("Regras guardadas:");

		Label defineRuleLabel = new Label(composite, SWT.NONE);
		defineRuleLabel.setText("Defina/altere uma regra para a deteção de codesmells: ");
		defineRuleLabel.setBounds(10, 32, 397, 20);

		savehistory = new Button(composite, SWT.NONE);
		saveHistoryListener();
		savehistory.setBounds(227, 422, 214, 30);
		savehistory.setText("Guardar histórico");

		firstSignal = new Combo(composite, SWT.READ_ONLY);
		firstSignal.setBounds(196, 58, 80, 28);
		firstSignal.add(">");
		firstSignal.add("<");

		secondOperator = new Combo(composite, SWT.READ_ONLY);
		secondOperator.setBounds(440, 92, 117, 28);
		secondOperator.setText("");
		secondOperator.add("OR");
		secondOperator.add("AND");
		secondOperator.add(" ");
		secondOperator.setVisible(false);

		thirdSignal = new Combo(composite, SWT.READ_ONLY);
		thirdSignal.setBounds(196, 126, 80, 28);
		thirdSignal.add(">");
		thirdSignal.add("<");
		thirdSignal.add(" ");
		thirdSignal.setVisible(false);

		thirdLimit = new Text(composite, SWT.BORDER);
		thirdLimit.setText(DEFAULT_LIMIT_TEXT);
		thirdLimit.setBounds(313, 126, 94, 28);
		thirdLimit.setVisible(false);

		secondSignal = new Combo(composite, SWT.READ_ONLY);
		secondSignal.setBounds(195, 92, 81, 28);
		secondSignal.add(">");
		secondSignal.add("<");

		cleanHistoryList = new Button(composite, SWT.NONE);
		cleanHistoryListListener();
		cleanHistoryList.setBounds(449, 267, 140, 30);
		cleanHistoryList.setText("Limpar lista");

		Label chooseProjectLabel = new Label(this, SWT.NONE);
		chooseProjectLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		chooseProjectLabel.setBounds(10, 9, 528, 20);
		chooseProjectLabel.setText("Escolha o projeto java que pretende analisar:");

		Menu helpMenu = new Menu(this, SWT.BAR);
		setMenuBar(helpMenu);

		MenuItem mntmHelp = new MenuItem(helpMenu, SWT.CASCADE);
		mntmHelp.setText("Ajuda");

		menuWithButtons = new Menu(mntmHelp);
		mntmHelp.setMenu(menuWithButtons);

		guiInstructionsButton = new MenuItem(menuWithButtons, SWT.NONE);
		guiInstructionsButtonListener();
		guiInstructionsButton.setText("Utilização da interface");

		metricInfoButton = new MenuItem(menuWithButtons, SWT.NONE);
		metricInfoButtonListener();
		metricInfoButton.setText("Informação sobre métricas");

		viewFileButton = new Button(this, SWT.NONE);
		viewFileButtonListener();
		viewFileButton.setBounds(10, 278, 109, 30);
		viewFileButton.setText("Ver ficheiro");

		folderToSavePath = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		folderToSavePath.setBounds(10, 35, 345, 26);

		choosePathToExtract = new Button(this, SWT.NONE);
		choosePathToExtractListener();
		choosePathToExtract.setBounds(372, 35, 166, 28);
		choosePathToExtract.setText("Selecionar destino");
	}

	/**
	 * METODO QUE PERMITE A SELEÇAO DO FICHEIRO EXCEL GERADO E A ESCRITA DAS
	 * CARACTERISTICAS DO MESMO
	 */
	private void excelfilesListener() {
		excelfiles.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				index = excelfiles.getSelectionIndex();
				if (index != -1) {
					changeSelectedFile();
					writeStatistics();
				}
			}
		});
	}

	/**
	 * METODO QUE VERIFICA SE A METRICA 3 SE ENCONTRA PREENCHIDA
	 * 
	 * @return true or false
	 */
	private boolean isThirdMetricEmpty() {
		if (thirdMetric.isVisible()) {
			return (thirdMetric.getText().isEmpty() && secondOperator.getText().isEmpty()
					&& (thirdLimit.getText().isEmpty() || thirdLimit.getText().equals(DEFAULT_LIMIT_TEXT))
					&& thirdSignal.getText().isEmpty());
		} else
			return true;
	}

	/**
	 * METODO QUE PERMITE A DEFINICAO DE REGRAS PREVIAMENTE CRIADAS PELO UTILIZADOR
	 * ATRAVES DA GUI
	 */
	private void defineRuleButtonListener() {
		defineRuleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isValid(firstLimit.getText()) || !isValid(secondLimit.getText())) {
					JOptionPane.showMessageDialog(null, INVALID_LIMITS);
				} else {
					boolean isRepeated = false;
					if (isFirstMetricCorrect() && isSecondMetricCorrect()) {
						isRepeated = createRuleAndVerify(isRepeated);
						checkExistanceAndAdd(isRepeated);
					} else
						JOptionPane.showMessageDialog(null, FIELDS_INCORRECT_MESSAGE);
				}
			}

		});
	}

	/**
	 * 
	 * METODO QUE PERMITE AO UTILIZADOR ALTERAR UMA REGRA PREVIAMENTE SELECIONADA
	 * 
	 */
	private void changeRuleButtonListener() {
		changeRuleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isValid(firstLimit.getText()) || !isValid(secondLimit.getText()))
					JOptionPane.showMessageDialog(null, INVALID_LIMITS);
				else {
					if (isFilledCorrectly()) {
						if (listrulestoshow.isSelected(rulesToShowSelectedIndex)) {
							list.get(rulesToShowSelectedIndex).setLimit1(firstLimit.getText());
							list.get(rulesToShowSelectedIndex).setLimit2(secondLimit.getText());
							list.get(rulesToShowSelectedIndex).setLimit3(thirdLimit.getText());
							list.get(rulesToShowSelectedIndex).setMethod1(firstMetric.getText());
							list.get(rulesToShowSelectedIndex).setMethod2(secondMetric.getText());
							list.get(rulesToShowSelectedIndex).setMethod3(thirdMetric.getText());
							list.get(rulesToShowSelectedIndex).setOperator(firstOperator.getText());
							list.get(rulesToShowSelectedIndex).setOperator2(secondOperator.getText());
							list.get(rulesToShowSelectedIndex).setSinal1(firstSignal.getText());
							list.get(rulesToShowSelectedIndex).setSinal2(secondSignal.getText());
							list.get(rulesToShowSelectedIndex).setSinal3(thirdSignal.getText());
							update = list.get(rulesToShowSelectedIndex).toString();
							updateRule();
							listrulestoshow.remove(rulesToShowSelectedIndex);
							listrulestoshow.add(update, rulesToShowSelectedIndex);
						} else
							JOptionPane.showMessageDialog(null, NO_RULE_SELECTED);
					} else
						JOptionPane.showMessageDialog(null, INCORRECT_FIELDS);
				}
			}
		});
	}

	/**
	 * METODO QUE PERMITE QUE O UTILIZADOR REALIZE A DETECAO DE CODESMELLS CONSOANTE
	 * A REGRA PREVIAMENTE SELECIONADA
	 * 
	 */
	private void detectSmellsButtonListener() {
		detectSmellsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Caso não tenham sido extraídas métricas de um projeto, mensagem de erro
				if (evaluateAndDetect.getActualmetrics() == null)
					JOptionPane.showMessageDialog(null, NO_METRICS_EXTRACTED_ERROR_MESSAGE);
				else {
					// Caso contrário a existencia de code smells no projeto é averiguada
					if (listrulestoshow.isSelected(rulesToShowSelectedIndex)) {
						// Apenas é averiguada caso exista uma regra selecionada
						String method1 = currentRule.getMethod1();
						String method2 = currentRule.getMethod2();
						HashMap<String, ArrayList<HasCodeSmell>> nameAndResults = evaluateAndDetect
								.evaluationChooser(currentRule);
						if (nameAndResults != null) {
							String name = nameAndResults.keySet().iterator().next();
							ArrayList<HasCodeSmell> results = nameAndResults.values().iterator().next();
							createSecondaryGUI(name, results);
						}
					} else
						JOptionPane.showMessageDialog(null, NO_RULE_SELECTED_ERROR_MESSAGE); // Caso contário é mostrada
																								// uma mensagem de erro
				}
			}

		});
	}

	/**
	 * METODO QUE PERMITE AO UTILIZADOR CARREGAR UM HISTORICO PARA A GUI E TRABALHAR
	 * CONSOANTE REGRAS DEFINIDAS A PRIORI
	 */
	private void loadHistoryButtonListener() {
		loadHistoryButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listrulestoshow.removeAll();
				list.clear();
				JFileChooser pathpasta = new JFileChooser(".txt");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				pathpasta.setFileFilter(filter);
				pathpasta.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
					history = pathpasta.getSelectedFile();
				if (history != null) {
					if (history.getPath().endsWith(".txt")) {
						try {
							readAndGetRules();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, INVALID_FILE_MESSAGE);
					}
				}

			}

		});
	}

	/**
	 * METODO QUE PERMITE AO UTILIZADOR GUARDAR, NUM FICHEIRO NAO VOLATIL, AS REGRAS
	 * DEFINIDAS. ESTE METODO PERMITE TAMBEM A CRIACAO DO FICHEIRO HISTORICO DE RAIZ
	 * 
	 */
	private void saveHistoryListener() {
		savehistory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!list.isEmpty()) {
					SaveHistoryGUI hist = new SaveHistoryGUI(getDisplay(), listrulestoshow, list);
					hist.loadGUI();
				} else
					JOptionPane.showMessageDialog(null, EMPTY_RULE_LIST_MESSAGE);
			}
		});
	}

	/**
	 * METODO QUE PERMITE AO UTILIZADOR ESVAZIAR A LISTA DAS REGRAS
	 * 
	 */
	private void cleanHistoryListListener() {
		cleanHistoryList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (listrulestoshow.getItemCount() != 0)
					listrulestoshow.removeAll();
				else
					JOptionPane.showMessageDialog(null, ALREADY_EMPTY_MESSAGE);
			}
		});
	}

	private void guiInstructionsButtonListener() {
		guiInstructionsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpInterface helpinterface = new HelpInterface(getDisplay());
				helpinterface.loadGUI();
			}
		});
	}

	/**
	 * METODO QUE PERMITE AO UTILIZADOR CONSULTAR INFORMACOES RELATIVAS AS METRICAS
	 * E CONSEQUENTEMENTE AO SEU FUNCIONAMENTO
	 * 
	 */
	private void metricInfoButtonListener() {
		metricInfoButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpGUIMetrics hgm = new HelpGUIMetrics(getDisplay());
				hgm.loadGUI();
			}
		});
	}

	/**
	 * METODO QUE PERMITE AO UTILIZADOR VISUALIZAR O FICHEIRO EXCEL GERADO PELA
	 * EXTRACAO
	 * 
	 */
	private void viewFileButtonListener() {
		viewFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (evaluateAndDetect.getActualmetrics() != null) {
					ShowExcelGUI showGUI = new ShowExcelGUI(getDisplay(), "Visualizar ficheiro",
							evaluateAndDetect.getActualmetrics());
					showGUI.loadGUI();
				} else
					JOptionPane.showMessageDialog(null, EXTRACT_PROJECT_MESSAGE);

			}
		});
	}

	private void choosePathToExtractListener() {
		choosePathToExtract.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
					folderextraction = pathpasta.getSelectedFile();
				if (folderextraction != null) {
					folderToSavePath.setText(folderextraction.getPath());
					folderextraction.getPath();
				}

			}
		});
	}

	private void addProjectSelectionListener() {
		projectSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				File choosenFile = null;
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					choosenFile = pathpasta.getSelectedFile();
					String path = choosenFile.getAbsolutePath();
					if (!path.contains("src"))
						JOptionPane.showMessageDialog(null, SELECT_SRC_MESSAGE);
					else
						fileList.add(choosenFile);

				}
				if (choosenFile != null && choosenFile.getAbsolutePath().contains("src")) {
					projectFolderPath.setText(choosenFile.getPath());
					selectedFile = choosenFile;
				}

			}
		});
	}

	private void extractMetricsListener() {
		extractMetricsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedFile == null || folderextraction == null)
					JOptionPane.showMessageDialog(null, FOLDER_SELECTION_ERROR);
				else {
					try {
						extractAndAddFileToList();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private void firstMetricListener() {
		firstMetric.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (firstMetric.getSelectionIndex() != -1)
					changeSecondMetricOptions();
			}
		});
	}

	private void secondMetricListener() {
		secondMetric.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int secondMetricSelected = secondMetric.getSelectionIndex();
				if (secondMetricSelected != -1) {
					if (secondMetric.getItem(secondMetricSelected).equals(LOC_CLASS))
						setThirdMetricVisible(false);
					else if (secondMetric.getItem(secondMetricSelected).equals(NOM_CLASS))
						setThirdMetricVisible(true);
				}
			}
		});
	}

	private void listRulesToShowListener() {
		listrulestoshow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rulesToShowSelectedIndex = listrulestoshow.getSelectionIndex();
				currentRule = list.get(rulesToShowSelectedIndex);
				selectFirstMetricByRule();
				changeSecondMetricOptions();
				selectSecondMetricByRule();
				if (!currentRule.getLimit3().isEmpty()) {
					setThirdMetricVisible(true);
					selectThirdMetricByRule();
				} else {
					setThirdMetricVisible(false);
					thirdSignal.deselectAll();
					secondOperator.deselectAll();
					thirdMetric.deselectAll();
					thirdLimit.clearSelection();
				}
				fillWithRule();
			}

		});
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

	private void changeSelectedFile() {
		for (File file : fileList) {
			NameByFile excelNameByFile = new NameByFile();
			excelNameByFile.setFileToExtract(file);
			String fileName = excelNameByFile.getFileName();
			if (fileName.equals(excelfiles.getItem(index)))
				selectedFile = file;
		}
	}

	private void writeStatistics() {
		for (Entry<String, ArrayList<String>> entry : mapStats.entrySet()) {
			if (entry.getKey().equals(excelfiles.getItem(index))) {
				ArrayList<String> statsToWrite = entry.getValue();
				numOfLines.setText(statsToWrite.get(LOC_INDEX));
				numOfClasses.setText(statsToWrite.get(NUMBER_OF_CLASSES_INDEX));
				numOfMethods.setText(statsToWrite.get(NOM_INDEX));
				numOfPackages.setText(statsToWrite.get(NUMBER_OF_PACKAGES_INDEX));
				DirExplorer dirEx = new DirExplorer(selectedFile);
				try {
					evaluateAndDetect.setActualmetrics(dirEx.exploreAndExtract());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private ArrayList<String> createStatsList() {
		Statistics stats = new Statistics(evaluateAndDetect.getActualmetrics());
		ArrayList<String> StringStats = new ArrayList<>();
		StringStats.add(String.valueOf(stats.countLinesOfCode()));
		StringStats.add(String.valueOf(stats.countNumberOfMethods()));
		StringStats.add(String.valueOf(stats.countClasses()));
		StringStats.add(String.valueOf(stats.countPackages()));
		return StringStats;
	}

	private void extractAndAddFileToList() throws IOException {
		DirExplorer dirEx = new DirExplorer(selectedFile);
		if (folderextraction.exists() && selectedFile.exists()) {
			NameByFile excelFileName = new NameByFile();
			excelFileName.setFileToExtract(selectedFile);
			String fileName = excelFileName.getFileName();
			evaluateAndDetect.setActualmetrics(dirEx.exploreAndExtract());
			ExcelManip em = new ExcelManip(selectedFile);
			em.createExcel(evaluateAndDetect.getActualmetrics(), folderToSavePath.getText());
			ArrayList<String> statsList = createStatsList();
			mapStats.put(fileName, statsList);
			excelfiles.add(fileName);
		}
	}

	private void setThirdMetricVisible(boolean visible) {
		secondOperator.setVisible(visible);
		thirdSignal.setVisible(visible);
		thirdMetric.setVisible(visible);
		thirdLimit.setVisible(visible);
	}

	private void changeSecondMetricOptions() {
		int firstMethodSelection = firstMetric.getSelectionIndex();
		if (firstMetric.getItem(firstMethodSelection).equals(LOC_METHOD)) {
			setThirdMetricVisible(false);
			secondMetric.removeAll();
			secondMetric.add(CYCLO_METHOD);
		} else if (firstMetric.getItem(firstMethodSelection).equals(WMC_CLASS)) {
			setThirdMetricVisible(true);
			secondMetric.removeAll();
			secondMetric.add(NOM_CLASS);
			secondMetric.add(LOC_CLASS);
		} else if (firstMetric.getItem(firstMethodSelection).equals(NOM_CLASS)) {
			setThirdMetricVisible(false);
			secondMetric.removeAll();
			secondMetric.add(LOC_CLASS);
		}
	}

	private void selectFirstMetricByRule() {
		for (int arrayIndex = 0; arrayIndex < firstMetric.getItemCount(); arrayIndex++)
			if (firstMetric.getItem(arrayIndex).contentEquals(currentRule.getMethod1()))
				firstMetric.select(arrayIndex);
	}

	private void selectSecondMetricByRule() {
		for (int k = 0; k < secondMetric.getItemCount(); k++)
			if (secondMetric.getItem(k).contentEquals(currentRule.getMethod2()))
				secondMetric.select(k);
	}

	private void selectThirdMetricByRule() {
		for (int z = 0; z < thirdMetric.getItemCount(); z++)
			if (thirdMetric.getItem(z).contentEquals(currentRule.getMethod3()))
				thirdMetric.select(z);
	}

	private void fillWithRule() {
		firstSignal.setText(currentRule.getSinal1());
		firstLimit.setText(currentRule.getLimit1());
		firstOperator.setText(currentRule.getOperator());
		secondSignal.setText(currentRule.getSinal2());
		secondLimit.setText(currentRule.getLimit2());
		secondOperator.setText(currentRule.getOperator2());
		thirdSignal.setText(currentRule.getSinal3());
		thirdLimit.setText(currentRule.getLimit3());
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

	// Criar GUI secundária, importar os resultados da aplicação da regra e lançar a
	// GUI
	public void createSecondaryGUI(String name, ArrayList<HasCodeSmell> detectionResults) {
		SecondaryGUI codesmells = new SecondaryGUI(getDisplay(), name, detectionResults);
		codesmells.loadGUI();
	}

	private boolean isSecondMetricCorrect() {
		return !secondMetric.getText().isEmpty() && !secondLimit.getText().isEmpty();
	}

	private boolean isFirstMetricCorrect() {
		return !firstMetric.getText().isEmpty() && !firstOperator.getText().isEmpty()
				&& !firstLimit.getText().isEmpty();
	}

	private boolean isThirdMetricFilled() {
		if (thirdMetric.isVisible())
			return !thirdMetric.getText().isEmpty() && !secondOperator.getText().isEmpty()
					&& isValid(thirdLimit.getText()) && !thirdSignal.getText().isEmpty();
		else
			return false;
	}

	private boolean verifyRuleExistance(boolean v) {
		ruleToShowInList = rule.toString();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().contentEquals(rule.toString())) {
				JOptionPane.showMessageDialog(null, REPEATED_RULE);
				v = true;
				break;

			}
		}
		return v;
	}

	private void checkExistanceAndAdd(boolean isRepeated) {
		if (isRepeated == false) {
			listrulestoshow.add(ruleToShowInList);
			list.add(rule);
		}
	}

	/**
	 * METODO QUE CRIA O OBJETO RULE, APOS A VERIFICAÇAO DA EXISTENCIA OU NAO DO
	 * MESMO
	 * 
	 * @param isRepeated
	 * @return
	 */
	private boolean createRuleAndVerify(boolean isRepeated) {
		if (isThirdMetricFilled()) {
			rule = new Rule(firstMetric.getText(), firstSignal.getText(), firstLimit.getText(), firstOperator.getText(),
					secondMetric.getText(), secondSignal.getText(), secondLimit.getText(), secondOperator.getText(),
					thirdMetric.getText(), thirdSignal.getText(), thirdLimit.getText());
			isRepeated = verifyRuleExistance(isRepeated);
		} else {
			if (isThirdMetricEmpty()) {
				rule = new Rule(firstMetric.getText(), firstSignal.getText(), firstLimit.getText(),
						firstOperator.getText(), secondMetric.getText(), secondSignal.getText(), secondLimit.getText(),
						"", "", "", "");
				isRepeated = verifyRuleExistance(isRepeated);
			} else
				JOptionPane.showMessageDialog(null, FIELDS_INCORRECT_MESSAGE);
		}
		return isRepeated;
	}

	private boolean isFilledCorrectly() {
		return isFirstMetricCorrect() && isSecondMetricCorrect() && (isThirdMetricFilled() || isThirdMetricEmpty());
	}

	/**
	 * METODO QUE, CONSOANTE A REGRA ESCOLHIDA, REMOVE E SUBSTITUI POR UMA NOVA
	 */
	private void updateRule() {
		for (int x = 0; x < list.size(); x++) {
			if (x == rulesToShowSelectedIndex) {
				listrulestoshow.remove(x);
				listrulestoshow.add(update, x);
			}
		}
	}

	/**
	 * METODO QUE ADICIONA UMA NOVA REGRA A LISTA DE REGRAS DEFINIDAS PELO
	 * UTILIZADOR
	 * 
	 * @param line
	 * @param ruleInHistory
	 */
	private void addRule(String line, Rule ruleInHistory) {
		list.add(ruleInHistory);
		listrulestoshow.add(line);
	}

	/**
	 * METODO QUE LÊ UM FICHEIRO HISTORICO DO TIPO .TXT E ESCREVE AS REGRAS CONTIDAS
	 * NO MESMO, NA GUI
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void readAndGetRules() throws FileNotFoundException, IOException {
		FileReader reader = new FileReader(new File(history.getPath()));
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			String[] rules = line.split(" ");
			if (rules.length > 9) {
				Rule ruleInHistory = new Rule(rules[0], rules[1], rules[2], rules[3], rules[4], rules[5], rules[6],
						rules[7], rules[8], rules[9], rules[10]);
				addRule(line, ruleInHistory);
			} else {
				Rule ruleInHistory = new Rule(rules[0], rules[1], rules[2], rules[3], rules[4], rules[5], rules[6], "",
						"", "", "");
				addRule(line, ruleInHistory);
			}

		}
		reader.close();
	}
}
