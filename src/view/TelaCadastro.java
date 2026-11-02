package view;

import controller.AuthController;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class TelaCadastro extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmaSenha;
    private JButton btnCadastrar;
    private JLabel lblVoltarLogin;

    private AuthController authController = new AuthController();

    private final Color AZUL_NEON   = new Color(0, 136, 255);
    private final Color BRANCO      = new Color(255, 255, 255);
    private final Color AZUL_BOTAO  = new Color(14, 126, 238);
    private final Color BG_CARD     = new Color(15, 20, 28);
    private final Color BG_CAMPO    = new Color(10, 14, 22);
    private final Color CINZA_TEXTO = new Color(160, 175, 200);

    public TelaCadastro() {
        setTitle("KeyPasso - Criar Conta");
        setSize(850, 580);
        setMinimumSize(new Dimension(800, 520));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

                RadialGradientPaint rgb = new RadialGradientPaint(
                    new Point(120, 100), 450f, new float[]{0.0f, 1.0f},
                    new Color[]{new Color(0, 136, 255, 20), new Color(0, 0, 0, 0)}
                );
                g2.setPaint(rgb);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panelBackground.setLayout(new GridBagLayout());
        add(panelBackground);

        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

                int xLinha = (int) (getWidth() * 0.40);
                g2.setColor(new Color(255, 255, 255, 40)); 
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawLine(xLinha, 40, xLinha, getHeight() - 40);

                g2.setColor(new Color(255, 255, 255, 12));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridLayout(1, 2));
        panelCard.setPreferredSize(new Dimension(760, 480));
        panelBackground.add(panelCard);

        // Lado Esquerdo
        JPanel panelEsquerda = new JPanel(new GridBagLayout());
        panelEsquerda.setOpaque(false);
        GridBagConstraints gbcEsq = new GridBagConstraints();
        gbcEsq.gridx = 0;

        JLabel lblTextoLogo = new JLabel("<html><span style='color: #FFFFFF;'>Key</span><span style='color: #0C264B;'><b>Passo</b></span><span style='color: #FFE600;'><b>*</b></span></html>");
        lblTextoLogo.setFont(new Font("Segoe UI", Font.PLAIN, 32));

        JLabel lblSub = new JLabel("<html><center style='color: #A0AFC8;'>Junte-se à nossa<br>plataforma de segurança.</center></html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbcEsq.gridy = 0; gbcEsq.insets = new Insets(0, 0, 15, 0);
        panelEsquerda.add(lblTextoLogo, gbcEsq);
        gbcEsq.gridy = 1;
        panelEsquerda.add(lblSub, gbcEsq);
        panelCard.add(panelEsquerda);

        // Lado Direito (Formulário)
        JPanel panelDireita = new JPanel(new GridBagLayout());
        panelDireita.setOpaque(false);
        GridBagConstraints gbcDir = new GridBagConstraints();
        gbcDir.fill = GridBagConstraints.HORIZONTAL;
        gbcDir.gridx = 0; gbcDir.insets = new Insets(4, 25, 4, 25);

        JLabel lblTitulo = new JLabel("<html><span style='letter-spacing: 1.5px; color: #FFFFFF;'>CRIAR CONTA</span><span style='color: #00D4FF;'>*</span></html>");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        gbcDir.gridy = 0; gbcDir.insets = new Insets(10, 25, 10, 25);
        panelDireita.add(lblTitulo, gbcDir);

        txtNome = criarCampoTexto("Nome Completo");
        gbcDir.gridy = 1; panelDireita.add(txtNome, gbcDir);

        txtEmail = criarCampoTexto("E-mail");
        gbcDir.gridy = 2; panelDireita.add(txtEmail, gbcDir);

        txtSenha = criarCampoSenha("Senha");
        gbcDir.gridy = 3; panelDireita.add(txtSenha, gbcDir);

        txtConfirmaSenha = criarCampoSenha("Confirmar Senha");
        gbcDir.gridy = 4; panelDireita.add(txtConfirmaSenha, gbcDir);

        btnCadastrar = criarBotaoEspelhado("Cadastrar");
        gbcDir.gridy = 5; gbcDir.insets = new Insets(15, 25, 5, 25);
        panelDireita.add(btnCadastrar, gbcDir);

        lblVoltarLogin = new JLabel("Já tem uma conta? Faça login", SwingConstants.CENTER);
        lblVoltarLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblVoltarLogin.setForeground(AZUL_NEON);
        lblVoltarLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbcDir.gridy = 6; gbcDir.insets = new Insets(5, 25, 10, 25);
        panelDireita.add(lblVoltarLogin, gbcDir);

        panelCard.add(panelDireita);

        // Eventos
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            String confirmaSenha = new String(txtConfirmaSenha.getPassword());

            String resultado = authController.cadastrarUsuario(nome, email, senha, confirmaSenha);

            if ("OK".equals(resultado)) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado! Verifique seu e-mail para o código.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, resultado, "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

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
        field.setPreferredSize(new Dimension(0, 36));

        Color bordaPadrao = new Color(35, 48, 70);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText(""); field.setForeground(BRANCO);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AZUL_NEON, 1, true),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder); field.setForeground(CINZA_TEXTO);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
        });
        return field;
    }

    private JPasswordField criarCampoSenha(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(BG_CAMPO);
        field.setForeground(CINZA_TEXTO);
        field.setCaretColor(AZUL_NEON);
        field.setEchoChar((char) 0);
        field.setPreferredSize(new Dimension(0, 36));

        Color bordaPadrao = new Color(35, 48, 70);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String senhaStr = new String(field.getPassword());
                if (senhaStr.equals(placeholder)) {
                    field.setText(""); field.setForeground(BRANCO); field.setEchoChar('•');
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
                    field.setText(placeholder); field.setForeground(CINZA_TEXTO); field.setEchoChar((char) 0);
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

                int w = getWidth(); int h = getHeight();
                boolean hover = getModel().isRollover();

                Color cor1 = hover ? new Color(0, 160, 255) : AZUL_BOTAO;
                Color cor2 = hover ? new Color(0, 100, 220) : new Color(10, 80, 180);

                GradientPaint gp = new GradientPaint(0, 0, cor1, 0, h, cor2);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 14, 14));

                g2.setColor(BRANCO); g2.setFont(getFont());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastro().setVisible(true));
    }
}