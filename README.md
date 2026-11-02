# KeyPasso 🔐

O **KeyPasso** é uma aplicação desktop de gestão e autenticação de segurança desenvolvida em **Java Swing** com padrão arquitetural **MVC (Model-View-Controller)** e integração completa com banco de dados **MySQL**.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java (JDK 17+)
- **Interface Gráfica:** Java Swing (Design customizado Dark Mode / Glassmorphism com `Graphics2D`)
- **Banco de Dados:** MySQL 8.0+
- **Driver JDBC:** MySQL Connector/J
- **Serviço de E-mail:** Jakarta Mail / JavaMail API

---

## 📐 Arquitetura do Projeto (MVC)

O projeto é estruturado seguindo o padrão Model-View-Controller, promovendo a separação clara de responsabilidades entre a interface visual, a lógica de negócio e a persistência de dados:

```text
KeyPasso/
├── src/
│   ├── controller/
│   │   └── AuthController.java     # Regras de negócio, envio de e-mails e mediação UI/BD
│   ├── util/
│   │   └── Conexao.java            # Gerenciamento da conexão JDBC (MySQL Driver)
│   └── view/
│       ├── TelaLogin.java          # Interface gráfica de autenticação de usuários
│       ├── TelaCadastro.java       # Interface gráfica de criação de conta
│       ├── TelaEsqueceuSenha.java  # Interface para recuperação de acesso
│       └── TelaPrincipal.java      # Dashboard do sistema pós-autenticação
└── README.md
```
🗄️ Estrutura do Banco de Dados
Antes de iniciar a aplicação, certifique-se de que a base de dados e a tabela usuarios estejam criadas no seu servidor MySQL:
````
-- Criar e selecionar o banco de dados
CREATE DATABASE IF NOT EXISTS keypasso;
USE keypasso;

-- Criar a tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    codigo_recuperacao VARCHAR(10),
    criado TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Consultar registros gravados
SELECT * FROM keypasso.usuarios;
````
⚙️ Configuração e Instalação
1. Clonar o Repositório
````
git clone [https://github.com/seu-usuario/keypasso.git](https://github.com/seu-usuario/keypasso.git)
cd keypasso
````
2. Configurar a Conexão com o Banco de Dados
Abra o arquivo src/util/Conexao.java no seu ambiente (Eclipse) e ajuste as credenciais de acesso do seu MySQL:
````
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/keypasso?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       //  usuário do MySQL
    private static final String PASS = "keypasso123"; // senha do MySQL

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado no Build Path!", e);
        }
    }
}
````
3. Build Path e Dependências
Certifique-se de que os seguintes arquivos JAR estão adicionados às bibliotecas do seu projeto (Build Path):
````
mysql-connector-j-8.3.0.jar (Driver oficial do MySQL)

jakarta.mail-2.0.1.jar e jakarta.activation-2.0.1.jar (Suporte ao envio de e-mails)
````
🚀 Como Executar o Projeto
Inicie o serviço local do MySQL (ex: via services.msc no Windows).

Abra o projeto na sua IDE (Eclipse).

Execute a classe src/view/TelaLogin.java ou src/view/TelaCadastro.java.

📌 Funcionalidades Concluídas
[x] Conexão JDBC robusta com tratamento de exceções via Conexao.java.

[x] Persistência em tempo real dos dados de cadastro no banco de dados MySQL.

[x] Validação de e-mails duplicados via restrição UNIQUE na tabela usuarios.

[x] Autenticação dinâmica de login integrando UI Swing com consultas SQL via AuthController.

[x] Interface gráfica moderna estilo Dark Mode / Glassmorphism desenvolvida nativamente em Java Swing.

[x] Integração com disparo de e-mails de confirmação.

*Este projeto é desenvolvido para fins corporativos e de demonstração da plataforma KeyPasso.*
