package xadrez.pecas;

import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{
	
	private PartidaDeXadrez pxadrez;
	
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez xadrez) {
		super(tabuleiro, cor);
		pxadrez = xadrez;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContarMovimento() == 0;
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
		
		//Teste de jogada especial.
		
		if(getContarMovimento() == 0 && !pxadrez.getCheck()) {
			//Roque pequeno, lado do Rei.
			Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if(testeTorreRoque(posicaoTorre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			
			//Roque grande, a esquerda do Rei.
			Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if(testeTorreRoque(posicaoTorre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			
		}
		
		return mat;
	}
	

}
