package G47.Grupo47;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;

public class Main {

	public static void main(String[] args) throws IOException {
		try {
			Display display = Display.getDefault();
			mainGUI shell = new mainGUI(display);
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

}

