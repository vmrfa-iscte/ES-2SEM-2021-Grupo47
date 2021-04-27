package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class HelpSecondaryGUI extends Shell {
	
	private Display display;
	
	public HelpSecondaryGUI(Display display) {
		// Definir tamanho, nome e imagem da GUI
		this.display = display;
		setSize(876, 377);
		setText("Ajuda-Utilização da interface");
		setImage(SWTResourceManager.getImage(HelpSecondaryGUI.class, "/G47/Grupo47/iscte_logo2.jpg"));
		
		// Criar um objeto Group para agrupar instruções na utilização da GUI
		Group groupHelpInstructions = new Group(this, SWT.NONE);
		groupHelpInstructions.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD | SWT.ITALIC));
		groupHelpInstructions.setText("Utilização da GUI");
		groupHelpInstructions.setBounds(10, 0, 838, 323);
		
		// Criar objeto label para mostrar a primeira instrução na utilização da GUI
		Label labelFirstInstruction = new Label(groupHelpInstructions, SWT.BORDER | SWT.WRAP);
		labelFirstInstruction.setBounds(10, 41, 818, 72);
		labelFirstInstruction.setText("1 - Carregar em \"Selecionar ficheiro\", ao aparecer uma nova janela deverá ser escolhido um ficheiro excel (.xlsx) que contenha a formatacao correta (Method id, nome do método, nome da classe, nome do pacote e detação de code smells isLong_method e isGod_class)");
		
		// Criar objeto label para mostrar a segunda instrução na utilização da GUI
		Label labelSecondInstruction = new Label(groupHelpInstructions, SWT.BORDER | SWT.WRAP);
		labelSecondInstruction.setText("2 - Após selecionado o ficheiro excel com a formatação correta deverá carregar em \"Avaliar codesmell\", este botão fará a comparação da tabela presente na GUI com a deteção do code smell em causa no ficheiro escolhido. \r\n\r\n\t- Depois de feita a comparação a tabela na GUI terá a última coluna (Qualidade) preenchida com os resultados.\r\n\r\n\t- Para além da tabela, aparecerá uma outra janela que contém um gráfico circular (Pie Chart) que tem como finalidade demonstrar os mesmos resultados da tabela mas de forma gráfica. O gráfico permite diferenciar a quantidade de Falsos Positivos, Falsos Negativos, Verdadeiros positivos e Verdadeiros Negativos.\r\n");
		labelSecondInstruction.setBounds(10, 129, 818, 176);
	}
	
	public void loadGUI () {
		// Abrir e mostrar a GUI
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
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
