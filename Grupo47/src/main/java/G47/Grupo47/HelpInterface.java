package G47.Grupo47;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class HelpInterface extends Shell {
	
	private Display display;

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			HelpInterface shell = new HelpInterface(display);
//			shell.open();
//			shell.layout();
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch()) {
//					display.sleep();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public HelpInterface(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label extrairm = new Label(this, SWT.NONE | SWT.BORDER | SWT.WRAP);
		extrairm.setBounds(10, 21, 870, 48);
		extrairm.setText("1-Selecione a pasta onde se localiza o projeto do qual pretende extrair métricas, clicando no botão 'Selecionar pasta'.De seguida, pressione o botão 'Extrair métricas', do qual resultará um ficheiro com as respetivas métricas calculadas.");
		
		Label resultadosext = new Label(this, SWT.BORDER | SWT.WRAP);
		resultadosext.setBounds(10, 75, 870, 66);
		resultadosext.setText("2-Pode agora verificar que, na lista centrada à esqueda da interface, foi gerada uma lista com o nome do(s) ficheiro(s) excel gerado(s).Ao clicar sobre esse ficheiro, poderá verificar as características gerais do mesmo, nos campos localizados à direita da interface. ");
		
		Label regras = new Label(this, SWT.BORDER | SWT.WRAP);
		regras.setBounds(10, 157, 870, 66);
		regras.setText("3-Após este processo de extração, poderá agora definir regras para a deteção de codesmells. Para tal, preencha os campos que se encontram vazios de forma válida e coerente, e de seguida pressione o botão 'Definir regra'. De recordar que não há um limite de regras por definir.");
		
		
		
		
		createContents();
	}
	
	public void loadGUI () {
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
		setText("Ajuda-Utilização da interface");
		setSize(934, 496);

	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
