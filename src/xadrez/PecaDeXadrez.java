package xadrez;

import campodejogo.Peca;
import campodejogo.Posicao;
import campodejogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{
	
	private Cor cor;
	private int contarMovimento;
	
	//
	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContarMovimento() {
		return contarMovimento;
	}
	
	public void aumentarContagemMovimentos(){
		contarMovimento++;
	}
	
	public void diminuirContagemMovimentos(){
		contarMovimento--;
	}
	
	public PosicaoNoXadrez getPosicaoNoXadrez() {
		return PosicaoNoXadrez.paraPosicao(posicao);
	}
	
	//Metodo para saber se há uma ça do oponente em uma dada posição.
	//checa se a variavel p é diferente de null;
	//checa se p.getCor é diferente da valor cor da peça atual;
	protected boolean pecaDoOponente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
	
}
