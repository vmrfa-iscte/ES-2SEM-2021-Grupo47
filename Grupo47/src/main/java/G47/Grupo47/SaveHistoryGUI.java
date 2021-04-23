package G47.Grupo47;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SaveHistoryGUI extends Shell {

	private ArrayList<Rules> rules;
	private List ruleslist;
	private Display display;
	private Text submitedFileName;
	private Text historyFile;
	private File file;
	private Text finaldirectoy;
	private File rules2;
	private File filecreated;

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
	//CONSTRUTOR DA GUI RELATIVA AO BOTÃO "GUARDAR HISTÓRICO"
	public SaveHistoryGUI(Display display, List regras, ArrayList<Rules> rules) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(SaveHistoryGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		this.ruleslist = regras;
		this.rules = rules;

		Label lblDefinaONome = new Label(this, SWT.NONE);
		lblDefinaONome.setBounds(10, 37, 185, 20);
		lblDefinaONome.setText("Defina o nome do ficheiro:");

		submitedFileName = new Text(this, SWT.BORDER); //TEXTFIELD QUE PERMITE AO UTILIZADOR DEFINIR O NOME DO FICHEIRO HISTÓRICO;
		submitedFileName.setBounds(211, 34, 211, 26);

		//JFILECHOOSER QUE PERMITE AO UTILIZADOR SELECIONAR O DESTINO/DIRETORIA DO FICHEIRO A CRIAR;
		Button pathpasta = new Button(this, SWT.NONE); 
		pathpasta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					file = pathpasta.getSelectedFile();

				}
				if (!submitedFileName.getText().isEmpty()) {
					finaldirectoy.setText(file.getPath() + "\\" + submitedFileName.getText() + ".txt"); //CRIAÇÃO DA DIRETORIA FINAL;
				} else {
					JOptionPane.showMessageDialog(null, "Preencha o campo 'Nome do ficheiro'");
				}
			}
		});
		pathpasta.setBounds(10, 71, 185, 30);
		pathpasta.setText("Selecione a pasta destino");

		finaldirectoy = new Text(this, SWT.BORDER);
		finaldirectoy.setBounds(211, 73, 211, 26);

		Label lblSeAPasta = new Label(this, SWT.NONE);
		lblSeAPasta.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblSeAPasta.setBounds(10, 147, 252, 20);
		lblSeAPasta.setText("Após criação/Se o ficheiro já existir:");

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblNewLabel.setBounds(10, 11, 101, 20);
		lblNewLabel.setText("Criar ficheiro");

		historyFile = new Text(this, SWT.BORDER);
		historyFile.setBounds(10, 184, 236, 26);

		Button choosenHistoryFile = new Button(this, SWT.NONE);
		choosenHistoryFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta2 = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				pathpasta2.setFileFilter(filter);
				pathpasta2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnValue = pathpasta2.showOpenDialog(null);
				String apath = new String();
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					filecreated = pathpasta2.getSelectedFile();
					apath = filecreated.getPath();

				}
				historyFile.setText(apath);
				if (!historyFile.getText().isEmpty()) {
					if (!rules.isEmpty()) {
						for (int x = 0; x < rules.size(); x++) {//CICLO QUE ESCREVE TODAS AS REGRAS DA LISTA N FICHEIRO .TXT

							try {
								FileWriter fw = new FileWriter(new File(historyFile.getText()), true);
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(rules.get(x).toString());
								bw.newLine();
								bw.close();

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						JOptionPane.showMessageDialog(null, "Histórico guardado com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null, "Lista de regras vazia, defina regras");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Selecione o ficheiro destino");
				}

			}
		});
		choosenHistoryFile.setBounds(265, 182, 157, 30);
		choosenHistoryFile.setText("Selecionar ficheiro ");

		//CRIAÇÃO DO FICHEIRO HISTÓRICO, APÓS A DEFINIÇÃO DA SUA DIRETORIA E RESPETIVO NOME;
		Button btnCreateFile = new Button(this, SWT.NONE);
		btnCreateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!finaldirectoy.getText().isEmpty() && !submitedFileName.getText().isEmpty()) {
					File f = new File(finaldirectoy.getText());
					System.out.println(f.getPath());
					if (!f.exists()) {
						try {
							f.createNewFile();
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
		btnCreateFile.setBounds(237, 113, 185, 30);
		btnCreateFile.setText("Criar ficheiro 'historico'");
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
		createContents();
	}

	public void loadGUI() {

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
