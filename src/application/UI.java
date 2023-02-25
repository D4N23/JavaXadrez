package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PecaDeXadrez;
import xadrez.PosicaoNoXadrez;

public class UI {
	
	
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoNoXadrez leiaAPosicao(Scanner sc) {
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0);
		int linha = Integer.parseInt(s.substring(1));
		return new PosicaoNoXadrez(coluna, linha);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Erro lendo a posição de xadrez. Valores validos são de a1 até h8");
		}
	}
	
	//Metodo pra gerar mais de uma peça na tela
	public static void printTabuleiro(PecaDeXadrez[][] pecas) {
		
		for(int i=0; i < pecas.length; i++ ) {
			System.out.print(8 - i + " ");
			
			for(int j=0; j < pecas.length; j++) {
				printPecas(pecas[i][j]);
				
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	//Metodo que imprime peça por peça 
	private static void printPecas(PecaDeXadrez peca) {
		
		if(peca == null) {
			System.out.print("-");
		}
		else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}

}
