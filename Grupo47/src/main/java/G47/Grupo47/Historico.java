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

public class Historico extends Shell {

	private ArrayList<Rules> rules;
	private List regras;
	private Display display;
	private Text nomef;
	private Text nomef2;
	private File ficheiro;
	private Text nomep;
	private File rules2;
	private File ficheirocriado;

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
	public Historico(Display display, List regras, ArrayList<Rules> rules) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(Historico.class, "/G47/Grupo47/iscte_logo2.jpg"));
		this.regras = regras;
		this.rules = rules;

		Label lblDefinaONome = new Label(this, SWT.NONE);
		lblDefinaONome.setBounds(10, 37, 185, 20);
		lblDefinaONome.setText("Defina o nome do ficheiro:");

		nomef = new Text(this, SWT.BORDER);
		nomef.setBounds(211, 34, 211, 26);

		Button pathpasta = new Button(this, SWT.NONE);
		pathpasta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				pathpasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = pathpasta.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					ficheiro = pathpasta.getSelectedFile();

				}
				if (!nomef.getText().isEmpty()) {
					nomep.setText(ficheiro.getPath() + "\\" + nomef.getText() + ".txt");
				} else {
					JOptionPane.showMessageDialog(null, "Preencha o campo 'Nome do ficheiro'");
				}
			}
		});
		pathpasta.setBounds(10, 71, 185, 30);
		pathpasta.setText("Selecione a pasta destino");

		nomep = new Text(this, SWT.BORDER);
		nomep.setBounds(211, 73, 211, 26);

		Label lblSeAPasta = new Label(this, SWT.NONE);
		lblSeAPasta.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblSeAPasta.setBounds(10, 147, 252, 20);
		lblSeAPasta.setText("Após criação/Se o ficheiro já existir:");

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblNewLabel.setBounds(10, 11, 101, 20);
		lblNewLabel.setText("Criar ficheiro");

		nomef2 = new Text(this, SWT.BORDER);
		nomef2.setBounds(10, 184, 236, 26);

		Button pathpasta2 = new Button(this, SWT.NONE);
		pathpasta2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta2 = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				pathpasta2.setFileFilter(filter);
				pathpasta2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnValue = pathpasta2.showOpenDialog(null);
				String apath = new String();
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					ficheirocriado = pathpasta2.getSelectedFile();
					apath = ficheirocriado.getPath();

				}
				nomef2.setText(apath);
				if (!nomef2.getText().isEmpty()) {
					if (!rules.isEmpty()) {
						for (int x = 0; x < rules.size(); x++) {

							try {
								FileWriter fw = new FileWriter(new File(nomef2.getText()), true);
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
		pathpasta2.setBounds(265, 182, 157, 30);
		pathpasta2.setText("Selecionar ficheiro ");

		Button btnCriarFicheiro = new Button(this, SWT.NONE);
		btnCriarFicheiro.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!nomep.getText().isEmpty() && !nomef.getText().isEmpty()) {
					File f = new File(nomep.getText());
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
		btnCriarFicheiro.setBounds(237, 113, 185, 30);
		btnCriarFicheiro.setText("Criar ficheiro 'historico'");
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
