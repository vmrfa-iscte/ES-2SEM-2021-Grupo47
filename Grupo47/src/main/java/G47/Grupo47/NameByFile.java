package G47.Grupo47;


import java.io.File;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import java.util.regex.Pattern;

public class NameByFile {
	private File fileToExtract;
	private static final String SRC = "src", RESULTS_ = "results_", FILE_TYPE = ".xlsx", EMPTY_PATH = "";

	public File getFileToExtract() {
		return fileToExtract;
	}

	public void setFileToExtract(File fileToExtract) {
		this.fileToExtract = fileToExtract;
	}

	// Retorna o nome utilizado para definir a classe no excel através do nome da classe e do nome do ficheiro
	public String getClassName(ClassOrInterfaceDeclaration classFromFile) {
		if (classFromFile.getNameAsString()
				.equals(fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING)))
			return classFromFile.getNameAsString();
		else
			return fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING) + "."
					+ classFromFile.getNameAsString();
	}

	// Faz o mesmo que o método em cima mas para classes que sejam Enumerados
	public String getClassNameForEnum(EnumDeclaration enumFromFile) {
		if (enumFromFile.getNameAsString()
				.equals(fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING)))
			return enumFromFile.getNameAsString();
		else
			return fileToExtract.getName().replace(ExtractMetrics.JAVA_FILE, ExtractMetrics.EMPTY_STRING) + "."
					+ enumFromFile.getNameAsString();
	}

	// Retorna o nome do pacote segundo um ficheiro
	public String getPackageName() {
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

	// Verifica se a classe está no pacote pré definido
	public boolean isDefaultPackage(String packageName, File fileToExtract) {
		// caso o caminho dado contenha uma pasta "src" e o nome do pacote esteja vazio quer dizer que está diretamente dentro da pasta "src
		// logo, pertence ao pacote pré-definido
		if (packageName.equals(ExtractMetrics.EMPTY_STRING)
				&& fileToExtract.getAbsolutePath().contains(ExtractMetrics.SRC_DIR))
			return true;
		else
			return false;
	}
	
	// Esta função é utilizada para retornar o nome, e apenas o nome do Ficheiro que foi passado como argumento
	public String getFileName() {
		String[] separated = fileToExtract.getAbsolutePath().split(Pattern.quote(File.separator));
		String fileName = EMPTY_PATH;
		for(int i = 0; i< separated.length;i++) {
			if(separated[i].contains(SRC)) { fileName = RESULTS_+separated[i-1]+FILE_TYPE; }
		}
		return fileName;
	}
}