
package Uno;

import javax.swing.*;
import java.awt.*;

public class Tela {

    private JFrame janela;

    private JPanel painelCentro;
    private JPanel painelMesa;
    private JPanel painelMao;

    private JScrollPane rolagemMao;

    private JButton botaoBaralho;

    private JLabel textoPilhaJogo;
    private JLabel textoBot;
    private JLabel textoStatus;

    private Jogo jogo;

    private final String[] cores = {
        "vermelho", "azul", "verde", "amarelo"
    };

    public Tela() {

        jogo = new Jogo();

        // Criar janela
        janela = new JFrame("UNO");

        janela.setSize(1000, 700);
        janela.setMinimumSize(new Dimension(800, 600));
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(new BorderLayout(15, 15));

        // Painel central
        painelCentro = new JPanel(new BorderLayout(20, 20));

        painelMesa = new JPanel(new GridLayout(1, 3, 20, 20));

        // Painel da mão
        painelMao = new JPanel();

        painelMao.setLayout(
            new BoxLayout(painelMao, BoxLayout.X_AXIS)
        );

        // Barra de rolagem
        rolagemMao = new JScrollPane(painelMao);

        rolagemMao.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        rolagemMao.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_NEVER
        );

        rolagemMao.setPreferredSize(new Dimension(950, 220));

        // Informações do bot
        textoBot = new JLabel("", SwingConstants.CENTER);

        textoBot.setFont(new Font("Arial", Font.BOLD, 22));

        janela.add(textoBot, BorderLayout.NORTH);

        // Botão de compra
        botaoBaralho = new JButton("COMPRAR");

        botaoBaralho.setPreferredSize(new Dimension(140, 180));

        botaoBaralho.setFont(new Font("Arial", Font.BOLD, 18));

        botaoBaralho.addActionListener(e -> {

            if (jogo.getJogadorAtualIndex() == 0) {

                boolean comprou = jogo.comprarCarta();

                if (!comprou) {
                    JOptionPane.showMessageDialog(
                        janela,
                        "O baralho está vazio!"
                    );
                }

                atualizarTela();
                executarTurnoBot();
            }
        });

        // Texto da pilha
        textoPilhaJogo = new JLabel("", SwingConstants.CENTER);

        textoPilhaJogo.setFont(
            new Font("Arial", Font.BOLD, 24)
        );

        // Texto de status
        textoStatus = new JLabel("", SwingConstants.CENTER);

        textoStatus.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        // Montar mesa
        painelMesa.add(botaoBaralho);
        painelMesa.add(textoPilhaJogo);
        painelMesa.add(textoStatus);

        painelCentro.add(painelMesa, BorderLayout.CENTER);

        janela.add(painelCentro, BorderLayout.CENTER);

        // Adicionar mão do jogador
        janela.add(rolagemMao, BorderLayout.SOUTH);

        atualizarTela();

        janela.setVisible(true);
    }

    // Atualizar toda a interface
    public void atualizarTela() {

        Jogador humano = jogo.getJogadorHumano();
        Jogador bot = jogo.getJogadorBot();

        // Atualizar quantidade de cartas do bot
        textoBot.setText(
            "COMPUTADOR - " + bot.getQuantidadeMao() + " cartas"
        );

        // Atualizar carta do topo
        Carta topo = jogo.getTopoJogo();

        textoPilhaJogo.setText(
            "<html><center>PILHA DE JOGO<br><br>"
            + topo.getValor()
            + "<br><br>Cor atual: "
            + jogo.getCorAtual().toUpperCase()
            + "</center></html>"
        );

        textoPilhaJogo.setForeground(
            obterCor(jogo.getCorAtual())
        );

        // Atualizar botão de compra
        botaoBaralho.setText(
            "<html><center>COMPRAR<br><br>Cartas: "
            + jogo.getQuantidadeBaralho()
            + "</center></html>"
        );

        botaoBaralho.setEnabled(
            !jogo.isGameOver()
            && jogo.getJogadorAtualIndex() == 0
        );

        // Atualizar status
        if (jogo.isGameOver()) {

            textoStatus.setText("FIM DE JOGO!");

        } else if (jogo.getJogadorAtualIndex() == 0) {

            textoStatus.setText("SUA VEZ!");

        } else {

            textoStatus.setText("VEZ DO COMPUTADOR...");
        }

        // Limpar cartas antigas
        painelMao.removeAll();

        // Criar botões das cartas do jogador
        for (int i = 0; i < humano.getQuantidadeMao(); i++) {

            final int indice = i;

            Carta carta = humano.getCarta(i);

            JButton botaoCarta = new JButton(
                "<html><center>"
                + carta.getCor().toUpperCase()
                + "<br><br>"
                + carta.getValor()
                + "</center></html>"
            );

            botaoCarta.setPreferredSize(
                new Dimension(110, 155)
            );

            botaoCarta.setMinimumSize(
                new Dimension(110, 155)
            );

            botaoCarta.setMaximumSize(
                new Dimension(110, 155)
            );

            botaoCarta.setBackground(
                obterCor(carta.getCor())
            );

            botaoCarta.setForeground(
                carta.getCor().equals("amarelo")
                    ? Color.BLACK : Color.WHITE
            );

            botaoCarta.setFont(
                new Font("Arial", Font.BOLD, 14)
            );

            botaoCarta.setFocusPainted(false);

            botaoCarta.setEnabled(
                !jogo.isGameOver()
                && jogo.getJogadorAtualIndex() == 0
            );

            botaoCarta.addActionListener(e -> {

                String corEscolhida = null;

                // Verificar se é coringa
                if (carta.getCor().equals("sem cor")) {

                    corEscolhida = (String) JOptionPane.showInputDialog(
                        janela,
                        "Escolha a nova cor:",
                        "Coringa",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        cores,
                        cores[0]
                    );

                    if (corEscolhida == null) {
                        return;
                    }
                }

                boolean jogou = jogo.jogarCarta(
                    indice,
                    corEscolhida
                );

                if (!jogou) {

                    JOptionPane.showMessageDialog(
                        janela,
                        "Essa carta não pode ser jogada!"
                    );

                } else {

                    atualizarTela();

                    verificarVitoria();

                    executarTurnoBot();
                }
            });

            painelMao.add(Box.createHorizontalStrut(8));

            painelMao.add(botaoCarta);
        }

        painelMao.revalidate();
        painelMao.repaint();
    }

    // Executar a vez do computador
    private void executarTurnoBot() {

        if (jogo.isGameOver()) {
            verificarVitoria();
            return;
        }

        if (jogo.getJogadorAtualIndex() != 1) {
            return;
        }

        Timer temporizador = new Timer(800, e -> {

            Timer timer = (Timer) e.getSource();

            timer.stop();

            if (!jogo.isGameOver()
                    && jogo.getJogadorAtualIndex() == 1) {

                jogo.jogarBot();

                atualizarTela();

                verificarVitoria();

                // Se o bot continuar com a vez, jogar novamente
                if (!jogo.isGameOver()
                        && jogo.getJogadorAtualIndex() == 1) {

                    executarTurnoBot();
                }
            }
        });

        temporizador.setRepeats(false);
        temporizador.start();
    }

    // Verificar se alguém venceu
    private void verificarVitoria() {

        if (jogo.isGameOver()) {

            String vencedor = jogo.getVencedor();

            JOptionPane.showMessageDialog(
                janela,
                vencedor + " venceu o jogo!",
                "Fim de jogo",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Converter nome da cor para Color
    private Color obterCor(String cor) {

        switch (cor.toLowerCase()) {

            case "vermelho":
                return Color.RED;

            case "azul":
                return Color.BLUE;

            case "verde":
                return new Color(0, 150, 0);

            case "amarelo":
                return Color.YELLOW;

            default:
                return Color.GRAY;
        }
    }
}
