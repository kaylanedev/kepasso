package util; // Confirma se o nome do pacote bate com o da tua pasta (ex: util, conexao, etc.)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Configurações da base de dados KeyPasso
    private static final String URL = "jdbc:mysql://localhost:3306/keypasso?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "keypasso123"; // A tua senha definida no instalador do MySQL

    public static Connection getConexao() throws SQLException {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado! Verifica se adicionaste o .jar ao Build Path.", e);
        }
    }
}