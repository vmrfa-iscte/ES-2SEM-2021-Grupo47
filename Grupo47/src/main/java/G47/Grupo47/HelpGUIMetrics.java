package G47.Grupo47;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

public class HelpGUIMetrics extends Shell {
	
	private Display display;
	
	public HelpGUIMetrics(Display display) {
		this.display = display;
		
	
		
		Group grpExtraoDeMtricas = new Group(this, SWT.NONE);
		grpExtraoDeMtricas.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		grpExtraoDeMtricas.setText("Extração de métricas");
		
		Label lblLocmethodParaEsta = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblLocmethodParaEsta.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblLocmethodParaEsta.setBounds(10, 41, 710, 64);
		lblLocmethodParaEsta.setText("LOC_method: Para esta métrica contamos todas as linhas existentes de um método, independentemente de serem comentários ou não por exemplo. Para calcular acedemos à localização da úlitma linha e subtraimos a localização da primeira linha do método.");
		
		Label lblLocclassParaEsta = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblLocclassParaEsta.setBounds(10, 125, 710, 44);
		lblLocclassParaEsta.setText("LOC_class: Para esta métrica foi utilizado o mesmo método explicado na métrica em cima mas aplicado à classe inteira.");
		
		Label lblCyclomethodEstaMtrica = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblCyclomethodEstaMtrica.setBounds(10, 191, 710, 44);
		lblCyclomethodEstaMtrica.setText("CYCLO_method: Esta métrica calcula a complexidade ciclomática de um método, este cálculo é feito contando o número de ciclos exsistentes no mético, são estes ciclos: if,while,for,switch.");
		
		Label lblCycloclassEstaMtrica = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblCycloclassEstaMtrica.setBounds(10, 256, 710, 64);
		lblCycloclassEstaMtrica.setText("CYCLO_class: Esta métrica calcula de novo a complexidade ciclomática da classe, esta complexidade vai ser resultado da soma da complexidade de todos os métodos da classe em questão.");
		
		Label lblNomclassAMtrica = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblNomclassAMtrica.setBounds(10, 335, 710, 20);
		lblNomclassAMtrica.setText("NOM_class: A métrica NOM_class conta o número de métodos presentes numa determinada classe.");
		
		Label lblTodasAsMtricas = new Label(grpExtraoDeMtricas, SWT.WRAP);
		lblTodasAsMtricas.setAlignment(SWT.CENTER);
		lblTodasAsMtricas.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD | SWT.ITALIC));
		lblTodasAsMtricas.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblTodasAsMtricas.setBounds(10, 382, 710, 71);
		lblTodasAsMtricas.setText("Todas as métricas são calculadas também para Inner Classes, ou seja, classes que estão dentro de outras. O nome destas classes terá a seguinte formatação: NomeClasseGeral.NomeInnerClass");
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
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	protected void createContents() {
		setText("Informaçã sobre métricas");
		setSize(748, 539);
		setImage(SWTResourceManager.getImage(HelpGUIMetrics.class, "/G47/Grupo47/iscte_logo2.jpg"));
		
		setLayout(new FillLayout(SWT.HORIZONTAL));

	}
}
