package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.AuthController;

public class TelaCadastro extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmaSenha;
    private JButton btnCadastrar;
    private JButton btnVoltar;
    private AuthController controller;

    public TelaCadastro() {
        controller = new AuthController();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("KeePasso - Novo Cadastro");
        setSize(440, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelMain = new JPanel();
        panelMain.setBackground(new Color(245, 247, 250));
        panelMain.setLayout(new GridBagLayout());
        panelMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        add(panelMain);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);

        JLabel lblTitulo = new JLabel("Criar Conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(33, 37, 41));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelMain.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Preencha seus dados abaixo", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(108, 117, 125));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 15, 0);
        panelMain.add(lblSub, gbc);

        // Campo Nome
        panelMain.add(criarLabel("Nome"), ajustarGbc(gbc, 2));
        txtNome = criarTextField();
        panelMain.add(txtNome, ajustarGbc(gbc, 3));

        // Campo E-mail
        panelMain.add(criarLabel("E-mail"), ajustarGbc(gbc, 4));
        txtEmail = criarTextField();
        panelMain.add(txtEmail, ajustarGbc(gbc, 5));

        // Campo Senha
        panelMain.add(criarLabel("Senha"), ajustarGbc(gbc, 6));
        txtSenha = criarPasswordField();
        panelMain.add(txtSenha, ajustarGbc(gbc, 7));

        // Campo Confirmar Senha
        panelMain.add(criarLabel("Confirmar Senha"), ajustarGbc(gbc, 8));
        txtConfirmaSenha = criarPasswordField();
        panelMain.add(txtConfirmaSenha, ajustarGbc(gbc, 9));

        // Botão Cadastrar (Verde com texto visível)
        btnCadastrar = new JButton("Finalizar Cadastro");
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(25, 135, 84));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setOpaque(true);
        btnCadastrar.setContentAreaFilled(true);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 10; gbc.insets = new Insets(20, 0, 8, 0);
        panelMain.add(btnCadastrar, gbc);

        // Botão Voltar (Cinza com texto visível)
        btnVoltar = new JButton("Voltar ao Login");
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnVoltar.setBackground(new Color(222, 226, 230));
        btnVoltar.setForeground(new Color(33, 37, 41));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setContentAreaFilled(true);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 11; gbc.insets = new Insets(0, 0, 0, 0);
        panelMain.add(btnVoltar, gbc);

        btnCadastrar.addActionListener(e -> açãoSalvar());
        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(73, 80, 87));
        return label;
    }

    private JTextField criarTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(0, 35));
        return field;
    }

    private JPasswordField criarPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(0, 35));
        return field;
    }

    private GridBagConstraints ajustarGbc(GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.insets = (y % 2 == 0) ? new Insets(8, 0, 2, 0) : new Insets(0, 0, 2, 0);
        return gbc;
    }

    private void açãoSalvar() {
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