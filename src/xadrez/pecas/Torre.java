package xadrez.pecas;

import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre  extends PecaDeXadrez{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "T";
	}
	
	//Metodo que implementa a logica de movimentos possiveis da peça;
		//***** ainda temporario. Recebe uma matriz temporia percorrendo todas a linha e colunas do tabuleiro;
		@Override
		public boolean[][] possiveisMovimentos() {
			boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];
			
			Posicao p = new Posicao(0, 0);
			
			//acima
			p.setValor(posicao.getLinha() - 1, posicao.getColuna());
			while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
				p.setLinha(p.getLinha() - 1);
			}
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//esquerda
			p.setValor(posicao.getLinha(), posicao.getColuna() - 1);
			while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
				p.setColuna(p.getColuna() - 1);
			}
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//direita
			p.setValor(posicao.getLinha(), posicao.getColuna() + 1);
			while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
				p.setColuna(p.getColuna() + 1);
			}
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//abaixo
			p.setValor(posicao.getLinha() + 1, posicao.getColuna());
			while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
				p.setLinha(p.getLinha() + 1);
			}
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			return mat;
		}
	
	

}
