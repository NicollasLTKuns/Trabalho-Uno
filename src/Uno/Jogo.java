package Uno;

public class Jogo {
	private Baralho baralho;
	private Pilha pilhaJogo;
	private Carta[] mao;
	private int quantidadeMao = 0;
	
	public Jogo() {
		baralho = new Baralho();
		pilhaJogo = new Pilha();
		baralho.embaralhar();
		mao = new Carta[108];
		
		for (int i = 0; i < 7; i++) {
			mao[quantidadeMao] = baralho.comprar();
			quantidadeMao++;
		}
		
		pilhaJogo.empilhar(baralho.comprar());
	}
	
	public void comprar() {
		if (baralho.getQuantidade() > 0) {
			mao[quantidadeMao] = baralho.comprar();
			quantidadeMao++;
		}
	}
	
	public void jogar(int indice) {
		if (indice >= 0 && indice < quantidadeMao) {
			Carta selecionada = mao[indice];
			Carta verificacao = pilhaJogo.verTopo();
			boolean jogadaValida = false;
			
			if (selecionada.getCor().equals(verificacao.getCor())
					|| selecionada.getValor().equals(verificacao.getValor())
					|| selecionada.getValor().equals("troca de cor")
					|| selecionada.getValor().equals("+4")
				) {
				jogadaValida = true;
			}
			if (jogadaValida == true) {
				pilhaJogo.empilhar(selecionada);
				removerDaMao(indice);
			}
		}
	}
	
	private void removerDaMao(int indice) {
		for (int i = indice; i < quantidadeMao - 1; i++) {
			mao[i] = mao[i + 1];
		}
		mao[quantidadeMao - 1] =  null;
		quantidadeMao--;
	}
	
	public Carta getTopoJogo() {		
		return pilhaJogo.verTopo();
	}
	
	public int getQuantidadeMao() {
		return quantidadeMao;
	}
	
	public int getQuantidadeBaralho() {
		return baralho.getQuantidade();
	}
	
	public Carta getCartaMao(int indice) {
		return mao[indice];
	}
}
