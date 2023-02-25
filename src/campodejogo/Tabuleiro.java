package campodejogo;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new TabuException("Erro ao criar tabuleiro. É necessário que haja pelos menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}
	

	public int getLinha() {
		return linhas;
	}

	public int getColuna() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if(!existePosicao(linha, coluna)) {
			throw new TabuException("Essa posição não existe no tabuleiro");
		}
		return pecas [linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuException("Essa posição não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	//metodo que coloca as peças em uma dada posição no tabuleiro
	public void colocarPeca(Peca peca, Posicao posicao) {
		if(temUmaPeca(posicao)) {
			throw new TabuException("Já existe uma peça na posição " + posicao + " !");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuException("Essa posição não existe no tabuleiro");
		}
		if(peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean existePosicao(int linha, int coluna) {
		
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean existePosicao(Posicao posicao) {
		
		return existePosicao(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean temUmaPeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuException("Essa posição não existe no tabuleiro");
		}
		return peca(posicao) != null;
	}
}
