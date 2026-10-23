package app; // pacote da classe principal

import javafx.application.Application; // classe base de aplicações JavaFX
import javafx.fxml.FXMLLoader; // carregador de arquivos FXML
import javafx.scene.Parent; // elemento raiz da tela
import javafx.scene.Scene; // cena da janela
import javafx.stage.Stage; // janela principal

    public class Main extends Application { // classe que inicia o programa

        @Override // sobrescreve o método start
        public void start(Stage palco) throws Exception { // método chamado quando o JavaFX inicia
            Parent raiz = FXMLLoader.load(getClass().getResource("/view/Produto.fxml")); // carrega a tela feita no Scene Builder
            palco.setTitle("Sistema de Gerenciamento de Produtos"); // define o título da janela
        palco.setScene(new Scene(raiz)); // coloca a tela na janela
        palco.show(); // mostra a janela
    } // fim do start

    public static void main(String[] args) { // método main do Java
        launch(args); // inicia o JavaFX
    } // fim do main
} // fim da classe Main