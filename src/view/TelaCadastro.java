package view;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import controller.AuthController;

public class TelaCadastro extends JFrame {

    private static final long serialVersionUID = 1L; // Evita o aviso do Eclipse[cite: 3]

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmaSenha;
    private JButton btnCadastrar;
    private JButton btnVoltar;
    private AuthController controller;

    public TelaCadastro() {
        controller = new AuthController();

        setTitle("KeePasso - Novo Cadastro");
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

        // Cartão Central Flutuante com Sombra e Efeito Vidro Translúcido (Glassmorphism)
        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 1. Sombra Suave de Fundo
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 24, 24);
                
                // 2. Fundo Translúcido
                g2.setColor(new Color(32, 35, 52, 220));
                g2.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 24, 24);
                
                // 3. Borda Superior de Destaque
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 24, 24);
                g2.dispose();
            }
        };
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridBagLayout());
        panelCard.setPreferredSize(new Dimension(420, 580));
        panelBackground.add(panelCard);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);

        // Título Estilizado
        JLabel lblTitulo = new JLabel("Criar Conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 0, 2, 0);
        panelCard.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSub = new JLabel("Preencha os dados para se cadastrar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 15, 0);
        panelCard.add(lblSub, gbc);

        // Campo Nome
        panelCard.add(criarLabel("NOME COMPLETO"), ajustarGbc(gbc, 2));
        txtNome = criarCampoTexto();
        panelCard.add(txtNome, ajustarGbc(gbc, 3));

        // Campo E-mail
        panelCard.add(criarLabel("E-MAIL"), ajustarGbc(gbc, 4));
        txtEmail = criarCampoTexto();
        panelCard.add(txtEmail, ajustarGbc(gbc, 5));

        // Campo Senha
        panelCard.add(criarLabel("SENHA"), ajustarGbc(gbc, 6));
        txtSenha = criarCampoSenha();
        panelCard.add(txtSenha, ajustarGbc(gbc, 7));

        // Campo Confirmar Senha
        panelCard.add(criarLabel("CONFIRMAR SENHA"), ajustarGbc(gbc, 8));
        txtConfirmaSenha = criarCampoSenha();
        panelCard.add(txtConfirmaSenha, ajustarGbc(gbc, 9));

        // Botão Finalizar Cadastro (Com a mesma cor do Botão de Entrar da TelaLogin)
        btnCadastrar = criarBotaoGradiente("FINALIZAR CADASTRO", new Color(99, 102, 241), new Color(139, 92, 246));
        gbc.gridy = 10; gbc.insets = new Insets(20, 0, 8, 0);
        panelCard.add(btnCadastrar, gbc);

        // Botão Voltar (Link Estilizado)
        btnVoltar = criarBotaoOutline("← Voltar ao Login");
        gbc.gridy = 11; gbc.insets = new Insets(0, 0, 10, 0);
        panelCard.add(btnVoltar, gbc);

        // Eventos
        btnCadastrar.addActionListener(e -> acaoSalvar());
        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(148, 163, 184));
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(21, 23, 35));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(139, 92, 246));
        field.setPreferredSize(new Dimension(0, 36));

        Color bordaPadrao = new Color(51, 65, 85);
        Color bordaFoco = new Color(139, 92, 246); // Borda roxa neon ao focar (harmoniza com o botão)

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaFoco, 2, true),
                    BorderFactory.createEmptyBorder(4, 9, 4, 9)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
        return field;
    }

    private JPasswordField criarCampoSenha() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(21, 23, 35));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(139, 92, 246));
        field.setPreferredSize(new Dimension(0, 36));

        Color bordaPadrao = new Color(51, 65, 85);
        Color bordaFoco = new Color(139, 92, 246);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordaPadrao, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaFoco, 2, true),
                    BorderFactory.createEmptyBorder(4, 9, 4, 9)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bordaPadrao, 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
        return field;
    }

    private GridBagConstraints ajustarGbc(GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.insets = (y % 2 == 0) ? new Insets(5, 0, 2, 0) : new Insets(0, 0, 2, 0);
        return gbc;
    }

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
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton criarBotaoOutline(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(new Color(148, 163, 184));
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
                btn.setForeground(new Color(148, 163, 184));
            }
        });
        return btn;
    }

    private void acaoSalvar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String confirmaSenha = new String(txtConfirmaSenha.getPassword());

        String resultado = controller.cadastrarUsuario(nome, email, senha, confirmaSenha);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Cadastro validado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new TelaLogin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Aviso de Validação", JOptionPane.WARNING_MESSAGE);
        }
    }
}