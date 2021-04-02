//package G47.Grupo47;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.widgets.List;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.widgets.TableColumn;
//
//public class DetecaoCodeSmells extends Shell {
//	private Table table;
//
//	/**
//	 * Launch the application.
//	 * @param args
//	 */
//	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			DetecaoCodeSmells shell = new DetecaoCodeSmells(display);
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
//
//	/**
//	 * Create the shell.
//	 * @param display
//	 */
//	public DetecaoCodeSmells(Display display) {
//		super(display, SWT.SHELL_TRIM);
//		
//		Combo regras = new Combo(this, SWT.NONE);
//		regras.setBounds(10, 273, 346, 28);
//		
//		Composite composite = new Composite(this, SWT.NONE);
//		composite.setBounds(262, 10, 295, 246);
//			
//		Button btnNewButton = new Button(this, SWT.NONE);
//		btnNewButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//			
//			}
//		});
//		btnNewButton.setBounds(377, 271, 180, 30);
//		btnNewButton.setText("Avaliar codesmell");
//		
//		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
//		table.setBounds(10, 10, 246, 246);
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
//		
//		TableColumn classmethod = new TableColumn(table, SWT.NONE);
//		classmethod.setWidth(143);
//		classmethod.setText("    Classe/método");
//		
//		TableColumn detecao = new TableColumn(table, SWT.NONE);
//		detecao.setWidth(111);
//		detecao.setText("   Deteção");
//		
//		
//	
//		createContents();
//	}
//
//	/**
//	 * Create contents of the shell.
//	 */
//	protected void createContents() {
//		setText("Codesmells");
//		setSize(585, 358);
//
//	}
//
//	@Override
//	protected void checkSubclass() {
//		// Disable the check that prevents subclassing of SWT components
//	}
//}
