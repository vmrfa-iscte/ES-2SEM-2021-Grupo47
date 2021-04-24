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
 
	private static ArrayList<String> headers;
	public String toCopy;
	private File file;
	private NameByFile excelFileName = new NameByFile(); 
	private static final String METHOD_ID_HEADER = "MethodID", PACKAGE_HEADER = "Package", CLASS_HEADER = "Class", METHOD_HEADER = "Method", 
			NOM_CLASS_HEADER= "NOM_Class", LOC_CLASS_HEADER= "LOC_Class", WMC_CLASS_HEADER = "WMC_Class", IS_GOD_CLASS_HEADER= "is_God_Class", 
			LOC_METHOD_HEADER= "LOC_Method", CYCLO_METHOD_HEADER= "CYCLO_Method",IS_LONG_METHOD_HEADER= "Is_Long_Method";	
	
	private static final int ZERO = 0, LETTER_HEIGHT = 10;
	private static final String  DOUBLE_LEFT_SLASH = "\\";
	private static final int METHOD_ID_COLUMN_INDEX = 0, PACKAGE_COLUMN_INDEX = 1,CLASS_COLUMN_INDEX = 2,METHOD_COLUMN_INDEX = 3,
	NOMCLASS_COLUMN_INDEX = 4,LOC_CLASS_COLUMN_INDEX = 5, WMC_CLASS_COLUMN_INDEX = 6, LOC_METHOD_COLUMN_INDEX = 8, CYCLO_METHOD_COLUMN_INDEX = 9;
	


	// construtor para a classe excel Manip

	// Estes cabeçalhos são adicionados ao atributo ArrayList da classe, assim que o objeto é instanciado
	public ExcelManip(File file) {
		this.file = file;
		this.excelFileName.setFileToExtract(file);
		headers = new ArrayList<String>();
		headers.add(METHOD_ID_HEADER);headers.add(PACKAGE_HEADER);headers.add(CLASS_HEADER);headers.add(METHOD_HEADER);
		headers.add(NOM_CLASS_HEADER);headers.add(LOC_CLASS_HEADER);headers.add(WMC_CLASS_HEADER);
		headers.add(IS_GOD_CLASS_HEADER);headers.add(LOC_METHOD_HEADER);headers.add(CYCLO_METHOD_HEADER);
		headers.add(IS_LONG_METHOD_HEADER);
	}

	// Este método é bastante simples e apenas retorna o ArrayList que contem os cabeçalhos que irão constituir o excel a criar
	// Cada um destes cabeçalhos representará uma coluna
	public ArrayList<String> extractHeaders() throws IOException {
		return headers;
	}

	
	
	public void createExcel(ArrayList<MethodMetrics> data,String toCopy) throws IOException {
		toCopy = toCopy + DOUBLE_LEFT_SLASH + excelFileName.getFileName();
		ArrayList<String> headers = extractHeaders();
		XSSFWorkbook create = new XSSFWorkbook();
		XSSFSheet sheet = create.createSheet();
		int numbheaders = headers.size();
		sheet.createRow(ZERO);
		//  Titulos a bold
		XSSFCellStyle style = create.createCellStyle();
		XSSFFont font = create.createFont();
		font.setFontHeightInPoints((short) LETTER_HEIGHT);
		font.setBold(true);
		style.setFont(font); 
		// Adicionar Titulos ao Excel
		for (int i = ZERO; i != numbheaders; i++) {
			Cell prov = sheet.getRow(ZERO).createCell(i);
			prov.setCellStyle(style);
			prov.setCellValue(headers.get(i));
			System.out.println("index deste header é " + prov.getColumnIndex());
		}
		//Adicionar dados ao Excel, linha a linha, provenientes do ArrayList<Metrics> data
		double rowNumbToCreate = 1;
		for (MethodMetrics m: data) {
			// enquanto houverem objetos no array data
			Row row = sheet.createRow((int) rowNumbToCreate);
			row.createCell(METHOD_ID_COLUMN_INDEX).setCellValue(m.getMethod_ID());
			row.createCell(PACKAGE_COLUMN_INDEX).setCellValue(m.getPacote());
			row.createCell(CLASS_COLUMN_INDEX).setCellValue(m.getClasse());
			row.createCell(METHOD_COLUMN_INDEX).setCellValue(m.getNome_metodo());
			row.createCell(NOMCLASS_COLUMN_INDEX).setCellValue(m.getNOM_class());
			row.createCell(LOC_CLASS_COLUMN_INDEX).setCellValue(m.getLOC_class());
			row.createCell(WMC_CLASS_COLUMN_INDEX).setCellValue(m.getWMC_class());
			row.createCell(LOC_METHOD_COLUMN_INDEX).setCellValue(m.getLOC_method());
			row.createCell(CYCLO_METHOD_COLUMN_INDEX).setCellValue(m.getCYCLO_method());
			rowNumbToCreate++;
		}
		createFile(toCopy, create);

	}

	private void createFile(String toCopy, XSSFWorkbook create) throws FileNotFoundException, IOException {
		FileOutputStream excel = new FileOutputStream(new File(toCopy));
		create.write(excel);
		create.close();
	}
	

	public ArrayList<HasCodeSmell> toComparables(int columnOfCodeSmell) throws IOException {
		FileInputStream fileTo = new FileInputStream(file.getAbsoluteFile());
		XSSFWorkbook workbook = new XSSFWorkbook(fileTo);
		XSSFSheet sheet = workbook.getSheetAt(ZERO);
		// Abertura do ficheiro Excel que contém os resultados corretos da classificação de CodeSmells
		ArrayList<HasCodeSmell> toCompare = new ArrayList<>();
		// Criação de Array para adicionar os resultados
		Iterator<Row> it = sheet.iterator();
		while (it.hasNext()) {
			HasCodeSmell toadd = readExcelRow(columnOfCodeSmell, it);
			toCompare.add(toadd);
			// leitura dos dados existentes no ficheiro Code_Smells e criação de um Array<HasCodeSmell> com esses mesmos dados
		}
		workbook.close();
		return toCompare;
	}

	private HasCodeSmell readExcelRow(int columnOfCodeSmell, Iterator<Row> it) {
		Row row = it.next();
		String method_name = row.getCell(METHOD_COLUMN_INDEX).toString();
		String hasCodeSmell = row.getCell(columnOfCodeSmell).toString();
		String methodID = row.getCell(METHOD_ID_COLUMN_INDEX).toString();
		String package_name = row.getCell(PACKAGE_COLUMN_INDEX).toString();
		String class_name = row.getCell(CLASS_COLUMN_INDEX).toString();
		HasCodeSmell toadd = new HasCodeSmell(method_name, hasCodeSmell, methodID, package_name, class_name, null);
		return toadd;
	}




}