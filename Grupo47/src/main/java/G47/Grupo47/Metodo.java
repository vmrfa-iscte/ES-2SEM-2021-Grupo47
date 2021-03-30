package G47.Grupo47;

public class Metodo {
	
	private int linhas;
	private String pacote;
	private String classe;
	private String nome_metodo;
	
	public Metodo(String nome_metodo,String classe,String pacote, int linhas) {
		this.nome_metodo = nome_metodo;
		this.pacote = pacote;
		this.linhas = linhas;
		this.classe = classe;
	}
	
	public String getClasse() {
		return classe;
	}

	public int getLinhas() {
		return linhas;
	}

	public String getPacote() {
		return pacote;
	}

	public String getNome_metodo() {
		return nome_metodo;
	}
	
	

}
