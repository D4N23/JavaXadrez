package xadrez.pecas;

import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "P";
	}
	

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];
		
		Posicao p = new Posicao(0, 0);
		//regra do peao branco
		if(getCor() == Cor.BRANCO) {
			p.setValor(posicao.getLinha() - 1,posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1,posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temUmaPeca(p2) &&  getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() - 1,posicao.getColuna() - 1);
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValor(posicao.getLinha() - 1,posicao.getColuna() + 1);
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}	
		}
		else {
			p.setValor(posicao.getLinha() + 1,posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1,posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temUmaPeca(p2) &&  getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() + 1,posicao.getColuna() - 1);
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValor(posicao.getLinha() + 1,posicao.getColuna() + 1);
			if(getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return mat;
	}
	
	
	

}
