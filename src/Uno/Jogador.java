
package Uno;

public class Jogador {

    private String nome;
    private boolean bot;

    private Carta[] mao;
    private int quantidadeMao = 0;

    public Jogador(String nome, boolean bot) {
        this.nome = nome;
        this.bot = bot;

        mao = new Carta[108];
    }

    public void adicionarCarta(Carta carta) {
        if (carta != null && quantidadeMao < mao.length) {
            mao[quantidadeMao] = carta;
            quantidadeMao++;
        }
    }

    public Carta jogarCarta(int indice) {

        if (indice >= 0 && indice < quantidadeMao) {

            Carta carta = mao[indice];

            for (int i = indice; i < quantidadeMao - 1; i++) {
                mao[i] = mao[i + 1];
            }

            mao[quantidadeMao - 1] = null;
            quantidadeMao--;

            return carta;
        }

        return null;
    }

    public Carta getCarta(int indice) {
        if (indice >= 0 && indice < quantidadeMao) {
            return mao[indice];
        }

        return null;
    }

    public int getQuantidadeMao() {
        return quantidadeMao;
    }

    public String getNome() {
        return nome;
    }

    public boolean isBot() {
        return bot;
    }
}
