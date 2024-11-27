package src.zoologico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.util.Conexao;

public class AnimalDAOImp implements AnimalDAO {

    @Override
    public void inserir(Animal a) throws AnimalException {
        String SQL = """
            INSERT INTO animais (nome, especie, idade, habitat)
            VALUES (?, ?, ?, ?)
        """;
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEspecie());
            stmt.setInt(3, a.getIdade());
            stmt.setString(4, a.getHabitat());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AnimalException("Erro ao inserir animal.", e);
        }
    }

    @Override
    public void atualizar(Animal a) throws AnimalException {
        String SQL = """
            UPDATE animais SET nome=?, especie=?, idade=?, habitat=?
            WHERE id=?
        """;
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEspecie());
            stmt.setInt(3, a.getIdade());
            stmt.setString(4, a.getHabitat());
            stmt.setString(5, a.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AnimalException("Erro ao atualizar animal.", e);
        }
    }

    @Override
    public void remover(Animal a) throws AnimalException {
        String SQL = """
            DELETE FROM animais WHERE id = ?
        """;
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, a.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AnimalException("Erro ao remover animal.", e);
        }
    }

    @Override
    public List<Animal> pesquisarPorNome(String nome) throws AnimalException {
        List<Animal> lista = new ArrayList<>();
        String SQL = """
            SELECT * FROM animais WHERE nome LIKE ?
        """;
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getString("id"));
                a.setNome(rs.getString("nome"));
                a.setEspecie(rs.getString("especie"));
                a.setIdade(rs.getInt("idade"));
                a.setHabitat(rs.getString("habitat"));
                lista.add(a);
            }
        } catch (SQLException e) {
            throw new AnimalException("Erro ao pesquisar animal.", e);
        }
        return lista;
    }

    @Override
    public List<Animal> listarTodos() throws AnimalException {
        List<Animal> lista = new ArrayList<>();
        String SQL = "SELECT * FROM animais";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getString("id"));
                a.setNome(rs.getString("nome"));
                a.setEspecie(rs.getString("especie"));
                a.setIdade(rs.getInt("idade"));
                a.setHabitat(rs.getString("habitat"));
                lista.add(a);
            }
        } catch (SQLException e) {
            throw new AnimalException("Erro ao listar animais.", e);
        }
        return lista;
    }
}
