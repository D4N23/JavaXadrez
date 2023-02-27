package xadrez.pecas;

import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	} 
	
	@Override
	public String toString() {
		return "C";
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
	
		p.setValor(posicao.getLinha() -1, posicao.getColuna()-2 );
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha() -2, posicao.getColuna()-1 );
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha()-2, posicao.getColuna() +1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha()+2, posicao.getColuna() -1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha() +1, posicao.getColuna() +2);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha() +2, posicao.getColuna() +1);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha()-1, posicao.getColuna() +2);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		p.setValor(posicao.getLinha()+1, posicao.getColuna() -2);
		if(getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		return mat;
	}
	

}
