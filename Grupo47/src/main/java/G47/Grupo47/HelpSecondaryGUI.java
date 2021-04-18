package G47.Grupo47;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class HelpSecondaryGUI extends Shell {
	
	private Display display;
	
	public HelpSecondaryGUI(Display display) {
		setSize(876, 377);
		setText("Ajuda");
		setImage(SWTResourceManager.getImage(HelpSecondaryGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		this.display = display;
		
		Group grpUtilizaoDaGui = new Group(this, SWT.NONE);
		grpUtilizaoDaGui.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD | SWT.ITALIC));
		grpUtilizaoDaGui.setText("Utilização da GUI");
		grpUtilizaoDaGui.setBounds(10, 0, 838, 323);
		
		Label lblCarregar = new Label(grpUtilizaoDaGui, SWT.BORDER | SWT.WRAP);
		lblCarregar.setBounds(10, 41, 818, 72);
		lblCarregar.setText("1 - Carregar em \"Selecionar ficheiro\", ao aparecer uma nova janela deverá ser escolhido um ficheiro excel (.xlsx) que contenha a formatacao correta (Method id, nome do método, nome da classe, nome do pacote e detação de code smells isLong_method e isGod_class)");
		
		Label lblAps = new Label(grpUtilizaoDaGui, SWT.BORDER | SWT.WRAP);
		lblAps.setText("2 - Após selecionado o ficheiro excel com a formatação correta deverá carregar em \"Avaliar codesmell\", este botão fará a comparação da tabela presente na GUI com a deteção do code smell em causa no ficheiro escolhido. \r\n\r\n\t- Depois de feita a comparação a tabela na GUI terá a última coluna (Qualidade) preenchida com os resultados.\r\n\r\n\t- Para além da tabela, aparecerá uma outra janela que contém um gráfico circular (Pie Chart) que tem como finalidade demonstrar os mesmos resultados da tabela mas de forma gráfica. O gráfico permite diferenciar a quantidade de Falsos Positivos, Falsos Negativos, Verdadeiros positivos e Verdadeiros Negativos.\r\n");
		lblAps.setBounds(10, 129, 818, 176);
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
		setSize(934, 558);

	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
