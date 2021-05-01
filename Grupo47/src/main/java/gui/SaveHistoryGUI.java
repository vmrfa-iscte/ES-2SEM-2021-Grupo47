package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import classes.Rule;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


/**
 * @author Guy Turpin
 * @author Rita Silva
 * @version 3
 *
 */
public class SaveHistoryGUI extends Shell {

	private static final String SELECT_DESTINATION_MESSAGE = "Selecione o ficheiro destino";
	private static final String EMPTY_RULE_LIST_MESSAGE = "Lista de regras vazia, defina regras";
	private static final String SUCESS_MESSAGE = "Histórico guardado com sucesso!";
	private static final String EMPTY_FIELD_MESSAGE = "Preencha o campo 'Nome do ficheiro'";
	private ArrayList<Rule> rules;
	private Display display;
	private Text submitedFileName;
	private Text historyFilePath;
	private File file;
	private Text destinationPath;
	private File filecreated;
	private Button selectedDestinationFolder;
	private Button chooseHistoryFile;
	private Button btnCreateFile;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	/**
	 * Create the shell.
	 * 
	 * @param display display
	 * @param regras uma dada lista
	 * @param rules uma dada lista de regras
	 */
	public SaveHistoryGUI(Display display, List regras, ArrayList<Rule> rules) {
		//CONSTRUTOR DA GUI RELATIVA AO BOTÃO "GUARDAR HISTÓRICO"
//		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(SaveHistoryGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		this.rules = rules;
		this.display=display;

		createContents();
		addElements();
	}
	
	/**
	 * ADICIONA CONTEUDO A GUI
	 */
	public void addElements() {
		Label defineFileNameLabel = new Label(this, SWT.NONE);
		defineFileNameLabel.setBounds(10, 37, 185, 20);
		defineFileNameLabel.setText("Defina o nome do ficheiro:");

		submitedFileName = new Text(this, SWT.BORDER); //TEXTFIELD QUE PERMITE AO UTILIZADOR DEFINIR O NOME DO FICHEIRO HISTÓRICO;
		submitedFileName.setBounds(211, 34, 211, 26);

		selectedDestinationFolder = new Button(this, SWT.NONE); 
		selectedDestinationFolderListener();
		selectedDestinationFolder.setBounds(10, 71, 185, 30);
		selectedDestinationFolder.setText("Selecione a pasta destino");

		destinationPath = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		destinationPath.setBounds(211, 73, 211, 26);

		Label ifFileExistsLabel = new Label(this, SWT.NONE);
		ifFileExistsLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		ifFileExistsLabel.setBounds(10, 147, 252, 20);
		ifFileExistsLabel.setText("Após criação/Se o ficheiro já existir:");

		Label createFileLabel = new Label(this, SWT.NONE);
		createFileLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		createFileLabel.setBounds(10, 11, 101, 20);
		createFileLabel.setText("Criar ficheiro");

		historyFilePath = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		historyFilePath.setBounds(10, 184, 236, 26);

		chooseHistoryFile = new Button(this, SWT.NONE);
		chooseHistoryFileListener();
		chooseHistoryFile.setBounds(265, 182, 157, 30);
		chooseHistoryFile.setText("Selecionar ficheiro ");

		btnCreateFile = new Button(this, SWT.NONE);
		btnCreateFileListener();
		btnCreateFile.setBounds(237, 113, 185, 30);
		btnCreateFile.setText("Criar ficheiro 'historico'");
	}

	/**
	 * Cria um ficheiro histórico do tipo .txt
	 */
	private void btnCreateFileListener() {
		btnCreateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!destinationPath.getText().isEmpty() && !submitedFileName.getText().isEmpty()) {
					File f = new File(destinationPath.getText());
					if (!f.exists()) {
						try {
							f.createNewFile();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}
			}
		});
	}

	/**
	 * METODO QUE PERMITE A ESCOLHA DO FICHEIRO DESTINO DAS REGRAS DEFINIDAS E PASSAGEM DESTAS PARA O MESMO
	 */
	private void chooseHistoryFileListener() {
		chooseHistoryFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser fileLocation = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fileLocation.setFileFilter(filter);
				fileLocation.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnValue = fileLocation.showOpenDialog(null);
				String filePath = new String();
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					filecreated = fileLocation.getSelectedFile();
					filePath = filecreated.getPath();
				}
				historyFilePath.setText(filePath);
				writeHistory();
			}
		});
	}


	/**
	 * CRIAÇAO DA DIRETORIA FINAL (PATH + NOME DO FICHEIRO)
	 */
	private void selectedDestinationFolderListener() {
		selectedDestinationFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					file = pathpasta.getSelectedFile();

				}
				if (!submitedFileName.getText().isEmpty()) {
					destinationPath.setText(file.getPath() + "\\" + submitedFileName.getText() + ".txt"); //CRIAÇÃO DA DIRETORIA FINAL;
				} else {
					JOptionPane.showMessageDialog(null, EMPTY_FIELD_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * ESCREVE NO FICHEIRO .TXT A LISTA DE REGRAS
	 */
	private void writeHistory() {
		if (!historyFilePath.getText().isEmpty()) {
			if (!rules.isEmpty()) {
				for (int x = 0; x < rules.size(); x++) {//CICLO QUE ESCREVE TODAS AS REGRAS DA LISTA N FICHEIRO .TXT

					try {
						FileWriter fw = new FileWriter(new File(historyFilePath.getText()), true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(rules.get(x).toString());
						bw.newLine();
						bw.close();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, SUCESS_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, EMPTY_RULE_LIST_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, SELECT_DESTINATION_MESSAGE);
		}
	}

	
	/**
	 * ABERTURA DA GUI
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
	protected void createContents() {
		setText("Histórico");
		setSize(464, 291);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
