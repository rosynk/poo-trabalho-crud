package src.livros;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import src.zoologico.Animal;

public class LivrosControl {

    private ObservableList<Livros> lista = FXCollections.observableArrayList();
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty titulo = new SimpleStringProperty("");
    private StringProperty autor = new SimpleStringProperty("");
    private StringProperty genero = new SimpleStringProperty("");
    private IntegerProperty anoLancamento = new SimpleIntegerProperty(0);
    private LivrosDAO livrosDAO;



    public void entidadeParaTela(Livros l) {
        if (l!=null) {
            this.id.set( l.getId() );
            this.autor.set(l.getAutor());
            this.genero.set(l.getGenero());
            this.anoLancamento.set(l.getAnoLancamento());
            this.titulo.set(l.getTitulo());
        }
    }

    public void limparTela() {

            this.id.set(0);
            this.autor.set("");
            this.genero.set("");
            this.anoLancamento.set(0);
            this.titulo.set("");

    }

    public LivrosControl() throws LivrosException{
        livrosDAO = new LivrosDAOImp();
        
    }

    public void gravar () throws LivrosException {
            //System.out.println("entrou no gravar");
            Livros l = new Livros();
            l.setId(this.id.get());
            l.setTitulo(this.titulo.get());
            l.setAutor(this.autor.get());
            l.setAnoLancamento(this.anoLancamento.get());
            l.setGenero(this.genero.get());

            boolean exists = false;
            for (Livros livros : lista) {
                if (livros.getId() == l.getId()) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                livrosDAO.inserir(l);
            } else {
                System.out.println("O já existente.\nClique em atualizar");
            }

        livrosDAO.inserir(l);
        System.out.println("Livro gravado com sucesso!");
        lista.add(l);


    }

    public void atualizar () throws LivrosException{
        Livros l = new Livros();

        l.setId(this.id.get());
        l.setTitulo(this.titulo.get());
        l.setAutor(this.autor.get());
        l.setAnoLancamento(this.anoLancamento.get());
        l.setGenero(this.genero.get());

        try {
            livrosDAO.atualizar(l);
        } catch(Exception e){
            System.out.println("Erro na atualização");
            }
    }

    public void alert(AlertType tipo, String msg) { 
        Alert alertWindow = new Alert(tipo);
        alertWindow.setHeaderText("Alerta");
        alertWindow.setContentText(msg);
        alertWindow.showAndWait();
    }

    public void pesquisarLivro () throws LivrosException {
        //fazer no DAO
        lista.clear();
        lista.addAll(livrosDAO.pesquisarPorNome(titulo.get()));
    }

    public void pesquisarPorNome()  throws LivrosException { 
        try {
            lista.clear();
            lista.addAll( livrosDAO.pesquisarPorNome( titulo.get() ) );
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Erro ao pesquisar o livro.");
        }
        
    }

    public void excluir (Livros l) throws LivrosException{
        try {
            System.out.println("Excluindo título com nome: " + l.getTitulo());
            livrosDAO.remover(l);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao excluir o livro.");
        }


        
    }

    public ObservableList<Livros> getLista() {
        return this.lista;
    }

    public int getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(Integer.parseInt(id));
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public String getAutor() {
        return autor.get();
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public int getAnoLancamento() {
        return anoLancamento.get();
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento.set(anoLancamento);
    }

    public IntegerProperty anoLancamentoProperty() {
        return anoLancamento;
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public ObservableList<Livros> pesquisarPorNome(String nome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pesquisarPorNome'");
    }
    
}
