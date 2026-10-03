package view;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;
import javax.swing.*;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnOlhoSenha;
    private boolean senhaVisivel = false;

    // Cores exatas da nova imagem (nova_2.png)
    private final Color AZUL_NEON   = new Color(0, 136, 255); // Azul brilhante da chave
    private final Color BG_DARK_IMG = new Color(13, 17, 23);   // Fundo escuro
    private final Color BRANCO      = new Color(255, 255, 255);
    
    private final Color AZUL_BOTAO  = new Color(14, 126, 238);
    private final Color BG_CARD     = new Color(15, 20, 28);
    private final Color BG_CAMPO    = new Color(10, 14, 22);
    private final Color CINZA_TEXTO = new Color(160, 175, 200);

    public TelaLogin() {
        setTitle("KeyPasso - Autenticação");
        setSize(850, 520);
        setMinimumSize(new Dimension(800, 480));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fundo Escuro com Gradiente Sutil
        JPanel panelBackground = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(10, 14, 24),
                    getWidth(), getHeight(), new Color(4, 6, 12)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                RadialGradientPaint rgb = new RadialGradientPaint(
                    new Point(120, 100),
                    450f,
                    new float[]{0.0f, 1.0f},
                    new Color[]{new Color(0, 136, 255, 20), new Color(0, 0, 0, 0)}
                );
                g2.setPaint(rgb);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.dispose();
            }
        };
        panelBackground.setLayout(new GridBagLayout());
        add(panelBackground);

     // Cartão Principal Glassmorphism com Linha Divisória Discreta
        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 1. Fundo do Cartão Principal
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

                // 2. Linha Divisória Reta e Discreta (Cinza/Semitransparente)
                int xLinha = (int) (getWidth() * 0.45); // Posição horizontal (35% da largura)
                int margemVertical = 40;                 // Espaço em cima e em baixo para não ir de ponta a ponta

                // Cor cinza suave e sutil (com transparência/alpha de 40)
                g2.setColor(new Color(255, 255, 255, 40)); 
                g2.setStroke(new BasicStroke(1.2f));       // Espessura da linha
                
                // Desenha a linha vertical
                g2.drawLine(xLinha, margemVertical, xLinha, getHeight() - margemVertical);

                // 3. Borda sutil ao redor de todo o cartão
                g2.setColor(new Color(255, 255, 255, 12));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);

                g2.dispose();
            }
        };
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridLayout(1, 2));
        panelCard.setPreferredSize(new Dimension(760, 420));
        panelBackground.add(panelCard);

        // ==========================================
        // PAINEL ESQUERDO: LOGO DA CHAVE 
        // ==========================================
        JPanel panelEsquerda = new JPanel(new GridBagLayout());
        panelEsquerda.setOpaque(false);

        GridBagConstraints gbcEsq = new GridBagConstraints();
        gbcEsq.gridx = 0;
        gbcEsq.anchor = GridBagConstraints.CENTER;

     // DESENHO DA CHAVE 
        JPanel panelLogo = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = getWidth() / 2 - 25;
                int cy = getHeight() / 2;

                // Cor Azul Escuro para a haste e o anel externo
                Color AZUL_ESCURO = new Color(12, 38, 75);

                // 1. Anel Circular Externo (AZUL ESCURO)
                g2.setColor(AZUL_ESCURO);
                g2.setStroke(new BasicStroke(7.0f)); 
                g2.drawArc(cx - 45, cy - 45, 90, 91, 28, 360);
                
                // 2. Haste Principal da Chave (AZUL ESCURO com ponta direita arredondada)
                // Recuamos para 'cx - 15' (dentro do disco) e esticamos a largura para 101.
                // Os cantos (12, 12) arredondam a ponta direita visível.
                g2.setColor(AZUL_ESCURO);
                g2.fillRoundRect(cx - 15, cy - 8, 108, 12, 12, 12);

                // 3. O Segredo em formato "U"
                int xInicioU = cx + 56; // Início do U na haste
                int yTopU = cy + 4;     // Topo do U (onde encosta na haste principal)
                int larguraU = 19;     // Largura total do "U"
                int alturaU = 14;      // Altura/profundidade do "U"

                // >>> COR Trocada para Azul Neon <<<
                g2.setColor(AZUL_NEON); 
                g2.setStroke(new BasicStroke(7.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int[] xPoints = {xInicioU, xInicioU, xInicioU + larguraU, xInicioU + larguraU};
                int[] yPoints = {yTopU, yTopU + alturaU, yTopU + alturaU, yTopU};

                g2.drawPolyline(xPoints, yPoints, 4);

                // RESTAURA O STROKE PADRÃO
                g2.setStroke(new BasicStroke(1.0f));

                // 4. Disco Central (AMARELO NEON) - Desenhado por cima para esconder a base da haste
                g2.setColor(new Color(255, 230, 0)); // Amarelo vibrante/neon
                g2.fillOval(cx - 22, cy - 22, 44, 44);

                // 5. Furo Centro Escuro
                g2.setColor(BG_DARK_IMG);
                g2.fillOval(cx - 9, cy - 9, 18, 18);

                g2.dispose();
            }
        };

        panelLogo.setOpaque(false);
        panelLogo.setPreferredSize(new Dimension(220, 130));

        gbcEsq.gridy = 0;
        gbcEsq.insets = new Insets(0, 0, 5, 0);
        panelEsquerda.add(panelLogo, gbcEsq);

        // Texto "KeyPasso"
     // Texto "KeyPasso" Bicolor (Mantém a estrutura original intacta)
        JLabel lblTextoLogo = new JLabel("<html><span style='color: #FFFFFF;'>Key</span><span style='color: #0C264B;'>"
        		+ "<b>Passo</b></span><span style='color: #FFE600;'>"
        		+ "<b>*</b></span></html>");
        lblTextoLogo.setFont(new Font("Segoe UI", Font.PLAIN, 28));

        gbcEsq.gridy = 1;
        gbcEsq.anchor = GridBagConstraints.WEST;
        gbcEsq.insets = new Insets(0, 30, 0, 0); // Mantém a margem que deu certo para mover à esquerda

        panelEsquerda.add(lblTextoLogo, gbcEsq);
        gbcEsq.anchor = GridBagConstraints.CENTER;
        
        // Reseta o anchor para CENTER para não afetar os próximos componentes
        gbcEsq.anchor = GridBagConstraints.CENTER;

        panelCard.add(panelEsquerda);

        // ==========================================
        // PAINEL DIREITO: FORMULÁRIO DE LOGIN
        // ==========================================
        JPanel panelDireita = new JPanel(new GridBagLayout());
        panelDireita.setOpaque(false);

        GridBagConstraints gbcDir = new GridBagConstraints();
        gbcDir.fill = GridBagConstraints.HORIZONTAL;
        gbcDir.gridx = 0;
        gbcDir.insets = new Insets(6, 35, 6, 35);

     // Cabeçalho com Indicador de Foco Lateral (Glow Bar)
        JPanel panelCabecalhoForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Barra Indicadora Neon à esquerda (Espessura 4px, altura proporcional ao bloco)
                g2.setColor(AZUL_NEON);
                g2.fillRoundRect(0, 4, 4, getHeight() - 8, 4, 4);

                g2.dispose();
            }
        };
        panelCabecalhoForm.setOpaque(false);
        panelCabecalhoForm.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0)); // Afasta o texto da barra

        JPanel panelTextoCabecalho = new JPanel();
        panelTextoCabecalho.setLayout(new BoxLayout(panelTextoCabecalho, BoxLayout.Y_AXIS));
        panelTextoCabecalho.setOpaque(false);

        // Título "ENTRAR" em caixa alta com letter-spacing
        JLabel lblEntrar = new JLabel("<html><span style='letter-spacing: 1.5px; color: #FFFFFF;'>ENTRAR</span><span style='color: #00D4FF;'>*</span></html>");
        lblEntrar.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel("Acesse sua conta para continuar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(130, 150, 175));

        panelTextoCabecalho.add(lblEntrar);
        panelTextoCabecalho.add(Box.createVerticalStrut(2));
        panelTextoCabecalho.add(lblSubtitulo);

        panelCabecalhoForm.add(panelTextoCabecalho);

        gbcDir.gridy = 0;
        gbcDir.anchor = GridBagConstraints.WEST;
        gbcDir.insets = new Insets(20, -50, 18, -20);
        panelDireita.add(panelCabecalhoForm, gbcDir);

     // Recompoe o alinhamento para os próximos componentes
     gbcDir.anchor = GridBagConstraints.CENTER;

     // Campo Usuário
     txtUsuario = criarCampoTexto("Usuário");
     gbcDir.gridy = 1;
     gbcDir.insets = new Insets(5, -50, 10, -20);
     panelDireita.add(txtUsuario, gbcDir);

     // Campo Senha com Olho
     JPanel panelSenhaContainer = new JPanel(new BorderLayout());
     panelSenhaContainer.setOpaque(false);

     txtSenha = criarCampoSenha();
     btnOlhoSenha = criarBotaoOlho();

     panelSenhaContainer.add(txtSenha, BorderLayout.CENTER);
     panelSenhaContainer.add(btnOlhoSenha, BorderLayout.EAST);

     gbcDir.gridy = 2;
     gbcDir.insets = new Insets(5, -50, 4, -35);
     panelDireita.add(panelSenhaContainer, gbcDir);

     // Botão Entrar
     btnLogin = criarBotaoEspelhado("Entrar");
     gbcDir.gridy = 4;
     gbcDir.insets = new Insets(15, -15, 8, 40);
     panelDireita.add(btnLogin, gbcDir);

     // Esqueceu a Senha (ABAIXO DO BOTÃO ENTRAR)
     JLabel lblEsqueceu = new JLabel("Esqueceu a senha?", SwingConstants.CENTER);
     lblEsqueceu.setFont(new Font("Segoe UI", Font.PLAIN, 11));
     lblEsqueceu.setForeground(CINZA_TEXTO);
     lblEsqueceu.setCursor(new Cursor(Cursor.HAND_CURSOR));
     gbcDir.gridy = 5;
     gbcDir.insets = new Insets(5, -15, 25, 40);
     panelDireita.add(lblEsqueceu, gbcDir);

     // Botões Redes Sociais
     JPanel panelRedes = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
     panelRedes.setOpaque(false);

     panelRedes.add(criarBotaoSocial("Google", "https://google.com"));
     panelRedes.add(criarBotaoSocial("Instagram", "https://instagram.com"));
     panelRedes.add(criarBotaoSocial("GitHub", "https://github.com"));

     gbcDir.gridy = 6;
     gbcDir.insets = new Insets(-10, -15, 30, 35);
     panelDireita.add(panelRedes, gbcDir);

     panelCard.add(panelDireita);
    }

    private JTextField criarCampoTexto(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(BG_CAMPO);
        field.setForeground(CINZA_TEXTO);
        field.setCaretColor(AZUL_NEON);
        field.setPreferredSize(new Dimension(0, 38));

        Color bordaPadrao = new Color(35, 48, 70);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(BRANCO);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AZUL_NEON, 1, true),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(CINZA_TEXTO);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
        });
        return field;
    }

    private JPasswordField criarCampoSenha() {
        JPasswordField field = new JPasswordField("Senha");
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(BG_CAMPO);
        field.setForeground(CINZA_TEXTO);
        field.setCaretColor(AZUL_NEON);
        field.setEchoChar((char) 0);
        field.setPreferredSize(new Dimension(0, 38));

        Color bordaPadrao = new Color(35, 48, 70);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String senhaStr = new String(field.getPassword());
                if (senhaStr.equals("Senha")) {
                    field.setText("");
                    field.setForeground(BRANCO);
                    if (!senhaVisivel) field.setEchoChar('•');
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AZUL_NEON, 1, true),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                String senhaStr = new String(field.getPassword());
                if (senhaStr.isEmpty()) {
                    field.setText("Senha");
                    field.setForeground(CINZA_TEXTO);
                    field.setEchoChar((char) 0);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
        });
        return field;
    }

    private JButton criarBotaoOlho() {
        JButton btn = new JButton() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getModel().isRollover() ? AZUL_NEON : CINZA_TEXTO);
                g2.setStroke(new BasicStroke(1.8f));

                int w = getWidth();
                int h = getHeight();

                g2.drawArc(w / 2 - 9, h / 2 - 6, 18, 12, 0, 180);
                g2.drawArc(w / 2 - 9, h / 2 - 6, 18, 12, 0, -180);
                g2.fillOval(w / 2 - 3, h / 2 - 3, 6, 6);

                if (!senhaVisivel) {
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(w / 2 - 8, h / 2 + 7, w / 2 + 8, h / 2 - 7);
                }

                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(38, 38));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            senhaVisivel = !senhaVisivel;
            String senhaStr = new String(txtSenha.getPassword());
            if (!senhaStr.equals("Senha")) {
                txtSenha.setEchoChar(senhaVisivel ? (char) 0 : '•');
            }
            btn.repaint();
        });

        return btn;
    }

    private JButton criarBotaoEspelhado(String texto) {
        JButton btn = new JButton(texto) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                boolean hover = getModel().isRollover();

                Color cor1 = hover ? new Color(0, 160, 255) : AZUL_BOTAO;
                Color cor2 = hover ? new Color(0, 100, 220) : new Color(10, 80, 180);

                GradientPaint gp = new GradientPaint(0, 0, cor1, 0, h, cor2);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 14, 14));

                GradientPaint gpReflexo = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 110),
                    0, h / 2, new Color(255, 255, 255, 15)
                );
                g2.setPaint(gpReflexo);
                g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h / 2 - 1, 12, 12));

                g2.setColor(new Color(255, 255, 255, hover ? 160 : 90));
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, 14, 14));

                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int xText = (w - fm.stringWidth(getText())) / 2;
                int yText = (h + fm.getAscent() - fm.getDescent()) / 2;

                g2.setColor(BRANCO);
                g2.drawString(getText(), xText, yText);

                g2.dispose();
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton criarBotaoSocial(String rede, String url) {
        JButton btn = new JButton() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover();

                g2.setColor(hover ? AZUL_NEON : BG_CAMPO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2.setColor(hover ? AZUL_NEON : new Color(40, 55, 80));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                g2.setColor(hover ? BG_DARK_IMG : BRANCO);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;

                switch (rede) {
                    case "Google":
                        g2.drawString("G", cx - fm.stringWidth("G") / 2, cy + 5);
                        break;
                    case "Facebook":
                        g2.drawString("f", cx - fm.stringWidth("f") / 2, cy + 5);
                        break;
                    case "Instagram":
                        g2.setStroke(new BasicStroke(1.6f));
                        g2.drawRoundRect(cx - 7, cy - 7, 14, 14, 4, 4);
                        g2.drawOval(cx - 3, cy - 3, 6, 6);
                        g2.fillOval(cx + 3, cy - 5, 2, 2);
                        break;
                    case "GitHub":
                        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                        fm = g2.getFontMetrics();
                        g2.drawString("GH", cx - fm.stringWidth("GH") / 2, cy + 4);
                        break;
                }

                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(38, 38));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}