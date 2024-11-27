package src.livros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.util.Conexao;



public class LivrosDAOImp implements LivrosDAO {
    /* */ // Constantes para conexão com o banco de dados
    //private static final String DB_CLASS = "org.postgresql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/trabalhobd";
    private static final String DB_USER = "root"; // Usuário do banco
    private static final String DB_PASS = "scooby";

    private Connection con = null;

    // Construtor que inicializa a conexão com o banco de dados
    /*public LivrosDAOImp() throws LivrosException {
        try {
            Class.forName(DB_CLASS); // Carrega o driver
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Conexão bem-sucedida!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public void inserir(Livros l) throws LivrosException {
        System.out.println("=======================");
        String SQL = """
                INSERT INTO livros (id, titulo, autor, anoLancamento, genero)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL)) {

            stmt.setInt(1, l.getId());
            stmt.setString(2, l.getTitulo());
            stmt.setString(3, l.getAutor());
            stmt.setInt(4, l.getAnoLancamento());
            stmt.setString(5, l.getGenero());

            stmt.executeUpdate();

            /*PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, 0); // O id será gerado automaticamente pelo banco, então passamos 0.
            stm.setString(2, l.getTitulo());
            stm.setString(3, l.getAutor());
            stm.setInt(4, l.getAnoLancamento());
            stm.setString(5, l.getGenero());
            int i = stm.executeUpdate();*/
        } catch (SQLException e) {
            throw new LivrosException("Erro ao salvar livro.", e);
            //e.printStackTrace();
            // throw new LivrosException(e);
        }
    }

    @Override
    public void atualizar(Livros l) throws LivrosException {

        String SQL = """
                UPDATE livros 
                SET id=?, titulo=?, autor=?, anoLancamento=?, genero=?
                WHERE id=?
                """;

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL)) {

            stmt.setInt(1, l.getId());
            stmt.setString(2, l.getTitulo());
            stmt.setString(3, l.getAutor());
            stmt.setInt(4, l.getAnoLancamento());
            stmt.setString(5, l.getGenero());
            stmt.setInt(6, l.getId());

            int i = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deu erro em atualizar no imp");
            e.printStackTrace();
            throw new LivrosException(e);

        }
    }

    @Override
    public void remover(Livros l) throws LivrosException {

        String SQL = """
                DELETE FROM livros WHERE id = ?
                """;


        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, l.getId());
            int i = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LivrosException(e);
        }
    }

    @Override
    public List<Livros> pesquisarPorNome(String nome) throws LivrosException {
        List<Livros> lista = new ArrayList<>();

        String SQL = """
                SELECT * FROM livros WHERE titulo LIKE ?
                """;

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livros livro = new Livros();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAnoLancamento(rs.getInt("anoLancamento")); //"ano_lancamento"
                livro.setGenero(rs.getString("genero"));
                lista.add(livro);
            
           /* PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");  // Procura por título que contenha o nome fornecido
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Livros l = new Livros();
                l.setId(rs.getString("id"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setAnoLancamento(rs.getInt("anoLancamento"));
                l.setGenero(rs.getString("genero"));
                lista.add(l);
            }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LivrosException("Erro ao pesquisar livros.", e);
        }
        return lista;
    }

    @Override
    public List<Livros> pesquisarTodos() throws LivrosException {

        List<Livros> livros = new ArrayList<>();
        String query = "SELECT * FROM livros";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livros livro = new Livros();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAnoLancamento(rs.getInt("ano_lancamento"));
                livro.setGenero(rs.getString("genero"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            throw new LivrosException("Erro ao pesquisar os livros", e);
        }
        return livros;
    }
}        /*List<Livros> lista = new ArrayList<>();
        try {
            String SQL = """
                    SELECT * FROM livros
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Livros l = new Livros();
                l.setId(rs.getString("id"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setAnoLancamento(rs.getInt("anoLancamento"));
                l.setGenero(rs.getString("genero"));
                lista.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LivrosException(e);
        }
        return lista;
    }*/
    


