package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import campodejogo.Peca;
import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if(testCheck(jogadorAtual)) {
			desfazMover(origem, destino, pecaCapturada);
			throw new XadrezException("Vc nao pode se colocar em check SEU BURRO!!!");
		}
		
		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
		proximoTurno();
		}
		return (PecaDeXadrez)pecaCapturada;
	}
	
	private Peca facaMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazMover(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peca na posicao de origem!!");
		}
		//no proximo é feita a operação de pegar a peça na atual posição pelo tabuleiro.peca(posicao),
		//é feito o Downcast para a classe generica PecaDeXadrez,
		//é testado a cor atraves do getCor(),
		//por fim é checado se a peça atual é diferente do atributo jogadorAtual e é lançada uma exception.
		if(jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new  XadrezException("A peça escolhida não é sua!!!");
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
	
	//Método com expressão condicional ternária que define qual a cor da peça no turno;
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO ) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return(cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor()== cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe o Rei " + cor + " no tabuleiro.");
	}
	
	private boolean testCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoNoXadrez().toPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor()== oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasDoOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if(mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Cor cor) {
		if(!testCheck(cor)) {
			return false;
		}
		List<Peca> list =  pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor()== cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for(int i = 0; i < tabuleiro.getLinha(); i++) {
				for(int j = 0; j < tabuleiro.getColuna(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaDeXadrez)p).getPosicaoNoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = facaMover(origem, destino);
						boolean testCheck = testCheck(cor);
						desfazMover(origem, destino, pecaCapturada);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarPecaNova(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoNoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
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
