package campodejogo;

public abstract class Peca {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	//Operação abstrata que retorna uma matriz booleana, 
	//definindo os movimentos possiveis de uma pessa no tipo generico.
	public abstract boolean[][] possiveisMovimentos();
	
	//Metodo concreto que retorna o valor booleano refente aos movimentos das peças,
	//retornando os valores da matriz booleana presentes no metodo abstrato. 
	public boolean possiveisMovimentos(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}
	
	//Metodo para saber se existe algum movimento possivel para a peça.
	//Chama o metodo abstrato possiveisMovimentos, retorna um matriz de boolean;
	//Varre a matriz e verifica se existe o valor posicao que seja verdadeiro.
	//Se nenhum valor na linha 'i' e coluna 'j' for verdadeiro, retorna false.
	public boolean existeMovimentoPossivel() {
		boolean[][] mat = possiveisMovimentos();
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
