package src;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import src.livros.LivrosBoundary;
import src.zoologico.AnimalBoundary;


public class PrincipalBoundary extends Application {

    private Map<String, Tela> telas =
            new HashMap<>();

    @Override
    public void start(Stage stage) {
        BorderPane panePrincipal = new BorderPane();
        telas.put("Livros", new LivrosBoundary());
        telas.put("Zoologico", new AnimalBoundary());

        MenuBar menuBar = new MenuBar();

        Menu mnuCadastro = new Menu("Cadastro");

        MenuItem mnuLivros = new MenuItem("Livros");
        MenuItem mnuZoo = new MenuItem("Zoologico");

        mnuCadastro.getItems().addAll(mnuLivros, mnuZoo);

        menuBar.getMenus().add( mnuCadastro );

        panePrincipal.setTop( menuBar );

        mnuLivros.setOnAction( e -> panePrincipal.setCenter( telas.get("Livros").render() ) );
        mnuZoo.setOnAction( e -> panePrincipal.setCenter( telas.get("Zoologico").render() ) );

        Scene scn = new Scene( panePrincipal, 600, 400);
        stage.setScene(scn);
        stage.setTitle("Cadastro de Livros e Animais do Zoologico");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }

}