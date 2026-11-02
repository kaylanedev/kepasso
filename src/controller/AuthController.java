package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Conexao; // Confirme se o pacote da Conexao está correto

public class AuthController {

    // 1. MÉTODO DE CADASTRO (Usado na TelaCadastro)
    public String cadastrarUsuario(String nome, String email, String senha, String confirmaSenha) {
        // Validação de preenchimento básico e placeholders
        if (nome == null || nome.trim().isEmpty() || nome.equals("Nome Completo")) {
            return "Por favor, informe seu nome.";
        }
        if (email == null || email.trim().isEmpty() || email.equals("E-mail")) {
            return "Por favor, informe seu e-mail.";
        }
        if (senha == null || senha.isEmpty() || senha.equals("Senha")) {
            return "Por favor, informe uma senha.";
        }
        if (!senha.equals(confirmaSenha)) {
            return "As senhas não coincidem!";
        }

        // Gravação no Banco MySQL
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);

            stmt.executeUpdate();
            return "OK"; // Retorna OK para exibir mensagem de sucesso na TelaCadastro

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                return "Este e-mail já está cadastrado!";
            }
            return "Erro no banco de dados: " + e.getMessage();
        }
    }

    // 2. MÉTODO DE AUTENTICAÇÃO (Usado na TelaLogin)
    public boolean autenticarUsuario(String emailOuUsuario, String senha) {
        String sql = "SELECT * FROM usuarios WHERE (email = ? OR nome = ?) AND senha = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailOuUsuario);
            stmt.setString(2, emailOuUsuario);
            stmt.setString(3, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se encontrar usuário, false se não
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. MÉTODO DE VERIFICAÇÃO DE E-MAIL (Usado na TelaEsqueceuSenha)
    public boolean verificarEmailExiste(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}