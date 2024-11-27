package src.zoologico;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnimalBoundary extends Application {

    private TextField txtNome = new TextField();
    private TextField txtEspecie = new TextField();
    private TextField txtIdade = new TextField();
    private TextField txtHabitat = new TextField();
    private TableView<Animal> table = new TableView<>();
    private AnimalControl control;

    @Override
    public void start(Stage stage) {
        try {
            control = new AnimalControl();
        } catch (AnimalException e) {
            alert(Alert.AlertType.ERROR, "Erro ao inicializar o sistema: " + e.getMessage());
        }

        // Layout
        GridPane pane = new GridPane();
        pane.add(new Label("Nome:"), 0, 0);
        pane.add(txtNome, 1, 0);

        pane.add(new Label("Espécie:"), 0, 1);
        pane.add(txtEspecie, 1, 1);

        pane.add(new Label("Idade:"), 0, 2);
        pane.add(txtIdade, 1, 2);

        pane.add(new Label("Habitat:"), 0, 3);
        pane.add(txtHabitat, 1, 3);

        Button btnGravar = new Button("Gravar");
        Button btnPesquisar = new Button("Pesquisar");

        pane.add(btnGravar, 0, 4);
        pane.add(btnPesquisar, 1, 4);

        // Configuração da tabela
        TableColumn<Animal, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Animal, String> colEspecie = new TableColumn<>("Espécie");
        colEspecie.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEspecie()));

        TableColumn<Animal, Integer> colIdade = new TableColumn<>("Idade");
        colIdade.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdade()).asObject());

        TableColumn<Animal, String> colHabitat = new TableColumn<>("Habitat");
        colHabitat.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHabitat()));

        TableColumn<Animal, Void> colExcluir = new TableColumn<>("Excluir");
        colExcluir.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Remover");

            {
                btn.setOnAction(e -> {
                    Animal a = getTableView().getItems().get(getIndex());
                    try {
                        control.excluir(a);
                    } catch (AnimalException ex) {
                        alert(Alert.AlertType.ERROR, "Erro ao excluir: " + ex.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        table.getColumns().addAll(colNome, colEspecie, colIdade, colHabitat, colExcluir);
        table.setItems(control.getLista());

        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
                table.refresh();
            } catch (AnimalException ex) {
                alert(Alert.AlertType.ERROR, "Erro ao gravar: " + ex.getMessage());
            }
        });

        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisarPorNome();
            } catch (AnimalException ex) {
                alert(Alert.AlertType.ERROR, "Erro ao pesquisar: " + ex.getMessage());
            }
        });

        VBox root = new VBox(pane, table);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Gerenciamento de Animais");
        stage.show();
    }

    private void alert(Alert.AlertType tipo, String msg) {
        Alert alerta = new Alert(tipo);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch(AnimalBoundary.class,args);
    }
}

