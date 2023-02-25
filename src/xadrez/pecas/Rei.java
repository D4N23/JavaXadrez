package xadrez.pecas;

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

	//Metodo que implementa a logica de movimentos possiveis da peça;
	//***** ainda temporario. Recebe uma matriz temporia percorrendo todas a linha e colunas do tabuleiro;
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];
		return mat;
	}
	

}
