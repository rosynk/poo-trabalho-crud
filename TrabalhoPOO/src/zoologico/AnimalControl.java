package src.zoologico;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import src.livros.LivrosException;

public class AnimalControl {

    private ObservableList<Animal> lista = FXCollections.observableArrayList();
    private IntegerProperty id = new SimpleIntegerProperty(0);
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
        a.setId(String.valueOf(this.id.get()));
        a.setNome(this.nome.get());
        a.setEspecie(this.especie.get());
        a.setIdade(this.idade.get());
        a.setHabitat(this.habitat.get());

        boolean exists = false;
        for (Animal animal : lista) {
            if (animal.getId() == a.getId()) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            animalDAO.inserir(a);
            lista.add(a);
            System.out.println("Animal gravado com sucesso!");
        } else {
            System.out.println("O animal já existe.\nClique em atualizar");
        }
    }

    public void entidadeParaTela(Animal a) {
        if (a!=null) {
            this.id.set( a.getId() );
            this.nome.set(a.getNome());
            this.especie.set(a.getEspecie());
            this.habitat.set(a.getHabitat());
            this.idade.set(a.getIdade());
        }
    }

    public void pesquisarAnimal () throws AnimalException {
        //fazer no DAO
        lista.clear();
        lista.addAll(animalDAO.pesquisarPorNome(nome.get()));
    }


    public void pesquisarPorNome()  throws AnimalException {
        try {
            lista.clear();
            lista.addAll(animalDAO.pesquisarPorNome(nome.get()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Erro ao pesquisar o livro.");
        }
    }

    public void atualizar() throws AnimalException {
        Animal a = new Animal();
        a.setId(String.valueOf(this.id.get()));
        a.setNome(this.nome.get());
        a.setEspecie(this.especie.get());
        a.setIdade(this.idade.get());
        a.setHabitat(this.habitat.get());

        animalDAO.atualizar(a);
        System.out.println("Animal atualizado com sucesso!");
    }

    public void excluir(Animal a) throws AnimalException {
        animalDAO.remover(a);
        lista.remove(a);
        System.out.println("Animal excluído com sucesso!");
    }

    public void alert(AlertType tipo, String msg) {
        Alert alertWindow = new Alert(tipo);
        alertWindow.setHeaderText("Alerta");
        alertWindow.setContentText(msg);
        alertWindow.showAndWait();
    }

    public ObservableList<Animal> getLista() {
        return this.lista;
    }

    public IntegerProperty idProperty() {
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

    public void limparTela() {
        this.id.set( 0 );
        this.nome.set("");
        this.especie.set("");
        this.habitat.set("");
        this.idade.set(0);
    }
}
