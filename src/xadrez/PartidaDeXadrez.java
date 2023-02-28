package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import campodejogo.Peca;
import campodejogo.Posicao;
import campodejogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez vuneravelEnPasant;
	private PecaDeXadrez promocao;

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

	public PecaDeXadrez getVuneravelEnPasant() {
		return vuneravelEnPasant;
	}

	public PecaDeXadrez getPromocao() {
		return promocao;
	}

	// Estrutura de criação das peças no tabuleiro!!
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] pmat = new PecaDeXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int i = 0; i < tabuleiro.getLinha(); i++) {
			for (int j = 0; j < tabuleiro.getColuna(); j++) {
				pmat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return pmat;
	}

	// Método para imprimir, na tela, os possiveis movimentos de uma peça;
	// foi criado uma variavel para receber a posição de origem da peça;
	// depois usamos o método validarOrigemPosicao, para validadar a posição;
	// da peça na matriz booleana do metodo;
	public boolean[][] possiveisMovimentos(PosicaoNoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	public PecaDeXadrez movimentosDoXadrez(PosicaoNoXadrez posicaoOrigem, PosicaoNoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca pecaCapturada = facaMover(origem, destino);

		if (testCheck(jogadorAtual)) {
			desfazMover(origem, destino, pecaCapturada);
			throw new XadrezException("Vc nao pode se colocar em check SEU BURRO!!!");
		}

		PecaDeXadrez pecaQueMoveu = (PecaDeXadrez) tabuleiro.peca(destino);

		// Movimento especial promoção
		promocao = null;
		if (pecaQueMoveu instanceof Peao) {
			if ((pecaQueMoveu.getCor() == Cor.BRANCO && destino.getLinha() == 0)
					|| (pecaQueMoveu.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promocao = (PecaDeXadrez)tabuleiro.peca(destino);
				promocao = substituirPecaPromovida("Q");
			}
		}

		check = (testCheck(oponente(jogadorAtual))) ? true : false;

		if (testCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// En Pasant
		if (pecaQueMoveu instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			vuneravelEnPasant = pecaQueMoveu;
		} else {
			vuneravelEnPasant = null;
		}

		return (PecaDeXadrez) pecaCapturada;
	}

	public PecaDeXadrez substituirPecaPromovida(String type)  {
		if (promocao == null) {
			throw new IllegalStateException("Não há peca a ser promovida");
		}
		if (!type.equals("B") && !type.equals("T") && !type.equals("C") && !type.equals("Q")) {
			throw new XadrezException("Tipo invalido para promoção");
		}

		Posicao pos = promocao.getPosicaoNoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaDeXadrez novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaDeXadrez novaPeca(String type, Cor cor) {
		if(type.equals("B")) return new Bispo(tabuleiro, cor);
		if(type.equals("Q")) return new Rainha(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peca facaMover(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(origem);
		p.aumentarContagemMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// Jogada especial Roque movimento da torre ao lado do rei (pequeno)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.aumentarContagemMovimentos();
		}

		// Jogada especial Roque movimento da torre ao lado da rainha (grande)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.aumentarContagemMovimentos();
		}

		// En pasant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazMover(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.diminuirContagemMovimentos();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// Jogada especial Roque movimento da torre ao lado do rei (pequeno)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.diminuirContagemMovimentos();
		}

		// Jogada especial Roque movimento da torre ao lado da rainha (grande)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.diminuirContagemMovimentos();
		}

		// En pasant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == vuneravelEnPasant) {
				PecaDeXadrez peao = (PecaDeXadrez) tabuleiro.removerPeca(destino);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, peaoPosicao);
			}
		}

	}

	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peca na posicao de origem!!");
		}
		// no proximo é feita a operação de pegar a peça na atual posição pelo
		// tabuleiro.peca(posicao),
		// é feito o Downcast para a classe generica PecaDeXadrez,
		// é testado a cor atraves do getCor(),
		// por fim é checado se a peça atual é diferente do atributo jogadorAtual e é
		// lançada uma exception.
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua!!!");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("Não exite movimento possivel para esta peça.");
		}
	}

	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possiveisMovimentos(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para essa posição.");
		}
	}

	// Método com expressão condicional ternária que define qual a cor da peça no
	// turno;
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaDeXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o Rei " + cor + " no tabuleiro.");
	}

	private boolean testCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoNoXadrez().toPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Cor cor) {
		if (!testCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinha(); i++) {
				for (int j = 0; j < tabuleiro.getColuna(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoNoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = facaMover(origem, destino);
						boolean testCheck = testCheck(cor);
						desfazMover(origem, destino, pecaCapturada);
						if (!testCheck) {
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
		colocarPecaNova('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarPecaNova('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarPecaNova('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarPecaNova('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarPecaNova('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarPecaNova('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarPecaNova('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarPecaNova('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarPecaNova('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarPecaNova('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarPecaNova('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarPecaNova('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarPecaNova('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarPecaNova('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarPecaNova('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarPecaNova('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarPecaNova('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarPecaNova('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}

}
