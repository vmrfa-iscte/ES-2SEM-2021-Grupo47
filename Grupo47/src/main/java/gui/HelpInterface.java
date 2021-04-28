package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class HelpInterface extends Shell {

	private Display display;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	// CONSTRUTOR DA INTERFACE "HelpInterface" COM TODO O CONTEÚDO QUE A COMPÕE.
	public HelpInterface(Display display) {
//		super(display, SWT.SHELL_TRIM);
		this.display=display;

		// INSTRUÇÃO 1-Extração de métricas
		Label lblHelpExtract = new Label(this, SWT.NONE | SWT.BORDER | SWT.WRAP);
		lblHelpExtract.setBounds(10, 21, 870, 48);
		lblHelpExtract.setText(
				"1-Selecione a pasta onde se localiza o projeto do qual pretende extrair métricas, clicando no botão 'Selecionar pasta'. De seguida, pressione o botão 'Extrair métricas', do qual resultará um ficheiro com as respetivas métricas calculadas.");

		// INSTRUÇÃO 2-Visualização de resultados
		Label lblExtractResult = new Label(this, SWT.BORDER | SWT.WRAP);
		lblExtractResult.setBounds(10, 75, 870, 66);
		lblExtractResult.setText(
				"2-Pode agora verificar que, na lista centrada à esqueda da interface, foi gerada uma lista com o nome do(s) ficheiro(s) excel gerado(s). Ao clicar sobre esse ficheiro, poderá verificar as características gerais do mesmo, nos campos localizados à direita da interface. ");

		// INSTRUÇÃO 3-Definir regras
		Label lblRules = new Label(this, SWT.BORDER | SWT.WRAP);
		lblRules.setBounds(10, 147, 870, 66);
		lblRules.setText(
				"3-Após este processo de extração, poderá agora definir regras para a deteção de codesmells. Para tal, preencha os campos que se encontram vazios de forma válida e coerente, e de seguida pressione o botão 'Definir regra'. De recordar que não há um limite de regras por definir.");

		// INSTRUÇÃO 4-Alterar regras
		Label lblChangeRules = new Label(this, SWT.BORDER | SWT.WRAP);
		lblChangeRules.setBounds(10, 219, 870, 66);
		lblChangeRules.setText(
				"4- Há medida que as regras são definidas, poderá notar que as mesmas são guardadas numa lista localizada no canto inferior esquerdo da interface. Se pretender alterar alguma destas regras, basta seleciona-la, alterar os campos desejados no mesmo sitio onde os definiu e, após isto, clicar no botão 'Alterar regra'.");

		// INSTRUÇÃO 5-Histórico
		Label lblHistory = new Label(this, SWT.BORDER | SWT.WRAP);
		lblHistory.setBounds(10, 291, 870, 113);
		lblHistory.setText(
				"5-Após a definição das regras desejadas, tem a possibilidade de as guardar num histórico não volátil. Para tal, basta clicar no botão 'Guardar histórico', da qual resultará a abertura de uma nova interface. Nesta nova interface, terá duas possibilidades: Se já tiver um ficheiro histórico criado, basta selecionar o mesmo através do botão 'Selecionar ficheiro'. Caso pretenda criar um ficheiro novo, basta preencher os campos relativos ao nome do ficheiro e à localização destino do mesmo. Por fim, basta pressionar o botão 'Criar ficheiro histórico'.");

		// INSTRUÇÃO 6-Deteção de codesmells
		Label lblCodesmellsDetect = new Label(this, SWT.BORDER | SWT.WRAP);
		lblCodesmellsDetect.setBounds(10, 410, 870, 91);
		lblCodesmellsDetect.setText(
				"6- Por fim, temos a deteção dos codesmells. Para tal, basta selecionar uma das regras resultantes da sua definição, para a qual pretende fazer esta mesma deteção. Após esta seleção, pressione o botão 'Deteção de codesmells', da qual resultará uma nova interface com os resultados obtidos.");

		createContents();
	}

	// MÉTODO PARA ABRIR A INTERFACE "AJUDA-UTILIZAÇÃO DA INTERFACE" COM O FORMATO
	// CORRETO.
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
		setText("Ajuda-Utilização da interface");
		setSize(934, 558);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
