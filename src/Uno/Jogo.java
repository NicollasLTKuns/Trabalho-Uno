
package Uno;

import java.util.Random;

public class Jogo {

    private Baralho baralho;
    private Pilha pilhaJogo;

    private Jogador[] jogadores = new Jogador[2];

    private int jogadorAtual = 0;

    private String corAtual;

    private boolean jogoFinalizado = false;

    private String[] cores = {
        "vermelho", "verde", "azul", "amarelo"
    };

    public Jogo() {

        baralho = new Baralho();
        pilhaJogo = new Pilha();

        jogadores[0] = new Jogador("Jogador", false);
        jogadores[1] = new Jogador("Computador", true);

        baralho.embaralhar();

        // Distribuir 7 cartas para cada jogador
        for (int i = 0; i < 7; i++) {

            jogadores[0].adicionarCarta(baralho.comprar());
            jogadores[1].adicionarCarta(baralho.comprar());
        }

        // Colocar a primeira carta na pilha
        Carta primeiraCarta = baralho.comprar();

        pilhaJogo.empilhar(primeiraCarta);

        if (primeiraCarta.getCor().equals("sem cor")) {

            Random random = new Random();

            corAtual = cores[random.nextInt(cores.length)];

        } else {

            corAtual = primeiraCarta.getCor();
        }
    }

    // Verificar se uma carta pode ser jogada
    private boolean cartaValida(Carta carta) {

        Carta topo = pilhaJogo.verTopo();

        return carta.getCor().equals("sem cor")
                || carta.getCor().equals(corAtual)
                || carta.getValor().equals(topo.getValor());
    }

    // Jogada do jogador humano
    public boolean jogarCarta(int indice, String corEscolhida) {

        if (jogoFinalizado || jogadorAtual != 0) {
            return false;
        }

        return executarJogada(indice, corEscolhida);
    }

    // Executar uma jogada
    private boolean executarJogada(int indice, String corEscolhida) {

        Jogador jogador = jogadores[jogadorAtual];

        Carta selecionada = jogador.getCarta(indice);

        if (selecionada == null || !cartaValida(selecionada)) {
            return false;
        }

        boolean coringa = selecionada.getCor().equals("sem cor");

        // Validar a cor escolhida pelo jogador
        if (coringa) {

            boolean corPermitida = false;

            for (String cor : cores) {
                if (cor.equals(corEscolhida)) {
                    corPermitida = true;
                    break;
                }
            }

            if (!corPermitida) {
                return false;
            }
        }

        // Remover carta da mão
        Carta cartaJogada = jogador.jogarCarta(indice);

        // Adicionar carta à pilha
        pilhaJogo.empilhar(cartaJogada);

        // Atualizar cor atual
        if (coringa) {
            corAtual = corEscolhida;
        } else {
            corAtual = cartaJogada.getCor();
        }

        // Verificar vitória
        if (jogador.getQuantidadeMao() == 0) {
            jogoFinalizado = true;
            return true;
        }

        // Identificar próximo jogador
        int proximoJogador = 1 - jogadorAtual;

        String valor = cartaJogada.getValor();

        // Carta +2
        if (valor.equals("+2")) {

            comprarParaJogador(proximoJogador, 2);

            // O jogador atual continua jogando
            return true;
        }

        // Carta +4
        if (valor.equals("+4")) {

            comprarParaJogador(proximoJogador, 4);

            return true;
        }

        // Bloqueio
        if (valor.equals("bloqueio")) {
            return true;
        }

        // Inverter: com dois jogadores, equivale ao bloqueio
        if (valor.equals("inverter")) {
            return true;
        }

        // Próximo turno
        jogadorAtual = proximoJogador;

        return true;
    }

    // Comprar cartas para determinado jogador
    private void comprarParaJogador(int indiceJogador, int quantidade) {

        Jogador jogador = jogadores[indiceJogador];

        for (int i = 0; i < quantidade; i++) {

            Carta carta = baralho.comprar();

            if (carta != null) {
                jogador.adicionarCarta(carta);
            }
        }
    }

    // Comprar uma carta no turno humano
    public boolean comprarCarta() {

        if (jogoFinalizado || jogadorAtual != 0) {
            return false;
        }

        Carta carta = baralho.comprar();

        if (carta != null) {
            jogadores[0].adicionarCarta(carta);
        }

        // Passar a vez
        jogadorAtual = 1;

        return carta != null;
    }

    // Jogada automática do computador
    public void jogarBot() {

        if (jogoFinalizado || jogadorAtual != 1) {
            return;
        }

        Jogador bot = jogadores[1];

        // Procurar uma carta válida
        for (int i = 0; i < bot.getQuantidadeMao(); i++) {

            Carta carta = bot.getCarta(i);

            if (cartaValida(carta)) {

                String corEscolhida = null;

                // Se for coringa, escolher a cor mais frequente
                if (carta.getCor().equals("sem cor")) {
                    corEscolhida = escolherCorBot();
                }

                executarJogada(i, corEscolhida);

                return;
            }
        }

        // Se não encontrou carta válida, comprar
        Carta comprada = baralho.comprar();

        if (comprada != null) {
            bot.adicionarCarta(comprada);
        }

        // Passar a vez
        jogadorAtual = 0;
    }

    // Escolher a cor mais frequente na mão do bot
    private String escolherCorBot() {

        int[] quantidadeCores = new int[4];

        Jogador bot = jogadores[1];

        for (int i = 0; i < bot.getQuantidadeMao(); i++) {

            Carta carta = bot.getCarta(i);

            for (int j = 0; j < cores.length; j++) {

                if (carta.getCor().equals(cores[j])) {
                    quantidadeCores[j]++;
                }
            }
        }

        int maiorQuantidade = 0;
        int melhorCor = 0;

        for (int i = 0; i < quantidadeCores.length; i++) {

            if (quantidadeCores[i] > maiorQuantidade) {
                maiorQuantidade = quantidadeCores[i];
                melhorCor = i;
            }
        }

        return cores[melhorCor];
    }

    // Getters

    public Jogador getJogadorHumano() {
        return jogadores[0];
    }

    public Jogador getJogadorBot() {
        return jogadores[1];
    }

    public int getJogadorAtualIndex() {
        return jogadorAtual;
    }

    public Carta getTopoJogo() {
        return pilhaJogo.verTopo();
    }

    public String getCorAtual() {
        return corAtual;
    }

    public int getQuantidadeBaralho() {
        return baralho.getQuantidade();
    }

    public boolean isGameOver() {
        return jogoFinalizado;
    }

    public String getVencedor() {

        if (!jogoFinalizado) {
            return null;
        }

        return jogadores[jogadorAtual].getNome();
    }
}
