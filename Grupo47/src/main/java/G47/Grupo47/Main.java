package G47.Grupo47;

import java.io.IOException;

import org.eclipse.swt.widgets.Display;

import gui.mainGUI;

public class Main {

	/** Funcao main que permite correr todo o projeto
	 * @param args valor default do java
	 * @throws IOException excecao
	 * @author Tomas Mendes
	 * @author Vasco Fontoura
	 * @author Gwinie tha pooh, aka gwin diesel, aka damn negro
	 * 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		try {
			// Criacao de um Display
			Display display = Display.getDefault();
		
			// Criacao da GUI principal do nosso projeto
			mainGUI shell = new mainGUI(display);
			
			
			shell.open();
			shell.layout();
			// Abertura e preenchimento do layout da GUI
			while (!shell.isDisposed()) {
				// Enquanto a shell nao e fechada
				if (!display.readAndDispatch()) {
					display.sleep();
					// o display mantem se aberto
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

