package G47.Grupo47;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManip {

	public String toCopy;
	private File file;

	public ExcelManip(File file) {
		this.file = file;
		this.toCopy = getFilePath();
	}

	public String getFileName() {
		String[] separated = file.getAbsolutePath().split(Pattern.quote(File.separator));
		String fileName = "";
		for(int i = 0; i< separated.length;i++) {
			if(separated[i].contains("src")) {
				fileName = "result_"+separated[i-1]+".xlsx";
			}
		}
		return fileName;
	}

	public String getFilePath() {
		System.out.println(file.getAbsolutePath());
		String[] separated = file.getAbsolutePath().split(Pattern.quote(File.separator));
		String pathToCreate = "";
		String soFar = "";
		for(int i = 0; i< separated.length;i++) {
			soFar = soFar+separated[i]+"\\";
			if(separated[i].contains("Users")) {
				pathToCreate = soFar + separated[i+1] + "\\Desktop\\" ;
			}
			if(separated[i].contains("src")) {
				pathToCreate = pathToCreate + "result_"+separated[i-1]+".xlsx";
			}
		}
		System.out.println("PathToCreate: "+pathToCreate);
		return pathToCreate;
	}

	public ArrayList<String> extractHeaders() throws IOException {
		FileInputStream file = new FileInputStream(new File("C:\\Code_Smells.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0); 
		Iterator<Row> it = sheet.iterator();

		ArrayList<String> column = new ArrayList<>();
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

	public void createExcel(ArrayList<Metrics> data) throws IOException {
		ArrayList<String> headers = extractHeaders();
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
		for (Metrics m: data) {
			Row a = sheet.createRow((int) i);
			a.createCell(0).setCellValue(m.getMethod_ID());
			a.createCell(1).setCellValue(m.getPacote());
			a.createCell(2).setCellValue(m.getClasse());
			a.createCell(3).setCellValue(m.getNome_metodo());
			a.createCell(4).setCellValue(m.getNOM_class());
			a.createCell(5).setCellValue(m.getLOC_class());
			a.createCell(6).setCellValue(m.getWMC_class());
			a.createCell(8).setCellValue(m.getLOC_method());
			a.createCell(9).setCellValue(m.getCYCLO_method());
			i++;
		}
		FileOutputStream excel = new FileOutputStream(new File(toCopy));
		create.write(excel);
		create.close();

	}



}
