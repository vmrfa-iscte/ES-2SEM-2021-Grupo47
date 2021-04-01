package G47.Grupo47;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;

public class Gui extends Shell {

	private int indice;
	private List ficheirosexcel;
	private Text foldername;
	private StyledText styledText;
	private Text text;
	private Composite composite;
	private Button btnDefinirRegras;
	private Text limite_1;
	private Text limite_2;
	private Text codesmells;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Gui shell = new Gui(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public Gui(Display display) {
		super(display, SWT.SHELL_TRIM);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(null);

		foldername = new Text(this, SWT.BORDER);
		foldername.setBounds(10, 14, 345, 26);

		text = new Text(this, SWT.BORDER);
		text.setBounds(372, 58, 351, 187);

		Button pasta = new Button(this, SWT.NONE);
		pasta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser pathpasta = new JFileChooser(".");
				String nomepath = new String();
				File selectedFile1 = null;
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
		ficheirosexcel.addSelectionListener(new SelectionAdapter() {
			public void valueSelected(ListSelectionEvent e) {
				indice = ficheirosexcel.getSelectionIndex();
				if (!e.getValueIsAdjusting()) {
					if (indice != -1) {
						// text.setText("numero total de packages:");
						// preencher lista da direita com as caracteristicas gerais do projeto de acordo
						// com o ficheiro selecionado ( indice )

					}
				}
			}
		});
		ficheirosexcel.setBounds(10, 57, 345, 188);

		Button extrair = new Button(this, SWT.NONE);
		extrair.setBounds(544, 12, 179, 30);
		extrair.setText("Extrair métricas");

		composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 251, 390, 156);

//		if (ficheirosexcel.getSize() != null) {
		Combo metrica1 = new Combo(composite, SWT.NONE);
		metrica1.setBounds(10, 20, 164, 28);

		Combo operador = new Combo(composite, SWT.NONE);
		operador.setBounds(10, 72, 74, 30);
		operador.add("OR");
		operador.add("AND");

		Combo metrica2 = new Combo(composite, SWT.NONE);
		metrica2.setBounds(10, 118, 164, 28);

		limite_1 = new Text(composite, SWT.BORDER);
		limite_1.setBounds(186, 20, 74, 30);
		limite_1.setText("Limite 1");

		limite_2 = new Text(composite, SWT.BORDER);
		limite_2.setBounds(186, 116, 74, 30);
		limite_2.setText("Limite 2");

//			codesmells = new Text(this, SWT.BORDER);
//			codesmells.setBounds(406, 251, 317, 156);

		btnDefinirRegras = new Button(composite, SWT.NONE);
		btnDefinirRegras.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

		btnDefinirRegras.setBounds(264, 70, 115, 30);
		btnDefinirRegras.setText("Definir regra");

//		}

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("GUI-Grupo 47");
		setSize(751, 464);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
