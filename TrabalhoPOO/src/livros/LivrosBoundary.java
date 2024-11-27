package src.livros;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableCell;

public class LivrosBoundary extends Application {

    private Label lblId = new Label();
    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtAnoLancamento = new TextField();
    private TextField txtGenero = new TextField();

    private TableView<Livros> tableView = new TableView<>();
    Button btnGravar = new Button("Gravar");
    Button btnPesquisar = new Button("Pesquisar");
    private LivrosControl control;

 

    @Override
    public void start(Stage stage) {

        try {
            control = new LivrosControl();
        } catch (Exception e) {
            control.alert(AlertType.ERROR, "Ao ao inicializar o sistema");
        }

        // Layout
        GridPane pane = new GridPane();
        pane.add(new Label("ID:"), 0, 0);
        pane.add(lblId, 1, 0);

        pane.add(new Label("Título:"), 0, 1);
        pane.add(txtTitulo, 1, 1);

        pane.add(new Label("Autor:"), 0, 2);
        pane.add(txtAutor, 1, 2);

        pane.add(new Label("Ano Lançamento:"), 0, 3);
        pane.add(txtAnoLancamento, 1, 3);

        pane.add(new Label("Gênero:"), 0, 4);
        pane.add(txtGenero, 1, 4);

        // Botões
        pane.add(btnGravar, 0, 5);
        pane.add(btnPesquisar, 1, 5);

        // Tabela
        generateColumns(); // Gera as colunas
        tableView.setItems(control.getLista()); // Associa a lista de livros ao TableView
        pane.add(tableView, 0, 6, 2, 1); // Adiciona a TableView ao GridPane

        // Eventos
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
                System.out.println("Entra em gravar");
            } catch (LivrosException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                System.err.println("Não entrou em gravar");
            }  
            tableView.refresh(); 
            
        });
        btnPesquisar.setOnAction(e -> {
            try { 
                control.pesquisarPorNome(); 
            } catch (LivrosException err) { 
                control.alert(AlertType.ERROR, "Erro ao pesquisar");
            } 
        });

        // Scene e Stage
        Scene scene = new Scene(pane, 600, 400);
        stage.setTitle("Cadastro de Livros");
        stage.setScene(scene);
        stage.show();
    }

    // Função para gerar as colunas da tabela
    public void generateColumns() {
        // Coluna ID
        TableColumn<Livros, Integer> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Coluna Título
        TableColumn<Livros, String> col2 = new TableColumn<>("Título");
        col2.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        // Coluna Autor
        TableColumn<Livros, String> col3 = new TableColumn<>("Autor");
        col3.setCellValueFactory(new PropertyValueFactory<>("autor"));

        // Coluna Ano de Lançamento
        TableColumn<Livros, Integer> col4 = new TableColumn<>("Ano Lançamento");
        col4.setCellValueFactory(new PropertyValueFactory<>("anoLancamento"));

        // Coluna Gênero
        TableColumn<Livros, String> col5 = new TableColumn<>("Gênero");
        col5.setCellValueFactory(new PropertyValueFactory<>("genero"));

        // Coluna Excluir (Botão)
        TableColumn<Livros, Void> colExcluir = new TableColumn<>("Excluir");
        colExcluir.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Livros, Void> call(TableColumn<Livros, Void> param) {
                TableCell<Livros, Void> cell = new TableCell<Livros, Void>() {
                    final Button btnExcluir = new Button("Apagar");

                    {
                        btnExcluir.setOnAction(e -> {
                            Livros livro = getTableView().getItems().get(getIndex());
                            try {
                                control.excluir(livro);
                            } catch (LivrosException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }  // Chama a exclusão do livro
                            tableView.getItems().remove(livro);  // Remove da tabela
                            System.out.println("Livro excluído: " + livro.getTitulo());
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

        // Adiciona as colunas na tabela
        tableView.getColumns().addAll(col1, col2, col3, col4, col5, colExcluir);
    }

    public void vincularPropriedades() { 
        Bindings.bindBidirectional(lblId.textProperty(), control.idProperty());
        Bindings.bindBidirectional(txtTitulo.textProperty(), control.tituloProperty());
        Bindings.bindBidirectional(txtAutor.textProperty(), control.autorProperty());
        Bindings.bindBidirectional(txtGenero.textProperty(), control.generoProperty());
        txtAnoLancamento.textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                // Atualiza a propriedade de ano com a conversão para inteiro
                control.anoLancamentoProperty().set(Integer.parseInt(newValue));
            } catch (NumberFormatException e) {
                // Caso o valor não seja um número válido, restaura o valor antigo
                txtAnoLancamento.setText(oldValue);
            }
        });
        
        control.anoLancamentoProperty().addListener((obs, oldValue, newValue) -> {
            // Atualiza o campo de texto com a conversão para String
            txtAnoLancamento.setText(newValue.toString());
        });
        
    }
    

    public static void main(String[] args) {
        Application.launch(LivrosBoundary.class, args);
    }
}
