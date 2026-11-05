package Uno;

import java.util.Random;

public class Baralho {	
	private Carta[] baralho = new Carta[108];
	private String[] cores = {"vermelho", "verde", "azul", "amarelo", "sem cor"};
	private String[] valores = {"1", "2","3","4","5","6","7","8","9","0"};
	private String[] especial = {"+2", "bloqueio", "inverter"};
	private String[] coringa = {"troca de cor", "+4"};
	private int quantidade = 0;
	
	
	public Baralho() {
			for (int i = 0; i < cores.length; i++) {
				if ("sem cor".equals(cores[i])) {
					for (int j = 0; j < coringa.length; j++) {
						for (int k = 0; k < 4; k++) {
							adicionar(cores[i],coringa[j]);								
						}
					}
				} else {			
					for (int j = 0; j < valores.length; j++) {
						if ("0".equals(valores[j])) {
							adicionar(cores[i],valores[j]);
						} else {
							for (int k = 0; k < 2; k++) {
								adicionar(cores[i],valores[j]);								
							}
						}
					}
					for (int j = 0; j < especial.length; j++) {
						for (int k = 0; k < 2; k++) {
							adicionar(cores[i],especial[j]);								
						}
					}
				}
			}
	}
	
	public void embaralhar() {
		Random random = new Random();

		for (int i = quantidade - 1; i > 0; i--) {
			int posicaoAleatoria = random.nextInt(i + 1);
			
			Carta temporaria = baralho[i];
			baralho[i] = baralho[posicaoAleatoria];
			baralho[posicaoAleatoria] = temporaria;				
		}
	}
	
	public void mostrarBaralho() {
		for (int i = 0; i < quantidade; i++) {
			System.out.println(baralho[i].mostrar());
		}
	}
	
	public void adicionar(String cor,String valor) {
		baralho[quantidade] = new Carta(cor, valor);
		quantidade++;
	}
	
	public Carta comprar() {
		if (quantidade > 0) {			
			Carta guardar = baralho[quantidade - 1];
			baralho[quantidade - 1] = null;
			quantidade--;
			return guardar;
		} else {
			return null;			
		}
	}
	
	public int getQuantidade() {
	    return quantidade;
	}
}
