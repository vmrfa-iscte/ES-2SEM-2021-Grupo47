package G47.Grupo47;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private static final String HEADER1 = "MethodID";
	private static final String HEADER2 = "Package";
	private static final String HEADER3 = "Class";
	private static final String HEADER4 = "Method";
	private static final String HEADER5= "NOM_Class";
	private static final String HEADER6= "LOC_Class";
	private static final String HEADER7 = "WMC_Class";
	private static final String HEADER8= "is_God_Class";
	private static final String HEADER9= "LOC_Method";
	private static final String HEADER10= "CYCLO_Method";
	private static final String HEADER11= "Is_Long_Method";
	private static ArrayList<String> headers;

	public ExcelManip(File file) {
		this.file = file;
		
		headers = new ArrayList<String>();
		headers.add(HEADER1);headers.add(HEADER2);headers.add(HEADER3);headers.add(HEADER4);headers.add(HEADER5);
		headers.add(HEADER6);headers.add(HEADER7);headers.add(HEADER8);headers.add(HEADER9);headers.add(HEADER10);
		headers.add(HEADER11);
	}


	public String getFileName() {
		String[] separated = file.getAbsolutePath().split(Pattern.quote(File.separator));
		String fileName = "";
		for(int i = 0; i< separated.length;i++) {
			if(separated[i].contains("src")) {
				fileName = "results_"+separated[i-1]+".xlsx";
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
		return headers;
	}

	public void createExcel(ArrayList<Metrics> data,String toCopy) throws IOException {
		toCopy = toCopy +"\\" + getFileName();
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
			System.out.println("index deste header é " + prov.getColumnIndex());
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



	public ArrayList<HasCodeSmell> toComparables(int a) throws IOException {
		FileInputStream fileTo = new FileInputStream(file.getAbsoluteFile());
		XSSFWorkbook workbook = new XSSFWorkbook(fileTo);
		XSSFSheet sheet = workbook.getSheetAt(0);
		ArrayList<HasCodeSmell> aux = new ArrayList<>();
		Iterator<Row> it = sheet.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			String method_name = row.getCell(3).toString();
			String hasCodeSmell = row.getCell(a).toString();
			String methodID = row.getCell(0).toString();
			String package_name = row.getCell(1).toString();
			String class_name = row.getCell(2).toString();
			HasCodeSmell toadd = new HasCodeSmell(method_name,hasCodeSmell,methodID,package_name,class_name,null);
			aux.add(toadd);
		}
		workbook.close();
		return aux;
	}




}