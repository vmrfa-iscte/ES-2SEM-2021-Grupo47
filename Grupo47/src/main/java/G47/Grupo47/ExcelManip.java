package G47.Grupo47;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManip {

	public static void main(String[] args) throws IOException {
		FileInputStream file = new FileInputStream(new File("C:\\Code_Smells.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		
		//desta forma percorre linha a linha
		int i = 0;
		int z = 0;
		ArrayList column = new ArrayList();
		while (it.hasNext()) {
			Row row = it.next();
			Iterator<Cell> ci = row.iterator();
			
			while (ci.hasNext()) {
				Cell cell = ci.next();
				if (row.getRowNum() == 0) {
					column.add(cell.toString());
				}
				if (row.getRowNum() == 0) {
					
				}else {
					System.out.println("Rownumb -" + cell.getRowIndex() +  "||" + " Column- " + column.get(cell.getColumnIndex()) + "||" +  " Content- " + cell.toString() );
				}
			}
			
		}
				

	}
	
	
}
