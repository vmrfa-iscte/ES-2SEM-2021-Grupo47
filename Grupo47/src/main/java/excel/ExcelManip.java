package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import classes.HasCodeSmell;
import classes.MethodIdentity;
import classes.MethodMetrics;
import classes.NameByFile;

/**
 * Classe utilizada para executar as operações de manipulacao necessarias nos ficheiros excel
 * @author Tomas Mendes
 * @version 4
 *
 */
public class ExcelManip {
 
	private static ArrayList<String> headers;
	private String toCopy;
	private File file;
	private NameByFile excelFileName = new NameByFile(); 
	private static final String METHOD_ID_HEADER = "MethodID", PACKAGE_HEADER = "Package", CLASS_HEADER = "Class", METHOD_HEADER = "Method", 
			NOM_CLASS_HEADER= "NOM_Class", LOC_CLASS_HEADER= "LOC_Class", WMC_CLASS_HEADER = "WMC_Class",  
			LOC_METHOD_HEADER= "LOC_Method", CYCLO_METHOD_HEADER= "CYCLO_Method";
	
	private static final int ZERO = 0, LETTER_HEIGHT = 10;
	private static final String  DOUBLE_LEFT_SLASH = "\\";
	private static final int METHOD_ID_COLUMN_INDEX = 0, PACKAGE_COLUMN_INDEX = 1,CLASS_COLUMN_INDEX = 2,METHOD_COLUMN_INDEX = 3,
	NOMCLASS_COLUMN_INDEX = 4,LOC_CLASS_COLUMN_INDEX = 5, WMC_CLASS_COLUMN_INDEX = 6, LOC_METHOD_COLUMN_INDEX = 7, CYCLO_METHOD_COLUMN_INDEX = 8;

	
	/**
	 * Construtor recebe como parametro um determinado ficheiro excel
	 * @param file um dado ficheiro
	 */
	public ExcelManip(File file) {
		this.file = file;
		this.excelFileName.setFileToExtract(file);
		headers = new ArrayList<String>();
		headers.add(METHOD_ID_HEADER);headers.add(PACKAGE_HEADER);headers.add(CLASS_HEADER);headers.add(METHOD_HEADER);
		headers.add(NOM_CLASS_HEADER);headers.add(LOC_CLASS_HEADER);headers.add(WMC_CLASS_HEADER);
		headers.add(LOC_METHOD_HEADER);headers.add(CYCLO_METHOD_HEADER);
		// Estes cabeçalhos são adicionados ao atributo ArrayList da classe, assim que o objeto é instanciado
		
	}

	
	/**
	 * Extrai os cabecalhos
	 * @return ArrayList com os cabeçalhos a inserir no excel que vai ser gerado
	 * @throws IOException excecao
	 */
	public ArrayList<String> extractHeaders() throws IOException {
		return headers;
		// Este método é bastante simples e apenas retorna o ArrayList que contem os cabeçalhos que irão constituir o excel a criar
		// Cada um destes cabeçalhos representará uma coluna
	}

	
	/**
	 * metodo para criar ficheiro excel, preechido com as metricas extraidas de um dado projeto (formato identico ao do ficheiro Code_Smells.xlsx)
	 * (LOC_Class, NOM_Class, CYCLO_Class, CYCLO_Method, LOC_Method)
	 * @param data (ArrayList com metricas calculadas)
	 * @param toCopy (Caminho onde será guardado o ficheiro)
	 * @throws IOException excecao
	 */
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

	/**
	 * metodo para criação de ficheiro excel
	 * @param toCopy (path onde será guardado o ficheiro)
	 * @param create (WorkBook excel onde serão escritos os dados numa dada sheet)
	 * @throws FileNotFoundException excecao 
	 * @throws IOException excecao
	 */
	private void createFile(String toCopy, XSSFWorkbook create) throws FileNotFoundException, IOException {
		FileOutputStream excel = new FileOutputStream(new File(toCopy));
		create.write(excel);
		create.close();
	}
	

	/**
	 * Metodo utilizado para transformar um ficheiro excel do genero Code_Smells.xlsx em um array list de HasCodeSmell, por forma a que
	 * as classificações corretas possam ser comparadas com as metricas extraidas
	 * @param columnOfCodeSmell (Consoante o code smell detetado, a coluna onde se vai buscar a classificação difere)
	 * @return retorna arrayList com o o conteudo do ficheiro excel
	 * @throws IOException excecao
	 */
	public ArrayList<HasCodeSmell> toComparables(int columnOfCodeSmell) throws IOException {
		FileInputStream fileTo = new FileInputStream(file.getAbsoluteFile());
		XSSFWorkbook workbook = new XSSFWorkbook(fileTo);
		XSSFSheet sheet = workbook.getSheetAt(ZERO);
		// Abertura do ficheiro Excel que contém os resultados corretos da classificação de CodeSmells
		ArrayList<HasCodeSmell> toCompare = new ArrayList<>();
		// Criação de Array para adicionar os resultados
		Iterator<Row> it = sheet.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			if(row.getRowNum()>0) {
				HasCodeSmell toadd = readExcelRow(columnOfCodeSmell,row);
				toCompare.add(toadd);
			}
			// leitura dos dados existentes no ficheiro Code_Smells e criação de um Array<HasCodeSmell> com esses mesmos dados
		}
		workbook.close();
		return toCompare;
	}

	
	/**
	 * Metodo utilizado para ler uma linha de um determinado ficheiro exel
	 * @param columnOfCodeSmell coluna de onde vai ser extraida a classificação correta
	 * @param row linha de onde esta classificacao sera extraida
	 * @return objeto HasCodeSmell composto pela informacao que foi lida
	 */
	private HasCodeSmell readExcelRow(int columnOfCodeSmell, Row row) {
		String method_name = row.getCell(METHOD_COLUMN_INDEX).toString();
		String hasCodeSmell = row.getCell(columnOfCodeSmell).toString();
		String package_name = row.getCell(PACKAGE_COLUMN_INDEX).toString();
		String class_name = row.getCell(CLASS_COLUMN_INDEX).toString();
		MethodIdentity method = new MethodIdentity(method_name,class_name,package_name,0);
		MethodMetrics methodWithMetrics = new MethodMetrics(method,null);
		HasCodeSmell toadd = new HasCodeSmell(hasCodeSmell, null, methodWithMetrics,true);
		return toadd;
	}




}