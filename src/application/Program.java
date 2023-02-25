package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoNoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {
		 
		Scanner sc = new Scanner(System.in);

		PartidaDeXadrez xadrez = new PartidaDeXadrez();
		
		while(true) {
			try {
				UI.limparTela();	
				UI.printTabuleiro(xadrez.getPecas());
				System.out.println();
				System.out.println("Origem: ");
				PosicaoNoXadrez origem = UI.leiaAPosicao(sc);
				
				System.out.println();
				System.out.println("Destino: ");
				PosicaoNoXadrez destino = UI.leiaAPosicao(sc);
				
				PecaDeXadrez pecaCapturada = xadrez.movimentosDoXadrez(origem, destino);
			}
			catch(XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
