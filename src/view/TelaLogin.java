package view;

import java.awt.*;
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

        // Aplicar o visual nativo do SO
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("KeePasso - Autenticação");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal
        JPanel panelMain = new JPanel();
        panelMain.setBackground(new Color(245, 247, 250));
        panelMain.setLayout(new GridBagLayout());
        panelMain.setBorder(new EmptyBorder(30, 40, 30, 40));
        add(panelMain);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Título Principal
        JLabel lblTitulo = new JLabel("KeePasso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 37, 41));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelMain.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Acesse sua conta para continuar", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        panelMain.add(lblSubtitulo, gbc);

        // Rótulo E-mail
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(new Color(73, 80, 87));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 2, 0);
        panelMain.add(lblEmail, gbc);

        // Campo E-mail
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 3;
        panelMain.add(txtEmail, gbc);

        // Rótulo Senha
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSenha.setForeground(new Color(73, 80, 87));
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 2, 0);
        panelMain.add(lblSenha, gbc);

        // Campo Senha
        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 5;
        panelMain.add(txtSenha, gbc);

        // Botão Entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setBackground(new Color(13, 110, 253));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setOpaque(true);
        btnEntrar.setContentAreaFilled(true);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 6;
        gbc.insets = new Insets(25, 0, 8, 0);
        panelMain.add(btnEntrar, gbc);

        // Botão Cadastrar
        btnCadastrar = new JButton("Criar nova conta");
        btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCadastrar.setBackground(new Color(230, 235, 245));
        btnCadastrar.setForeground(new Color(13, 110, 253));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setOpaque(true);
        btnCadastrar.setContentAreaFilled(true);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelMain.add(btnCadastrar, gbc);

        // Ações dos Botões
        btnEntrar.addActionListener(e -> acaoLogin());
        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });
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