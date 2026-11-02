package controller;

import java.util.Random;
import model.Usuario;
import util.SecurityUtils;

public class AuthController {

    public String cadastrarUsuario(String nome, String email, String senha, String confirmaSenha) {
        if (nome == null || nome.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            senha == null || senha.isEmpty()) {
            return "Preencha todos os campos obrigatórios!";
        }

        if (!email.contains("@") || !email.contains(".")) {
            return "Digite um e-mail válido!";
        }

        if (!senha.equals(confirmaSenha)) {
            return "As senhas não coincidem!";
        }

        if (senha.length() < 6) {
            return "A senha deve ter pelo menos 6 caracteres!";
        }

        // Criptografa a senha
        String senhaHash = SecurityUtils.hashSenha(senha);
        Usuario novoUsuario = new Usuario(nome, email, senhaHash);

        // Gera código de 6 dígitos para o e-mail
        String codigo = String.valueOf(100000 + new Random().nextInt(900000));
        novoUsuario.setCodigoVerificacao(codigo);

        // AQUI: O Integrante 2 (Banco) salva 'novoUsuario' no MySQL
        // AQUI: O método de envio de e-mail envia 'codigo' para o 'email'

        return "OK";
    }

    public String autenticarUsuario(String email, String senha) {
        if (email == null || email.trim().isEmpty() || senha == null || senha.isEmpty()) {
            return "Preencha e-mail e senha!";
        }

        String senhaHash = SecurityUtils.hashSenha(senha);
        // AQUI: O Integrante 2 busca no banco se existe email, senhaHash e se ativo = true
        return "OK";
    }
}