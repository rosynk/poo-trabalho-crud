package src.zoologico;

import java.util.List;

public interface AnimalDAO {

    void inserir(Animal a) throws AnimalException;
    void atualizar(Animal a) throws AnimalException;
    void remover(Animal a) throws AnimalException;
    List<Animal> pesquisarPorNome(String nome) throws AnimalException;
    List<Animal> pesquisarTodos() throws AnimalException;
}
