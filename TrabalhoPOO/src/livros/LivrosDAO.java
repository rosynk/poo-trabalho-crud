package src.livros;

import java.util.List;

public interface LivrosDAO {

    void inserir(Livros c) throws LivrosException;
    void atualizar(Livros c) throws LivrosException;
    void remover(Livros c) throws LivrosException;
    List<Livros> pesquisarPorNome(String nome) throws LivrosException;
    List<Livros> pesquisarTodos() throws LivrosException;

    
}


