package G47.Grupo47;


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

	public void fillSecondaryGUI(ArrayList<HasCodeSmell> toFill,boolean withQuality) {
		table.removeAll();
		for (HasCodeSmell hascodesmell : toFill)
			addCodeSmellsInfo(hascodesmell, withQuality);
	}

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
	
	public ArrayList<HasCodeSmell> calculateQuality(ArrayList<HasCodeSmell> trueResults,ArrayList<HasCodeSmell> toEdit) {
		for (HasCodeSmell indicator : toEdit) {
			if(indicator.isMethod()) {
				for (HasCodeSmell calculated: trueResults ) {
					if (indicator.isEqual(calculated)) 
						calculator.setQuality(indicator, calculated);
				}
			}
		}
		return toEdit;
	}
	
	


}