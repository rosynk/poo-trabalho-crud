package src.zoologico;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import src.Tela;



public class AnimalBoundary implements Tela {

    private TextField lblId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtEspecie = new TextField();
    private TextField txtIdade = new TextField();
    private TextField txtHabitat = new TextField();

    private TableView<Animal> tableView = new TableView<>();
    Button btnGravar = new Button("Gravar");
    Button btnPesquisar = new Button("Pesquisar");
    Button btnAtualizar = new Button("Atualizar");
    private AnimalControl control;

    @Override
    public Pane render() {
        try {
            control = new AnimalControl();
        } catch (Exception e) {
            control.alert(AlertType.ERROR, "Erro ao inicializar o sistema");
        }

        // Layout
        GridPane pane = new GridPane();
        pane.add(new Label("ID:"), 0, 0);
        pane.add(lblId, 1, 0);

        pane.add(new Label("Nome:"), 0, 1);
        pane.add(txtNome, 1, 1);

        pane.add(new Label("Espécie:"), 0, 2);
        pane.add(txtEspecie, 1, 2);

        pane.add(new Label("Idade:"), 0, 3);
        pane.add(txtIdade, 1, 3);

        pane.add(new Label("Habitat:"), 0, 4);
        pane.add(txtHabitat, 1, 4);

        // Botões
        pane.add(btnGravar, 0, 5);
        pane.add(btnPesquisar, 1, 5);
        pane.add(btnAtualizar, 2, 5);

        // Espaçamento entre os componentes
        pane.setHgap(5);
        pane.setVgap(5);

        // Tabela
        generateColumns();
        tableView.setItems(control.getLista());
        pane.add(tableView, 0, 6, 3, 1);

        // Eventos
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (AnimalException e1) {
                control.alert(AlertType.ERROR, "Erro ao gravar");
            }
            tableView.refresh();
            control.limparTela();
        });

        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisarPorNome();
            } catch (AnimalException err) {
                control.alert(AlertType.ERROR, "Erro ao pesquisar");
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                control.atualizar();
            } catch (AnimalException ex) {
                control.alert(AlertType.ERROR, "Erro ao atualizar");
            }
            control.limparTela();
        });

        vincularPropriedades();

        tableView.refresh();

        try {
            control.pesquisarAnimal ();
        } catch (AnimalException e) {
            throw new RuntimeException(e);
        }

        /*Scene scene = new Scene(pane, 600, 400);
        stage.setTitle("Cadastro de Animais");
        stage.setScene(scene);
        stage.show();*/
        return pane;
    }

    public void generateColumns() {
        // Coluna ID
        TableColumn<Animal, Integer> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Coluna Nome
        TableColumn<Animal, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Coluna Espécie
        TableColumn<Animal, String> col3 = new TableColumn<>("Espécie");
        col3.setCellValueFactory(new PropertyValueFactory<>("especie"));

        // Coluna Idade
        TableColumn<Animal, Integer> col4 = new TableColumn<>("Idade");
        col4.setCellValueFactory(new PropertyValueFactory<>("idade"));

        // Coluna Habitat
        TableColumn<Animal, String> col5 = new TableColumn<>("Habitat");
        col5.setCellValueFactory(new PropertyValueFactory<>("habitat"));

        //Coluna Excluir (Botão)
        TableColumn<Animal, Void> colExcluir = new TableColumn<>("Excluir");
        colExcluir.setCellFactory(new Callback<>() {
        @Override
        public TableCell<Animal, Void> call(TableColumn<Animal, Void> param) {
            TableCell<Animal, Void> cell = new TableCell<Animal, Void>() {
                final Button btnExcluir = new Button("Apagar");

                {
                    btnExcluir.setOnAction(e -> {
                        Animal animal = getTableView().getItems().get(getIndex());
                        try {
                            control.excluir(animal);
                        } catch (AnimalException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }  // Chama a exclusão do livro
                        tableView.getItems().remove(animal);  // Remove da tabela
                        System.out.println("Livro excluído: " + animal.getNome());
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnExcluir);
                    }
                }
            };
            return cell;
        }
    });

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, colExcluir);

        tableView.getSelectionModel().selectedItemProperty()
                .addListener( (obs, antigo, novo) -> {
                    System.out.println( "Selecionado o animal ==> " + novo);
                    control.entidadeParaTela( novo );
                });
    }

    public void vincularPropriedades() {

        Bindings.bindBidirectional(lblId.textProperty(), control.idProperty(), (StringConverter) new IntegerStringConverter() );
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtEspecie.textProperty(), control.especieProperty());
        Bindings.bindBidirectional(txtIdade.textProperty(), control.idadeProperty(), (StringConverter) new IntegerStringConverter() );
        Bindings.bindBidirectional(txtHabitat.textProperty(), control.habitatProperty());
    }


}
