package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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

        // Configurações da Janela
        setTitle("KeePasso - Cadastro de Usuário");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a tela
        setLayout(null); // Layout por coordenadas

        // Título
        JLabel lblTitulo = new JLabel("Criar Conta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(150, 15, 150, 30);
        add(lblTitulo);

        // Campo Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(40, 60, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 60, 200, 25);
        add(txtNome);

        // Campo E-mail
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(40, 100, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 100, 200, 25);
        add(txtEmail);

        // Campo Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(40, 140, 100, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(150, 140, 200, 25);
        add(txtSenha);

        // Campo Confirmar Senha
        JLabel lblConfirma = new JLabel("Confirmar Senha:");
        lblConfirma.setBounds(40, 180, 110, 25);
        add(lblConfirma);

        txtConfirmaSenha = new JPasswordField();
        txtConfirmaSenha.setBounds(150, 180, 200, 25);
        add(txtConfirmaSenha);

        // Botão Cadastrar
        btnCadastrar = new JButton("Salvar");
        btnCadastrar.setBounds(150, 230, 95, 30);
        add(btnCadastrar);

        // Botão Voltar para Login
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(255, 230, 95, 30);
        add(btnVoltar);

        // Ação do Botão Cadastrar
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                açãoSalvar();
            }
        });

        // Ação do Botão Voltar
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
    }

    private void açãoSalvar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String confirmaSenha = new String(txtConfirmaSenha.getPassword());

        String resultado = controller.cadastrarUsuario(nome, email, senha, confirmaSenha);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Cadastro validado com sucesso! (O Integrante 5 vai enviar o código por e-mail)");
            new TelaLogin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Aviso de Validação", JOptionPane.WARNING_MESSAGE);
        }
    }
}
