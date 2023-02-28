package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoNoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		PartidaDeXadrez xadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturada = new ArrayList<>();

		while (!xadrez.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(xadrez, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoNoXadrez origem = UI.leiaAPosicao(sc);

				boolean[][] possiveisMovimentos = xadrez.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(xadrez.getPecas(), possiveisMovimentos);
				System.out.println();
				System.out.print("Destino: ");
				PosicaoNoXadrez destino = UI.leiaAPosicao(sc);

				PecaDeXadrez pecaCapturada = xadrez.movimentosDoXadrez(origem, destino);

				if (pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				if (xadrez.getPromocao() != null) {
					System.out.print("Enter com a peça aser promovida(B/Q/C/T): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("T") && !type.equals("C") && !type.equals("Q")) {
						System.out.print("Valor invalido. Entre com a peça a ser promovida(B/Q/C/T): ");
						type = sc.nextLine().toUpperCase();
					}
					xadrez.substituirPecaPromovida(type);
				}
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.printPartida(xadrez, capturada);
	}

}
