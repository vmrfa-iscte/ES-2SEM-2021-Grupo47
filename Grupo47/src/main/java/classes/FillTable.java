package classes;


import org.eclipse.swt.widgets.Table;
import java.util.ArrayList;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import java.util.HashMap;

public class FillTable {
	
	private Table table;
	private QualityCalculator calculator = new QualityCalculator();


	public FillTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}

	// Percorre uma lista de HasCodeSmell e adiciona a sua informação à tabela 
	public void fillSecondaryGUI(ArrayList<HasCodeSmell> toFill,boolean withQuality) {
		table.removeAll();
		for (HasCodeSmell hascodesmell : toFill)
			addCodeSmellsInfo(hascodesmell, withQuality);
	}

	// Preenche a tabela da SecondaryGUI com a informação de um HasCodeSmell, caso o OBjeto já tenha qualidade, esta será preenchida na coluna "Qualidade"
	public void addCodeSmellsInfo(HasCodeSmell hcs, boolean withQuality) {
		TableItem tableItem = new TableItem(table, SWT.NONE);
		if (withQuality) {
			if (hcs.isMethod())
				tableItem.setText(new String[] { hcs.getMethod_ID(), hcs.getMethodName(), hcs.getHasCodeSmell(),
						hcs.getQuality() });
			else
				tableItem.setText(new String[] { hcs.getMethod_ID(), hcs.getClassName(), hcs.getHasCodeSmell(),
						hcs.getQuality() });
		} else {
			if (hcs.isMethod())
				tableItem.setText(new String[] { hcs.getMethod_ID(), hcs.getMethodName(), hcs.getHasCodeSmell(), "" });
			else
				tableItem.setText(new String[] { hcs.getMethod_ID(), hcs.getClassName(), hcs.getHasCodeSmell(), "" });
		}
	}
	
	// Percorre a lista de HasCodeSmells que resultaram da detação pela regra escolhida e para cada uma edita a sua qualidade
	public ArrayList<HasCodeSmell> calculateQuality(ArrayList<HasCodeSmell> trueResults,ArrayList<HasCodeSmell> toEdit) {
		for (HasCodeSmell indicator : toEdit) {
			searchAndSetQuality(trueResults, indicator);
		}
		return toEdit;
	}

	// Procura o método correto e depois compara as deteções para editar a qualidade (ex: Verdadeiro Positivo,etc..)
	private void searchAndSetQuality(ArrayList<HasCodeSmell> trueResults, HasCodeSmell indicator) {
		if (indicator.isMethod()) {
			for (HasCodeSmell calculated : trueResults) {
				if (indicator.isEqual(calculated))
					calculator.setQuality(indicator, calculated);
			}
		}
	}
	
	


}