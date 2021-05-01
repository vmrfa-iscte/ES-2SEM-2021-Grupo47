package classes;


import java.io.File;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;

import extraction.ExtractMetrics;

import java.util.regex.Pattern;

/**
 * Esta classe e responsavel por obter diferentes informacoes perante um ojeto File dado tal como: nome de uma classe presente no ficheiro
 * com a formatacao correta, nome do pacote a que o ficheiro .java pertence e qual o nome que vai ser dado ao ficheiro excel gerado 
 * atraves da extracao de metricas deste projeto em que o ficheiro esta inserido
 * @author Vasco Fontoura
 * @version 1
 *
 */
public class NameByFile {
	
	private File fileToExtract;
	private static final String SRC = "src", RESULTS_ = "results_", FILE_TYPE = ".xlsx", EMPTY_PATH = "";

	/**
	 * Getter para o file dado
	 * @return um ficheiro dado
	 */
	public File getFileToExtract() {
		return fileToExtract;
	}

	/**
	 * Setter para o file dado
	 * @param fileToExtract um dado ficheiro
	 */
	public void setFileToExtract(File fileToExtract) {
		this.fileToExtract = fileToExtract;
	}
	
	/**
	 * Metodo responsavel por retornar o nome de uma dada classe que esta inserida no ficheiro, 
	 * caso o nome da da classe corresponda ao nome do ficheiro a formatacao entao o nome da classe sera o nome do ficheiro sem ".java"
	 * caso seja uma inner classe a formatacao sera:
	 *  nome_do_ficheiro+"."+nom_da_inner_class
	 * @param classFromFile uma dada classe ou interface
	 * @return o nome da classe na correta formatacao
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
	 * @return o nome do enumerado na correta formatacao
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
	 * Metodo responsavel por retornar o nome do pacote a que o ficheiro .java dado pertence
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
	 * Verifica se o ficheiro dado pertence ao 'Default Package'
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
	 * Metodo responsavel por retornar o nome do ficheiro excel onde serao mostradas as metricas extraidas
	 * @return nome do ficheiro dado na correta formatacao
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