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

public class TelaLogin extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private AuthController controller;

    public TelaLogin() {
        controller = new AuthController();

        // Configurações da Janela
        setTitle("KeePasso - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a tela
        setLayout(null); // Layout livre por coordenadas

        // Título
        JLabel lblTitulo = new JLabel("KeePasso");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(140, 20, 150, 30);
        add(lblTitulo);

        // Rótulo e Campo E-mail
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(50, 70, 80, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 70, 200, 25);
        add(txtEmail);

        // Rótulo e Campo Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 110, 80, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(120, 110, 200, 25);
        add(txtSenha);

        // Botão Entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(120, 160, 90, 30);
        add(btnEntrar);

        // Botão Cadastrar
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(220, 160, 100, 30);
        add(btnCadastrar);

        // Ações dos Botões
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                açãoLogin();
            }
        });

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a Tela de Cadastro e fecha a de Login
                new TelaCadastro().setVisible(true);
                dispose();
            }
        });
    }

    private void açãoLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        String resultado = controller.autenticarUsuario(email, senha);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Validado no Controller! (Aguardando DAO do Integrante 2)");
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método main para testar a tela diretamente
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}