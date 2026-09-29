package view;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import controller.AuthController;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L; // Corrige o aviso do Eclipse[cite: 3]

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private AuthController controller;

    public TelaLogin() {
        controller = new AuthController();

        setTitle("KeePasso - Autenticação");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fundo Gradiente Fluido (Roxo Profundo -> Azul Noite)
        JPanel panelBackground = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 21, 34), getWidth(), getHeight(), new Color(10, 11, 20));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelBackground.setLayout(new GridBagLayout());
        add(panelBackground);

        // Card Central Flutuante com Sombra e Efeito Vidro Translúcido
        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 1. Desenha a Sombra Suave
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 24, 24);
                
                // 2. Desenha o Fundo Translúcido (Glassmorphism)
                g2.setColor(new Color(32, 35, 52, 220));
                g2.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 24, 24);
                
                // 3. Borda sutil brilhante no topo do card
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 24, 24);
                g2.dispose();
            }
        };
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridBagLayout());
        panelCard.setPreferredSize(new Dimension(410, 520));
        panelBackground.add(panelCard);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        
     // Ícone Vetorial Moderno em Prata / Azul Neon
        JPanel lblLogo = new JPanel() {
            private static final long serialVersionUID = 1L; // Evita o aviso do Eclipse[cite: 3]

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = getWidth() / 2;
                int cy = getHeight() / 2 + 2;

                // Gradiente Prateado / Neon Moderno (Branco -> Azul Claro)
                GradientPaint prata = new GradientPaint(cx - 15, cy - 15, new Color(255, 255, 255), cx + 15, cy + 15, new Color(129, 140, 248));
                g2.setPaint(prata);

                // 1. Arco Superior do Cadeado
                g2.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawArc(cx - 10, cy - 18, 20, 20, 0, 180);

                // 2. Corpo do Cadeado (Outline Moderno e Limpo)
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawRoundRect(cx - 15, cy - 5, 30, 24, 10, 10);

                // 3. Ponto Central / Fechadura Neon
                g2.setColor(new Color(129, 140, 248));
                g2.fillOval(cx - 3, cy + 4, 6, 6);

                g2.dispose();
            }
        };
        lblLogo.setOpaque(false);
        lblLogo.setPreferredSize(new Dimension(80, 60));

        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 0, 5, 0); // Mantém o espaçamento para não cortar no topo
        panelCard.add(lblLogo, gbc);

        // Título Estilizado
        JLabel lblTitulo = new JLabel("KeePasso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panelCard.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Acesse seu cofre de senhas", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(148, 163, 184));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelCard.add(lblSubtitulo, gbc);

        // Rótulo E-mail
        JLabel lblEmail = new JLabel("E-MAIL");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEmail.setForeground(new Color(148, 163, 184));
        gbc.gridy = 3;
        gbc.insets = new Insets(4, 0, 2, 0);
        panelCard.add(lblEmail, gbc);

        // Campo E-mail com Borda Dinâmica (Glow)
        txtEmail = criarCampoTextoComIcone("✉");
        gbc.gridy = 4;
        panelCard.add(txtEmail, gbc);

        // Rótulo Senha
        JLabel lblSenha = new JLabel("SENHA");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSenha.setForeground(new Color(148, 163, 184));
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 2, 0);
        panelCard.add(lblSenha, gbc);

        // Campo Senha com Borda Dinâmica (Glow)
        txtSenha = criarCampoSenhaComIcone("🔒");
        gbc.gridy = 6;
        panelCard.add(txtSenha, gbc);

        // Botão Entrar com Gradiente Vibrante
        btnEntrar = criarBotaoGradiente("ENTRAR", new Color(99, 102, 241), new Color(139, 92, 246));
        gbc.gridy = 7;
        gbc.insets = new Insets(22, 0, 12, 0);
        panelCard.add(btnEntrar, gbc);

        // Divisor Visual com Texto
        JPanel panelDivisor = criarDivisor();
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 0, 10, 0);
        panelCard.add(panelDivisor, gbc);

        // Botão Criar Conta (Outline Moderno)
        btnCadastrar = criarBotaoOutline("Criar nova conta");
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelCard.add(btnCadastrar, gbc);

        // Eventos
        btnEntrar.addActionListener(e -> acaoLogin());
        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });
    }

    // Criador de Campo de Texto com Borda de Foco Ativo (Glow Effect)
    private JTextField criarCampoTextoComIcone(String icone) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(21, 23, 35));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(129, 140, 248));
        field.setPreferredSize(new Dimension(0, 40));
        
        Color bordaPadrao = new Color(51, 65, 85);
        Color bordaFoco = new Color(129, 140, 248);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        // Evento que muda a cor da borda ao focar no campo
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaFoco, 2, true),
                    BorderFactory.createEmptyBorder(5, 11, 5, 11)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
        });
        return field;
    }

    // Criador de Campo de Senha com Borda de Foco Ativo
    private JPasswordField criarCampoSenhaComIcone(String icone) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(21, 23, 35));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(129, 140, 248));
        field.setPreferredSize(new Dimension(0, 40));

        Color bordaPadrao = new Color(51, 65, 85);
        Color bordaFoco = new Color(129, 140, 248);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaFoco, 2, true),
                    BorderFactory.createEmptyBorder(5, 11, 5, 11)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
        });
        return field;
    }

    // Botão Principal com Gradiente Interno e Animação de Hover
    private JButton criarBotaoGradiente(String texto, Color corInicio, Color corFim) {
        JButton btn = new JButton(texto) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = getModel().isRollover() 
                    ? new GradientPaint(0, 0, corInicio.brighter(), getWidth(), 0, corFim.brighter())
                    : new GradientPaint(0, 0, corInicio, getWidth(), 0, corFim);
                
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(0, 42)); // Altura padrão de 42px
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Botão de Cadastrar estilo Texto Link
    private JButton criarBotaoOutline(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(new Color(129, 140, 248));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(129, 140, 248));
            }
        });
        return btn;
    }

    // Linha Divisória "OU"
    private JPanel criarDivisor() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(51, 65, 85));
        sep1.setBackground(new Color(51, 65, 85));

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(51, 65, 85));
        sep2.setBackground(new Color(51, 65, 85));

        JLabel lblOu = new JLabel("  OU  ");
        lblOu.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblOu.setForeground(new Color(100, 116, 139));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(sep1, gbc);
        gbc.weightx = 0.0;
        panel.add(lblOu, gbc);
        gbc.weightx = 1.0;
        panel.add(sep2, gbc);

        return panel;
    }

    private void acaoLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String resultado = controller.autenticarUsuario(email, senha);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Validado no Controller!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}