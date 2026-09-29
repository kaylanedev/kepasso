package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.AuthController;

public class TelaLogin extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private AuthController controller;

    public TelaLogin() {
        controller = new AuthController();

        setTitle("KeePasso - Autenticação");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Personalizado com Gradiente de Fundo (Efeito CSS linear-gradient)
        JPanel panelMain = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradiente suave de roxo escuro para azul noite
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 31, 48), 0, getHeight(), new Color(15, 16, 25));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panelMain.setLayout(new GridBagLayout());
        panelMain.setBorder(new EmptyBorder(30, 40, 30, 40));
        add(panelMain);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Ícone/Logo em Texto Embutido
        JLabel lblLogo = new JLabel("🔒", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelMain.add(lblLogo, gbc);

        // Título Principal com cor Neon/Branca
        JLabel lblTitulo = new JLabel("KeePasso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panelMain.add(lblTitulo, gbc);

        // Subtítulo em tom pastel
        JLabel lblSubtitulo = new JLabel("Gerenciador de Senhas Seguro", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(160, 174, 192));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelMain.add(lblSubtitulo, gbc);

        // Rótulo E-mail
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(new Color(226, 232, 240));
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 2, 0);
        panelMain.add(lblEmail, gbc);

        // Campo E-mail Estilizado
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setBackground(new Color(45, 55, 72));
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setCaretColor(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(74, 85, 104), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 4;
        panelMain.add(txtEmail, gbc);

        // Rótulo Senha
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSenha.setForeground(new Color(226, 232, 240));
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 2, 0);
        panelMain.add(lblSenha, gbc);

        // Campo Senha Estilizado
        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setBackground(new Color(45, 55, 72));
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(74, 85, 104), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 6;
        panelMain.add(txtSenha, gbc);

        // Botão Entrar Customizado (Estilo Arredondado + Efeito Hover CSS)
        btnEntrar = criarBotaoEstilizado("Entrar", new Color(99, 102, 241), new Color(129, 140, 248));
        gbc.gridy = 7;
        gbc.insets = new Insets(25, 0, 10, 0);
        panelMain.add(btnEntrar, gbc);

        // Botão Cadastrar (Transparente/Outline)
        btnCadastrar = criarBotaoOutline("Criar nova conta");
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelMain.add(btnCadastrar, gbc);

        // Ações dos Botões
        btnEntrar.addActionListener(e -> acaoLogin());
        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });
    }

    // Método Auxiliar para Botão com Gradiente e Arredondado
    private JButton criarBotaoEstilizado(String texto, Color corPadrao, Color corHover) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? corHover : corPadrao);
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Método Auxiliar para Botão estilo "Outline"
    private JButton criarBotaoOutline(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(160, 174, 192));
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
                btn.setForeground(new Color(160, 174, 192));
            }
        });
        return btn;
    }

    private void acaoLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String resultado = controller.autenticarUsuario(email, senha);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Validado no Controller! (Aguardando DAO do Integrante 2)", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}