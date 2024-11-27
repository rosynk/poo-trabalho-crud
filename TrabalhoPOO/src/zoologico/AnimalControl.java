package src.zoologico;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AnimalControl {

    private ObservableList<Animal> lista = FXCollections.observableArrayList();
    private StringProperty id = new SimpleStringProperty("");
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty especie = new SimpleStringProperty("");
    private IntegerProperty idade = new SimpleIntegerProperty(0);
    private StringProperty habitat = new SimpleStringProperty("");

    private AnimalDAO animalDAO;

    public AnimalControl() throws AnimalException {
        animalDAO = new AnimalDAOImp();
    }

    public void gravar() throws AnimalException {
        Animal a = new Animal();
        a.setId(this.id.get());
        a.setNome(this.nome.get());
        a.setEspecie(this.especie.get());
        a.setIdade(this.idade.get());
        a.setHabitat(this.habitat.get());

        if (a.getId() == null || a.getId().isEmpty()) {
            animalDAO.inserir(a);
        } else {
            animalDAO.atualizar(a);
        }

        lista.clear();
        lista.addAll(animalDAO.listarTodos());
    }

    public void pesquisarPorNome() throws AnimalException {
        lista.clear();
        lista.addAll(animalDAO.pesquisarPorNome(nome.get()));
    }

    public void excluir(Animal a) throws AnimalException {
        animalDAO.remover(a);
        lista.remove(a);
    }

    public ObservableList<Animal> getLista() {
        return lista;
    }

    // Getters e Setters das propriedades
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty especieProperty() {
        return especie;
    }

    public IntegerProperty idadeProperty() {
        return idade;
    }

    public StringProperty habitatProperty() {
        return habitat;
    }
}
