package xadrez.pecas;

import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	//Metodo que implementa a logica de movimentos possiveis da peça;
	//***** ainda temporario. Recebe uma matriz temporia percorrendo todas a linha e colunas do tabuleiro;
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];
		
		Posicao p = new Posicao(0, 0);
	
		//acima
		p.setValor(posicao.getLinha() -1, posicao.getColuna() );
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//abaixo
		p.setValor(posicao.getLinha() +1, posicao.getColuna() );
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//esquerda
		p.setValor(posicao.getLinha(), posicao.getColuna() -1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//direita
		p.setValor(posicao.getLinha(), posicao.getColuna() +1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//NOROESTE
		p.setValor(posicao.getLinha() -1, posicao.getColuna() -1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//NORDESTE
		p.setValor(posicao.getLinha() -1, posicao.getColuna() +1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//SUDOESTE
		p.setValor(posicao.getLinha()+1, posicao.getColuna() -1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		//SUDESTE
		p.setValor(posicao.getLinha()+1, posicao.getColuna() +1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		return mat;
	}
	

}
