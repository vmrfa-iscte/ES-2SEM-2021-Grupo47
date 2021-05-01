package classes;

import org.eclipse.swt.widgets.Table;
import java.util.ArrayList;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;

/**
 * Esta classe e responsavel por preencher uma dada tabela com informacao da detecao de code smells (nome do metodo,
 * nome da classe, id do metodo,detecao e qualidade de detecao).
 * @author Vasco Fontoura
 * @version 2
 *
 */
public class FillTable {
	
	private Table table;
	private QualityCalculator calculator = new QualityCalculator();


	/**
	 * Construtor
	 * @param table uma tabela
	 */
	public FillTable(Table table) {
		this.table = table;
	}

	/**
	 * Getter para a tabela dada
	 * @return a tabela recebida na criacao
	 */
	public Table getTable() {
		return table;
	}
	
	/**
	 * Percorre uma lista de HasCodeSmell e adiciona a sua informacao a tabela
	 * @param toFill uma lista de HasCodeSmell para colocar na tabela
	 * @param withQuality um boleano para indicar se se deve colocar a qualidade 
	 */
	public void fillTable(ArrayList<HasCodeSmell> toFill,boolean withQuality) { 
		table.removeAll();
		for (HasCodeSmell hascodesmell : toFill)
			addCodeSmellsInfo(hascodesmell, withQuality);
	}

	
	/**
	 *  Preenche a tabela da SecondaryGUI com a informacao de um HasCodeSmell, caso o Objeto ja tenha qualidade, esta sera preenchida na coluna "Qualidade"
	 * @param codesmellToAdd um objeto HasCodeSmell para adicionar a tabela
	 * @param withQuality um boleano para indicar se se deve colocar a qualidade 
	 */
	public void addCodeSmellsInfo(HasCodeSmell codesmellToAdd, boolean withQuality) {
		TableItem tableItem = new TableItem(table, SWT.NONE);
		if (withQuality) {
			if (codesmellToAdd.isMethod())
				tableItem.setText(new String[] { codesmellToAdd.getMethod_ID(), codesmellToAdd.getMethodName(), codesmellToAdd.getHasCodeSmell(),
						codesmellToAdd.getQuality() });
			else
				tableItem.setText(new String[] { codesmellToAdd.getMethod_ID(), codesmellToAdd.getClassName(), codesmellToAdd.getHasCodeSmell(),
						codesmellToAdd.getQuality() });
		} else {
			if (codesmellToAdd.isMethod())
				tableItem.setText(new String[] { codesmellToAdd.getMethod_ID(), codesmellToAdd.getMethodName(), codesmellToAdd.getHasCodeSmell(), "" });
			else
				tableItem.setText(new String[] { codesmellToAdd.getMethod_ID(), codesmellToAdd.getClassName(), codesmellToAdd.getHasCodeSmell(), "" });
		}
	}
	
	
	/**
	 * Percorre a lista de HasCodeSmells que resultaram da detecao pela regra escolhida e para cada um edita a sua qualidade
	 * @param trueResults uma lista de HasCodeSmell resultante da leitura do ficheiro excel
	 * @param toEdit uma lista com os objetos HasCodeSmells que estão na tabela
	 * @return uma lista de HasCodeSmell com o campo Qualidade alterado
	 */
	public ArrayList<HasCodeSmell> calculateQuality(ArrayList<HasCodeSmell> trueResults,ArrayList<HasCodeSmell> toEdit) {
		for (HasCodeSmell indicator : toEdit) 
			searchAndSetQuality(trueResults, indicator);
		return toEdit;
	}

	
	/**
	 * Procura o metodo correto e depois compara as detecoes para editar a qualidade (ex: Verdadeiro Positivo,etc..)
	 * @param trueResults uma lista de HasCodeSmell resultante da leitura do ficheiro excel
	 * @param indicator um objeto HasCodeSmell
	 */
	private void searchAndSetQuality(ArrayList<HasCodeSmell> trueResults, HasCodeSmell indicator) {
	
		if (indicator.isMethod()) {
			for (HasCodeSmell calculated : trueResults) {
				if (indicator.isEqual(calculated))
					calculator.setQuality(indicator, calculated);
			}
		}
	}
	
	


}