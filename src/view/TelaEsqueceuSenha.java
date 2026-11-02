package view;

import controller.AuthController;
import util.EmailUtils;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class TelaEsqueceuSenha extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtEmail;
    private JButton btnEnviar;
    private JLabel lblVoltarLogin;

    private AuthController authController = new AuthController();

    // Paleta de Cores (Estilo Dark / Glassmorphism)
    private final Color AZUL_NEON   = new Color(0, 136, 255);
    private final Color BRANCO      = new Color(255, 255, 255);
    private final Color AZUL_BOTAO  = new Color(14, 126, 238);
    private final Color BG_CARD     = new Color(15, 20, 28);
    private final Color BG_CAMPO    = new Color(10, 14, 22);
    private final Color CINZA_TEXTO = new Color(160, 175, 200);

    public TelaEsqueceuSenha() {
        setTitle("KeyPasso - Recuperar Senha");
        setSize(500, 420);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de Fundo com Gradiente
        JPanel panelBackground = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 14, 24), getWidth(), getHeight(), new Color(4, 6, 12));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panelBackground.setLayout(new GridBagLayout());
        add(panelBackground);

        // Card Transparente (Glassmorphism)
        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

                g2.setColor(new Color(255, 255, 255, 12));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridBagLayout());
        panelCard.setPreferredSize(new Dimension(420, 320));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(8, 25, 8, 25);

        // Título
        JLabel lblTitulo = new JLabel("RECUPERAR SENHA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(BRANCO);
        gbc.gridy = 0;
        panelCard.add(lblTitulo, gbc);

        // Subtítulo explicativo
        JLabel lblSub = new JLabel("<html><center style='color: #A0AFC8;'>Digite seu e-mail cadastrado para receber o código de verificação.</center></html>", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 1;
        panelCard.add(lblSub, gbc);

        // Campo de Texto do E-mail
        txtEmail = criarCampoTexto("Seu e-mail cadastrado");
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 25, 15, 25);
        panelCard.add(txtEmail, gbc);

        // Botão Enviar
        btnEnviar = criarBotaoEspelhado("Enviar Código");
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 25, 10, 25);
        panelCard.add(btnEnviar, gbc);

        // Link para Voltar ao Login
        lblVoltarLogin = new JLabel("Voltar ao Login", SwingConstants.CENTER);
        lblVoltarLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblVoltarLogin.setForeground(AZUL_NEON);
        lblVoltarLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        panelCard.add(lblVoltarLogin, gbc);

        panelBackground.add(panelCard);

        // ==========================================
        // FLUXO DO BOTÃO "ENVIAR CÓDIGO"
        // ==========================================
        btnEnviar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            
            if (email.isEmpty() || email.equals("Seu e-mail cadastrado")) {
                JOptionPane.showMessageDialog(this, "Informe o e-mail da sua conta!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Desabilita o botão temporariamente enquanto processa
            btnEnviar.setEnabled(false);
            btnEnviar.setText("Verificando...");

            // Processamento em segundo plano (Thread)
            new Thread(() -> {
                // 1. Verifica no banco se o e-mail existe
                boolean emailExiste = authController.verificarEmailExiste(email);

                if (!emailExiste) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(TelaEsqueceuSenha.this, 
                            "Este e-mail não está cadastrado no KeyPasso. Crie uma conta primeiro!", 
                            "E-mail não encontrado", JOptionPane.ERROR_MESSAGE);
                        btnEnviar.setEnabled(true);
                        btnEnviar.setText("Enviar Código");
                    });
                    return;
                }

                // 2. Se o e-mail existe, gera o código e dispara o e-mail
                SwingUtilities.invokeLater(() -> btnEnviar.setText("Enviando e-mail..."));
                String codigo = EmailUtils.gerarCodigo();
                boolean enviado = EmailUtils.enviarCodigoRecuperacao(email, codigo);

                // 3. Atualiza a tela com o resultado do envio
                SwingUtilities.invokeLater(() -> {
                    btnEnviar.setEnabled(true);
                    btnEnviar.setText("Enviar Código");

                    if (enviado) {
                        JOptionPane.showMessageDialog(TelaEsqueceuSenha.this, 
                            "Código enviado para o seu e-mail!", 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Passa o e-mail e o código gerado para a tela de redefinir
                        new TelaRedefinirSenha(email, codigo).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(TelaEsqueceuSenha.this, 
                            "Falha ao enviar o e-mail. Verifique a sua conexão com a internet.", 
                            "Erro de Envio", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }).start();
        });

        // Evento do clique em "Voltar ao Login"
        lblVoltarLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
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

                g2.setColor(BRANCO); 
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int xText = (w - fm.stringWidth(getText())) / 2;
                int yText = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), xText, yText);
                g2.dispose();
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(0, 40));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}