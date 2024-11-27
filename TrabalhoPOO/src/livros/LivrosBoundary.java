package src.livros;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableCell;
import src.Tela;

public class LivrosBoundary implements Tela {

    private TextField lblId = new TextField();
    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtAnoLancamento = new TextField();
    private TextField txtGenero = new TextField();

    private TableView<Livros> tableView = new TableView<>();
    Button btnGravar = new Button("Gravar");
    Button btnPesquisar = new Button("Pesquisar");
    Button btnAtualizar = new Button("Atualizar");
    private LivrosControl control;

 

    @Override
    public Pane render() {

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
        pane.add(btnAtualizar, 2, 5);

        // Espaçamento entre os componentes
        pane.setHgap(5);
        pane.setVgap(5);

        // Tabela
        generateColumns();
        tableView.setItems(control.getLista()); // Associa a lista de livros ao TableView
        pane.add(tableView, 0, 6, 3, 1);

        // Eventos
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
                //System.out.println("Entra em gravar");
            } catch (LivrosException e1) {

                control.alert(AlertType.ERROR, "Erro ao gravar");
            }  
            tableView.refresh(); 
            control.limparTela();
        });
        btnPesquisar.setOnAction(e -> {
            try { 
                control.pesquisarPorNome(); 
            } catch (LivrosException err) { 
                control.alert(AlertType.ERROR, "Erro ao pesquisar");
            } 
        });
        btnAtualizar.setOnAction(e -> {
            try{
                control.atualizar();
            } catch (LivrosException ex) {
                control.alert(AlertType.ERROR, "Erro ao atualizar");
            }
            control.limparTela();
        });

        vincularPropriedades();

        //Retorna as coisas no tableView
       try {
            control.pesquisarLivro();
        } catch (LivrosException e) {
            throw new RuntimeException(e);
        }

        // Scene e Stage
        /*Scene scene = new Scene(pane, 600, 400);
        stage.setTitle("Cadastro de Livros");
        stage.setScene(scene);
        stage.show();*/

        tableView.refresh();
        return pane;
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
        tableView.setItems(control.getLista());

        tableView.getSelectionModel().selectedItemProperty()
                .addListener( (obs, antigo, novo) -> {
                    System.out.println( "Selecionado o animal ==> " + novo);
                    control.entidadeParaTela( novo );
                });
    }

    public void vincularPropriedades() {
        Bindings.bindBidirectional(lblId.textProperty(), control.idProperty(), (StringConverter) new IntegerStringConverter() );
        Bindings.bindBidirectional(txtTitulo.textProperty(), control.tituloProperty());
        Bindings.bindBidirectional(txtAutor.textProperty(), control.autorProperty());
        Bindings.bindBidirectional(txtGenero.textProperty(), control.generoProperty());
        Bindings.bindBidirectional(txtAnoLancamento.textProperty(), control.anoLancamentoProperty(), (StringConverter) new IntegerStringConverter() );
        
    }
    


}
