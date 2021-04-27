package gui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author Vasco Fontoura
 *
 */
public class HelpGUIMetrics extends Shell {
	
	private Display display;
	
	/**
	 * @param display
	 */
	public HelpGUIMetrics(Display display) {
		this.display = display;
		
		Group grpExtraoDeMtricas = new Group(this, SWT.NONE);
		grpExtraoDeMtricas.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		grpExtraoDeMtricas.setText("Extração de métricas");
		
		Label lblLocmethodParaEsta = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblLocmethodParaEsta.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblLocmethodParaEsta.setBounds(10, 41, 892, 49);
		lblLocmethodParaEsta.setText("LOC_method: Para esta métrica contamos todas as linhas existentes de um método, independentemente de serem comentários ou não por exemplo. Para calcular acedemos à localização da úlitma linha e subtraimos a localização da primeira linha do método.");
		
		Label lblLocclassParaEsta = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblLocclassParaEsta.setBounds(10, 108, 892, 44);
		lblLocclassParaEsta.setText("LOC_class: Para esta métrica foi utilizado o mesmo método explicado na métrica em cima mas aplicado à classe inteira. Caso dentro de uma classe esteja uma outra classe \"Inner\" então esta terá como LOC_class apenas o número de linhas da sua classe, por outro lado, a classe que a contém terá contabilizadas as linhas da Inner Class.");
		
		Label lblCyclomethodEstaMtrica = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblCyclomethodEstaMtrica.setBounds(10, 169, 892, 44);
		lblCyclomethodEstaMtrica.setText("CYCLO_method: Esta métrica calcula a complexidade ciclomática de um método, este cálculo é feito contando o número de ciclos exsistentes no mético, são estes ciclos: if,while,for,switch,case e default.");
		
		Label lblCycloclassEstaMtrica = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblCycloclassEstaMtrica.setBounds(10, 226, 892, 49);
		lblCycloclassEstaMtrica.setText("CYCLO_class: Esta métrica calcula de novo a complexidade ciclomática da classe, esta complexidade vai ser resultado da soma da complexidade de todos os métodos da classe em questão.");
		
		Label lblNomclassAMtrica = new Label(grpExtraoDeMtricas, SWT.BORDER | SWT.WRAP);
		lblNomclassAMtrica.setBounds(10, 289, 892, 49);
		lblNomclassAMtrica.setText("NOM_class: A métrica NOM_class conta o número de métodos presentes numa determinada classe. São contabilizados métodos e construtores.\r\n");
		
		Label lblTodasAsMtricas = new Label(grpExtraoDeMtricas, SWT.WRAP);
		lblTodasAsMtricas.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD | SWT.ITALIC));
		lblTodasAsMtricas.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblTodasAsMtricas.setBounds(10, 382, 892, 71);
		lblTodasAsMtricas.setText("1- Todas as métricas são calculadas também para Inner Classes, ou seja, classes que estão dentro de outras. O nome destas classes terá a seguinte formatação: NomeClasseGeral.NomeInnerClass");
		
		Label lblCasoUma = new Label(grpExtraoDeMtricas, SWT.WRAP);
		lblCasoUma.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD | SWT.ITALIC));
		lblCasoUma.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblCasoUma.setBounds(10, 466, 876, 140);
		lblCasoUma.setText("2- Caso uma classe não tenha qualquer método não serão calculadas métricas para essa classe. As métricas WMC_class e LOC_class são calculadas para classes com métodos ou construtores, caso uma classe não tenha nenhum dos referidos, então as linhas dessa classe não estarão contabilizadas nas linhas totais do projeto. Para além disto, nesse caso, essa classe não estará também contabilizada no número de classes do projeto.\r\n");
		
		Label lblNotas = new Label(grpExtraoDeMtricas, SWT.NONE);
		lblNotas.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD | SWT.ITALIC));
		lblNotas.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNotas.setBounds(10, 344, 70, 20);
		lblNotas.setText("NOTAS:");
		createContents();
	}
	
	/**
	 * abre a GUI
	 */
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
	
	/**
	 * define características da GUI
	 */
	protected void createContents() {
		setText("Informaçã sobre métricas");
		setSize(930, 682);
		setImage(SWTResourceManager.getImage(HelpGUIMetrics.class, "/G47/Grupo47/iscte_logo2.jpg"));
		
		setLayout(new FillLayout(SWT.HORIZONTAL));

	}
}