package Uno;

public class Main {

    public static void main(String[] args) {

        Jogo jogo = new Jogo();

        // TESTE 1: INÍCIO DO JOGO
        System.out.println("=== INÍCIO DO JOGO ===");

        System.out.println("Cartas no baralho: " + jogo.getQuantidadeBaralho());
        System.out.println("Cartas na mão: " + jogo.getQuantidadeMao());
        System.out.println("Carta no topo: " + jogo.getTopoJogo().mostrar());


        // TESTE 2: COMPRAR UMA CARTA
        System.out.println("\n=== COMPRANDO UMA CARTA ===");

        jogo.comprar();

        System.out.println("Cartas no baralho: " + jogo.getQuantidadeBaralho());
        System.out.println("Cartas na mão: " + jogo.getQuantidadeMao());


        // TESTE 3: MOSTRAR CARTAS NA MÃO
        System.out.println("\n=== CARTAS NA MÃO ===");

        for (int i = 0; i < jogo.getQuantidadeMao(); i++) {
            System.out.println("Índice " + i + ": " + jogo.getCartaMao(i).mostrar());
        }


        // TESTE 4: JOGAR UMA CARTA VÁLIDA
        System.out.println("\n=== JOGANDO UMA CARTA ===");

        // Escolha manualmente um índice válido após conferir a mão.
        // Neste exemplo, usamos o índice 0.
        jogo.jogar(0);

        System.out.println("Cartas na mão: " + jogo.getQuantidadeMao());
        System.out.println("Cartas no baralho: " + jogo.getQuantidadeBaralho());
        System.out.println("Nova carta no topo: " + jogo.getTopoJogo().mostrar());


        // TESTE 5: MOSTRAR A MÃO APÓS A JOGADA
        System.out.println("\n=== MÃO APÓS A JOGADA ===");

        for (int i = 0; i < jogo.getQuantidadeMao(); i++) {
            System.out.println("Índice " + i + ": " + jogo.getCartaMao(i).mostrar());
        }


        // TESTE 6: TENTAR UMA JOGADA INVÁLIDA
        System.out.println("\n=== TESTANDO JOGADA INVÁLIDA ===");

        System.out.println("Topo atual: " + jogo.getTopoJogo().mostrar());

        int quantidadeAntes = jogo.getQuantidadeMao();

        // Troque o índice 0 por um índice que tenha uma carta inválida.
        jogo.jogar(0);

        System.out.println("Cartas na mão antes: " + quantidadeAntes);
        System.out.println("Cartas na mão depois: " + jogo.getQuantidadeMao());
        System.out.println("Topo após tentativa: " + jogo.getTopoJogo().mostrar());

    }
}