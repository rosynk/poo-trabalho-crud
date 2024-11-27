

    package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import src.livros.LivrosException;

public class Conexao {

    // URL do banco de dados PostgreSQL
    private static final String URL = "jdbc:mariadb://localhost:3306/trabalhobd";
    private static final String USER = "root"; // Usuário do banco
    private static final String PASSWORD = "scooby"; // Senha do banco

    // Carrega o driver (opcional, mas ajuda em algumas configurações)
    /*static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do PostgreSQL não encontrado!");
            e.printStackTrace();
        }
    }*/

    // Obtém uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados.");
            throw new SQLException(e);
        }
    }
}


    //do kazu
    /*private static final String URL = "jdbc:postgresql://localhost:5432/trabalhobd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "scooby";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados.", e);
        }
    }*/




    //oq eu tinha feito antigo
    /*private static final String DB_CLASS = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/trabalhobd?allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "scooby";

    private static Conexao instancia;
    private Connection connection;

    private Conexao() throws LivrosException {
        try {
            Class.forName(DB_CLASS);
        } catch (ClassNotFoundException e) { 
            throw new LivrosException( e );
        }
    }

    public static Conexao getInstance() throws LivrosException { 
        if (instancia == null) { 
            instancia = new Conexao();
        }
        return instancia;
    }

    public Connection getConnection() throws LivrosException {
        try { 
            if (this.connection == null || 
                this.connection.isClosed() || 
                !this.connection.isValid(5000)) { 
                    this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            }
        } catch (SQLException e) { 
            throw new LivrosException(e);
        }
        return this.connection;
    }
    
}*/
