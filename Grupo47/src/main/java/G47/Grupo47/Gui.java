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

public class Gui extends Shell {

	private int indice;
	private List ficheirosexcel;
	private Text foldername;
	private StyledText styledText;
	private Text text;

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
		text.setBounds(361, 58, 274, 188);

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
		pasta.setBounds(361, 12, 131, 30);
		pasta.setText("Selecionar pasta");

		ficheirosexcel = new List(this, SWT.BORDER);
		ficheirosexcel.addSelectionListener(new SelectionAdapter() {
			public void valueSelected(ListSelectionEvent e) {
				indice = ficheirosexcel.getSelectionIndex();
				if (!e.getValueIsAdjusting()) {
					if (indice != -1) {
						//preencher lista da direita com as caracteristicas gerais do projeto
						
						
						
					}
				}
			}
		});
		ficheirosexcel.setBounds(10, 58, 345, 188);

		Button extrair = new Button(this, SWT.NONE);
		extrair.setBounds(498, 12, 137, 30);
		extrair.setText("Extrair métricas");

		// dar ação de mandar o diretorio para a main do VASCO E ALIN

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("GUI-Grupo 47");
		setSize(663, 413);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
