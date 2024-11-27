package src.zoologico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import src.util.Conexao;

public class AnimalDAOImp implements AnimalDAO {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/trabalhobd";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "scooby";

    private Connection con = null;

    @Override
    public void inserir(Animal a) throws AnimalException {
        String SQL = """
                INSERT INTO zoologico (id, nome, especie, idade, habitat)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setInt(1, a.getId());
            stmt.setString(2, a.getNome());
            stmt.setString(3, a.getEspecie());
            stmt.setInt(4, a.getIdade());
            stmt.setString(5, a.getHabitat());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AnimalException("Erro ao salvar dados do animal.", e);
        }
    }

    @Override
    public void atualizar(Animal a) throws AnimalException {
        String SQL = """
                UPDATE zoologico 
                SET nome=?, especie=?, idade=?, habitat=?
                WHERE id=?
                """;

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEspecie());
            stmt.setInt(3, a.getIdade());
            stmt.setString(4, a.getHabitat());
            stmt.setInt(5, a.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AnimalException("Erro ao atualizar dados do animal.", e);
        }
    }

    @Override
    public void remover(Animal a) throws AnimalException {
        String SQL = """
                DELETE FROM zoologico WHERE id = ?
                """;

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AnimalException("Erro ao excluir animal.", e);
        }
    }

    @Override
    public List<Animal> pesquisarPorNome(String nome) throws AnimalException {
        List<Animal> lista = new ArrayList<>();
        String SQL = """
                SELECT * FROM zoologico WHERE nome LIKE ?
                """;

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId(String.valueOf(rs.getInt("id")));
                animal.setNome(rs.getString("nome"));
                animal.setEspecie(rs.getString("especie"));
                animal.setIdade(rs.getInt("idade"));
                animal.setHabitat(rs.getString("habitat"));
                lista.add(animal);
            }
        } catch (SQLException e) {
            throw new AnimalException("Erro ao pesquisar animal por nome.", e);
        }
        return lista;
    }

    @Override
    public List<Animal> pesquisarTodos() throws AnimalException {
        List<Animal> animais = new ArrayList<>();
        String SQL = """
                SELECT * FROM zoologico
                """;

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(SQL); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId(String.valueOf(rs.getInt("id")));
                animal.setNome(rs.getString("nome"));
                animal.setEspecie(rs.getString("especie"));
                animal.setIdade(rs.getInt("idade"));
                animal.setHabitat(rs.getString("habitat"));
                animais.add(animal);
            }
        } catch (SQLException e) {
            throw new AnimalException("Erro ao pesquisar todos os animais.", e);
        }
        return animais;
    }
}
