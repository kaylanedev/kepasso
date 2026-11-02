package view;

import controller.AuthController;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class TelaRedefinirSenha extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtCodigo;
    private JPasswordField txtNovaSenha;
    private JPasswordField txtConfirmaSenha;
    private JButton btnSalvar;
    
    private String emailUsuario;
    private String codigoCorreto;

    private AuthController authController = new AuthController();

    private final Color AZUL_NEON   = new Color(0, 136, 255);
    private final Color BRANCO      = new Color(255, 255, 255);
    private final Color AZUL_BOTAO  = new Color(14, 126, 238);
    private final Color BG_CARD     = new Color(15, 20, 28);
    private final Color BG_CAMPO    = new Color(10, 14, 22);
    private final Color CINZA_TEXTO = new Color(160, 175, 200);

    public TelaRedefinirSenha(String email, String codigoEnviado) {
        this.emailUsuario = email;
        this.codigoCorreto = codigoEnviado;

        setTitle("KeyPasso - Nova Senha");
        setSize(520, 560); // Aumentado para acomodar o quadro de regras
        setResizable(false);
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

                g2.setColor(new Color(255, 255, 255, 12));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridBagLayout());
        panelCard.setPreferredSize(new Dimension(440, 480));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(4, 25, 4, 25);

        JLabel lblTitulo = new JLabel("NOVA SENHA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(BRANCO);
        gbc.gridy = 0;
        panelCard.add(lblTitulo, gbc);

        // Bloco de texto informando as regras da senha
        JLabel lblRegras = new JLabel("<html><div style='background-color: #0A0E16; padding: 8px; border-radius: 6px; color: #A0AFC8; font-size: 10px;'>"
                + "<b>Requisitos da senha:</b><br>"
                + "• De 8 a 20 caracteres<br>"
                + "• Pelo menos 1 letra maiúscula<br>"
                + "• Pelo menos 1 número<br>"
                + "• Pelo menos 1 símbolo (!@#$%^&*...)"
                + "</div></html>");
        gbc.gridy = 1;
        gbc.insets = new Insets(6, 25, 8, 25);
        panelCard.add(lblRegras, gbc);

        gbc.insets = new Insets(4, 25, 4, 25);

        txtCodigo = criarCampoTexto("Código de Verificação");
        gbc.gridy = 2;
        panelCard.add(txtCodigo, gbc);

        txtNovaSenha = criarCampoSenha("Nova Senha");
        gbc.gridy = 3;
        panelCard.add(txtNovaSenha, gbc);

        txtConfirmaSenha = criarCampoSenha("Confirmar Nova Senha");
        gbc.gridy = 4;
        panelCard.add(txtConfirmaSenha, gbc);

        btnSalvar = criarBotaoEspelhado("Alterar Senha");
        gbc.gridy = 5;
        gbc.insets = new Insets(12, 25, 10, 25);
        panelCard.add(btnSalvar, gbc);

        panelBackground.add(panelCard);

        // Evento do Botão Salvar
        btnSalvar.addActionListener(e -> {
            String codigoDigitado = txtCodigo.getText().trim();
            String novaSenha = new String(txtNovaSenha.getPassword());
            String confirma = new String(txtConfirmaSenha.getPassword());

            // 1. Validação do Código de Verificação
            if (codigoCorreto != null && !codigoCorreto.isEmpty()) {
                if (!codigoDigitado.equals(codigoCorreto)) {
                    JOptionPane.showMessageDialog(this, "Código de verificação incorreto!", "Código Inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // 2. Validação das Regras da Senha
            String erroSenha = validarRegrasSenha(novaSenha);
            if (erroSenha != null) {
                JOptionPane.showMessageDialog(this, erroSenha, "Senha Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Validação de Confirmação
            if (!novaSenha.equals(confirma)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // AQUI: Atualiza no banco MySQL a senha do emailUsuario
            JOptionPane.showMessageDialog(this, "Senha redefinida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    public TelaRedefinirSenha() {
        this("", "");
    }

    /**
     * Valida se a senha cumpre todos os requisitos de segurança.
     * Retorna null se for válida, ou uma mensagem de erro caso contrário.
     */
    private String validarRegrasSenha(String senha) {
        if (senha == null || senha.isEmpty() || senha.equals("Nova Senha")) {
            return "Digite a nova senha!";
        }
        if (senha.length() < 8 || senha.length() > 20) {
            return "A senha deve ter entre 8 e 20 caracteres!";
        }
        if (!senha.matches(".*[A-Z].*")) {
            return "A senha deve conter no mínimo uma letra maiúscula!";
        }
        if (!senha.matches(".*[0-9].*")) {
            return "A senha deve conter no mínimo um número!";
        }
        if (!senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return "A senha deve conter no mínimo um símbolo (!@#$%...)!";
        }
        return null;
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
    
}