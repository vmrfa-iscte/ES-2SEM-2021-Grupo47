package G47.Grupo47;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManip {
	
	public String toCopy = "C:\\Users\\Tomás Mendes\\Desktop\\result.xlsx";

//	public void readExcel () throws IOException {
//		FileInputStream file = new FileInputStream(new File("C:\\Code_Smells.xlsx"));
//		XSSFWorkbook workbook = new XSSFWorkbook(file);
//		XSSFSheet sheet = workbook.getSheetAt(0);
//		Iterator<Row> it = sheet.iterator();
//
//		//desta forma percorre linha a linha
//		int i = 0;
//		int z = 0;
//		ArrayList column = new ArrayList();
//		while (it.hasNext()) {
//			Row row = it.next();
//			Iterator<Cell> ci = row.iterator();
//
//			while (ci.hasNext()) {
//				Cell cell = ci.next();
//				if (row.getRowNum() == 0) {
//					column.add(cell.toString());
//				}
//				if (row.getRowNum() == 0) {
//
//				}else {
//					System.out.println("Rownumb -" + cell.getRowIndex() +  "||" + " Column- " + column.get(cell.getColumnIndex()) + "||" +  " Content- " + cell.toString() );
//				}
//			}
//
//		}
//
//	}

	public ArrayList<String> extractHeaders() throws IOException {
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

			}

		}
		return column;
	}

	public void createExcel(ArrayList<String> headers, ArrayList<Metricas> data) throws IOException {
		XSSFWorkbook create = new XSSFWorkbook();
		XSSFSheet sheet = create.createSheet();
		int numbheaders = headers.size();
		sheet.createRow(0);
		// titulos a bold
		XSSFCellStyle style = create.createCellStyle();
		XSSFFont font = create.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		style.setFont(font); 
		// Adicionar Titulos
		for (int i = 0; i != numbheaders; i++) {
			Cell prov = sheet.getRow(0).createCell(i);
			prov.setCellStyle(style);
			prov.setCellValue(headers.get(i));
		}
		//Adicionar dados 
		double i = 1;
		for (Metricas m : data) {
			Row a = sheet.createRow((int) i);
			a.createCell(0).setCellValue(i);
			a.createCell(1).setCellValue(m.getPacote());
			a.createCell(2).setCellValue(m.getClasse());
			a.createCell(3).setCellValue(m.getNome_metodo());
			a.createCell(5).setCellValue(m.getLOC_class());
			a.createCell(8).setCellValue(m.getLOC_method());
			i++;
		}
		FileOutputStream excel = new FileOutputStream(new File(toCopy));
		create.write(excel);
		create.close();
	
}
	
//	public void writeData(ArrayList<Metricas> data) throws IOException {
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet sheet = workbook.getSheetAt(0);
//		
//
//		FileOutputStream excel = new FileOutputStream(new File(toCopy));
//		workbook.write(excel);
//		workbook.close();
//		
//	}
	
	
}
