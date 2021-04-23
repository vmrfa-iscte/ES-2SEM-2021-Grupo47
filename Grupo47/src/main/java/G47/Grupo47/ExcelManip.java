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
	private static final String HEADER1 = "MethodID", HEADER2 = "Package", HEADER3 = "Class", HEADER4 = "Method", HEADER5= "NOM_Class", 
			HEADER6= "LOC_Class", HEADER7 = "WMC_Class", HEADER8= "is_God_Class", HEADER9= "LOC_Method", HEADER10= "CYCLO_Method",
					HEADER11= "Is_Long_Method";	
	
	private static final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6, EIGHT = 8, NINE = 9, LETTER_HEIGHT = 10;
	private static final String SRC = "src", RESULTS_ = "results_", FILE_TYPE = ".xlsx", DOUBLE_LEFT_SLASH = "\\", EMPTY_PATH = "";

	// construtor para a classe excel Manip
	// A mesma tem 2 atributos, 1 File, e um ArrayList que contem os cabeçalhos do Excel que irá ser gerado, contendo o resultado da extração
	// de métricas
	// Estes cabeçalhos são adicionados ao atributo ArrayList da classe, assim que o objeto é instanciado
	public ExcelManip(File file) {
		this.file = file;
		headers = new ArrayList<String>();
		headers.add(HEADER1);headers.add(HEADER2);headers.add(HEADER3);headers.add(HEADER4);headers.add(HEADER5);
		headers.add(HEADER6);headers.add(HEADER7);headers.add(HEADER8);headers.add(HEADER9);headers.add(HEADER10);
		headers.add(HEADER11);
	}


	// Esta função é utilizada para retornar o nome, e apenas o nome do Ficheiro que foi passado como argumento
	public String getFileName() {
		String[] separated = file.getAbsolutePath().split(Pattern.quote(File.separator));
		String fileName = EMPTY_PATH;
		for(int i = ZERO; i< separated.length;i++) {
			if(separated[i].contains(SRC)) {
				fileName = RESULTS_+separated[i-ONE]+FILE_TYPE;
			}
		}
		return fileName;
	}



	// Este método é bastante simples e apenas retorna o ArrayList que contem os cabeçalhos que irão constituir o excel a criar
	// Cada um destes cabeçalhos representará uma coluna
	public ArrayList<String> extractHeaders() throws IOException {
		return headers;
	}

	
	// Este método irá criar o excel, constituido por todos os métodos do projeto indicado, assim como as métricas calculadas para
	// cada um deles. São incluidas também as métricas para a classe.
	// Numa primeira instância, este método obtem os cabeçalhos através do método interno extractHeaders(), após isto cria um ficheiro excel
	// para onde copia os cabeçalhos que obteve previamente
	// Após isto, o método vai percorrer o ArrayList data, métrica a métrica, e copiar o seu conteudo para o ficheiro excel, linha a linha
	// No fim é consumada a criação do excel, com auxilio do método FileOutPutStream
	public void createExcel(ArrayList<Metrics> data,String toCopy) throws IOException {
		toCopy = toCopy +DOUBLE_LEFT_SLASH + getFileName();
		ArrayList<String> headers = extractHeaders();
		XSSFWorkbook create = new XSSFWorkbook();
		XSSFSheet sheet = create.createSheet();
		int numbheaders = headers.size();
		sheet.createRow(ZERO);
		// titulos a bold
		XSSFCellStyle style = create.createCellStyle();
		XSSFFont font = create.createFont();
		font.setFontHeightInPoints((short) LETTER_HEIGHT);
		font.setBold(true);
		style.setFont(font); 
		// Adicionar Titulos
		for (int i = ZERO; i != numbheaders; i++) {
			Cell prov = sheet.getRow(ZERO).createCell(i);
			prov.setCellStyle(style);
			prov.setCellValue(headers.get(i));
			System.out.println("index deste header é " + prov.getColumnIndex());
		}
		//Adicionar dados 
		double rowNumbToCreate = 1;
		for (Metrics m: data) {
			Row row = sheet.createRow((int) rowNumbToCreate);
			row.createCell(ZERO).setCellValue(m.getMethod_ID());
			row.createCell(ONE).setCellValue(m.getPacote());
			row.createCell(TWO).setCellValue(m.getClasse());
			row.createCell(THREE).setCellValue(m.getNome_metodo());
			row.createCell(FOUR).setCellValue(m.getNOM_class());
			row.createCell(FIVE).setCellValue(m.getLOC_class());
			row.createCell(SIX).setCellValue(m.getWMC_class());
			row.createCell(EIGHT).setCellValue(m.getLOC_method());
			row.createCell(NINE).setCellValue(m.getCYCLO_method());
			rowNumbToCreate++;
		}
		FileOutputStream excel = new FileOutputStream(new File(toCopy));
		create.write(excel);
		create.close();

	}


	// Após a aplicação das regras definidas pelo utilizador é gerado um ArrayList<HasCodeSmell> contendo os resultados da aplicaçã desta regra
	// os resultados são apenas TRUE (tem code smell) ou FALSE (não tem code smell)
	// Para posteriormente conseguirmos averiguar a qualidade desta avaliação, temos de comparar os resultados reais presentes no ficheiro
	// Code_Smells.xlsx com os resultados que obtivemos.
	// Tudo o que este método faz, é ler um ficheiro Excel (neste caso o Code_Smells.xlsx) e copiar o seu conteudo, linha a linha, para um
	// ArrayList<HasCodeSmell>. É portanto através deste arrayList que se poderam comparar os resultados e posteriormente identificar os
	// Falsos positivos, falsos negativos, verdadeiros positivos e verdadeiros negativos
	// É passado como argumento um inteiro a, pois dependendo do code Smell que está a ser detetado (is Long Method ou God Class), o seu resultado encontra-se
	// em colunas diferentes no excel Code_Smells.xlsx
	public ArrayList<HasCodeSmell> toComparables(int a) throws IOException {
		FileInputStream fileTo = new FileInputStream(file.getAbsoluteFile());
		XSSFWorkbook workbook = new XSSFWorkbook(fileTo);
		XSSFSheet sheet = workbook.getSheetAt(ZERO);
		ArrayList<HasCodeSmell> toCompare = new ArrayList<>();
		Iterator<Row> it = sheet.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			String method_name = row.getCell(THREE).toString();
			String hasCodeSmell = row.getCell(a).toString();
			String methodID = row.getCell(ZERO).toString();
			String package_name = row.getCell(ONE).toString();
			String class_name = row.getCell(TWO).toString();
			HasCodeSmell toadd = new HasCodeSmell(method_name,hasCodeSmell,methodID,package_name,class_name,null);
			toCompare.add(toadd);
		}
		workbook.close();
		return toCompare;
	}




}