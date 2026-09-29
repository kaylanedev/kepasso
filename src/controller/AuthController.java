package controller;

import model.Usuario;
import util.SecurityUtils;

public class AuthController {

    // Valida os dados e prepara o usuário para o cadastro
    public String cadastrarUsuario(String nome, String email, String senha, String confirmaSenha) {
        if (nome.trim().isEmpty() || email.trim().isEmpty() || senha.isEmpty()) {
            return "Preencha todos os campos!";
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

        // Criptografa a senha antes de preparar o objeto
        String senhaHash = SecurityUtils.hashSenha(senha);
        Usuario novoUsuario = new Usuario(nome, email, senhaHash);

        // AQUI: O Integrante 2 vai disponibilizar o DAO para salvar 'novoUsuario' no banco
        // E o Integrante 5 vai acionar o envio do código por e-mail

        return "OK";
    }

    // Valida os dados do formulário de Login
    public String autenticarUsuario(String email, String senha) {
        if (email.trim().isEmpty() || senha.isEmpty()) {
            return "Preencha e-mail e senha!";
        }

        String senhaHash = SecurityUtils.hashSenha(senha);

        // AQUI: O Integrante 2 vai buscar no banco de dados se existe um usuário 
        // com este 'email' e 'senhaHash' e se a conta está com 'ativo = true'

        return "OK";
    }
}
