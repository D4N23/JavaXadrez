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
	
	//Método para imprimir, na tela, os possiveis movimentos de uma peça;
	//foi criado uma variavel para receber a posição de origem da peça;
	//depois usamos o método validarOrigemPosicao, para validadar a posição;
	//da peça na matriz booleana do metodo;
	public boolean[][] possiveisMovimentos(PosicaoNoXadrez origemPosicao){
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	public PecaDeXadrez movimentosDoXadrez(PosicaoNoXadrez posicaoOrigem, PosicaoNoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
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
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("Não exite movimento possivel para esta peça.");
		}
	}
	
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).possiveisMovimentos(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para essa posição.");
		}
	}
	
	private void colocarPecaNova(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoNoXadrez(coluna, linha).toPosicao());
	}
	
	private void configuracaoInicial() {
		colocarPecaNova('a' , 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarPecaNova('h' , 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarPecaNova('d' , 1, new Rei(tabuleiro, Cor.BRANCO));
		colocarPecaNova('a' , 8, new Torre(tabuleiro, Cor.PRETO));
		colocarPecaNova('h' , 8, new Torre(tabuleiro, Cor.PRETO));
		colocarPecaNova('e' , 8, new Rei(tabuleiro, Cor.PRETO));
	}

}
