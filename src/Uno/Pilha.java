package Uno;

public class Pilha {
	private Carta[] cartas = new Carta[108];
	private int quantidade = 0;
	
	public Pilha() {
		
	}
	
	public void empilhar(Carta carta) {
		cartas[quantidade] = carta;
		quantidade++;
	}
	
	public Carta desempilhar() {
		if (quantidade > 0) {			
			Carta guardar = cartas[quantidade - 1];
			cartas[quantidade - 1] = null;
			quantidade--;
			return guardar;
		} else {
			return null;			
		}
	}
	
	public Carta verTopo() {
		if ( quantidade > 0 ) {
			Carta guardar = cartas[quantidade - 1];
			return guardar;			
		} else {
			return null;
		}
	}
	
	public int getQuantidade() {
		return quantidade;
	}
}