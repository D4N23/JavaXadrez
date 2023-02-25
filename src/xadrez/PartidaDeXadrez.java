package xadrez;

import campodejogo.Peca;
import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}
	
	
	//Estrutura de criação das peças no tabuleiro!!
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] pmat = new PecaDeXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for(int i=0; i < tabuleiro.getLinha(); i++) {
			for(int j = 0; j < tabuleiro.getColuna(); j++) {
				pmat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return pmat;
	}
	
	public PecaDeXadrez MovimentosDoXadrez(PosicaoNoXadrez posicaoOrigem, PosicaoNoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarOrigemPosicao(origem);
		Peca pecaCapturada= facaMover(origem, destino);
		return (PecaDeXadrez)pecaCapturada;
	}
	
	private Peca facaMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
	}

	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peca na posicao de origem!!");
		}
	}
	
	private void ColocarPecaNova(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoNoXadrez(coluna, linha).toPosicao());
	}
	
	private void configuracaoInicial() {
		ColocarPecaNova('a' , 1, new Torre(tabuleiro, Cor.BRANCO));
		ColocarPecaNova('h' , 1, new Torre(tabuleiro, Cor.BRANCO));
		ColocarPecaNova('d' , 1, new Rei(tabuleiro, Cor.BRANCO));
		ColocarPecaNova('a' , 8, new Torre(tabuleiro, Cor.PRETO));
		ColocarPecaNova('h' , 8, new Torre(tabuleiro, Cor.PRETO));
		ColocarPecaNova('e' , 8, new Rei(tabuleiro, Cor.PRETO));
	}

}
