package classes;


import java.io.File;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;

import extraction.ExtractMetrics;

import java.util.regex.Pattern;

/**
 * @author Vasco Fontoura
 *
 */
public class NameByFile {
	
	private File fileToExtract;
	private static final String SRC = "src", RESULTS_ = "results_", FILE_TYPE = ".xlsx", EMPTY_PATH = "";

	/**
	 * @return um ficheiro dado
	 */
	public File getFileToExtract() {
		return fileToExtract;
	}

	/**
	 * @param fileToExtract um dado ficheiro
	 */
	public void setFileToExtract(File fileToExtract) {
		this.fileToExtract = fileToExtract;
	}
	
	/**
	 * @param classFromFile uma dada classe ou interface
	 * @return o nome da classe na correta formatação
	 */
	public String getClassName(ClassOrInterfaceDeclaration classFromFile) {
		// Retorna o nome utilizado para definir a classe no excel através do nome da classe e do nome do ficheiro
		if (classFromFile.getNameAsString()
				.equals(fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING)))
			return classFromFile.getNameAsString();
		else
			return fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING) + "."
					+ classFromFile.getNameAsString();
	}

	
	/**
	 * @param enumFromFile um dado enumerado
	 * @return o nome do enumerado na correta formatação
	 */
	public String getClassNameForEnum(EnumDeclaration enumFromFile) {
		// Faz o mesmo que o método em cima mas para classes que sejam Enumerados
		if (enumFromFile.getNameAsString()
				.equals(fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING)))
			return enumFromFile.getNameAsString();
		else
			return fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING) + "."
					+ enumFromFile.getNameAsString();
	}

	
	/**
	 * @return nome do pacote na correta formatação do ficheiro dado
	 */
	public String getPackageName() {
		// Retorna o nome do pacote segundo um ficheiro
		String packageName = ExtractMetrics.EMPTY_STRING;
		boolean src = false;
		String[] separated = fileToExtract.getAbsolutePath().split(Pattern.quote(File.separator));
		for (int i = 0; i < separated.length - 1; i++) {
			if (src && i <= separated.length - 2) {
				if (i < separated.length - 2)
					packageName += separated[i] + ".";
				else
					packageName += separated[i];
			}
			if (separated[i].contains(ExtractMetrics.SRC_DIR))src = true;
		}
		if (isDefaultPackage(packageName, fileToExtract))
			packageName = ExtractMetrics.DEFAULT_PACKAGE;
		return packageName;
	}

	
	/**
	 * @param packageName o nome de um pacote
	 * @param fileToExtract um dado ficheiro
	 * @return indicador se o ficheiro se encontra no pacote 'DefaultPackage'
	 */
	public boolean isDefaultPackage(String packageName, File fileToExtract) {
		// Verifica se a classe está no pacote pré definido
		// caso o caminho dado contenha uma pasta "src" e o nome do pacote esteja vazio quer dizer que está diretamente dentro da pasta "src
		// logo, pertence ao pacote pré-definido
		if (packageName.equals(ExtractMetrics.EMPTY_STRING)
				&& fileToExtract.getAbsolutePath().contains(ExtractMetrics.SRC_DIR))
			return true;
		else
			return false;
	}
	
	
	/**
	 * @return nome do ficheiro dado na correta formatação
	 */
	public String getFileName() {
		// Esta função é utilizada para retornar o nome, e apenas o nome do Ficheiro que foi passado como argumento
		String[] separated = fileToExtract.getAbsolutePath().split(Pattern.quote(File.separator));
		String fileName = EMPTY_PATH;
		for(int i = 0; i< separated.length;i++) {
			if(separated[i].contains(SRC)) { fileName = RESULTS_+separated[i-1]+FILE_TYPE; }
		}
		return fileName;
	}
}