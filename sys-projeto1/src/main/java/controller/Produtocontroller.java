package controller; // pacote do controlador

import java.io.IOException; // erro de arquivo
import java.math.BigDecimal; // valores em dinheiro
import java.time.LocalDate; // datas
import java.time.format.DateTimeFormatter; // formatador de datas
import java.time.format.DateTimeParseException; // erro ao ler data digitada
import java.util.List; // lista
import javafx.beans.property.SimpleStringProperty; // texto observável para as colunas
import javafx.collections.FXCollections; // cria listas observáveis
import javafx.collections.ObservableList; // lista que atualiza a tabela
import javafx.fxml.FXML; // liga componentes do FXML ao código
import javafx.scene.control.Alert; // caixa de mensagem
import javafx.scene.control.DatePicker; // seletor de data
import javafx.scene.control.TableColumn; // coluna da tabela
import javafx.scene.control.TableView; // tabela
import javafx.scene.control.TextArea; // área de texto grande
import javafx.scene.control.TextField; // campo de texto
import javafx.util.StringConverter; // conversor de texto para data
import model.Categoria; // classe Categoria
import model.Produto; // classe Produto
import repository.Produtorepository; // classe do arquivo CSV
import repository.Produtorepository;
import service.Relatorio; // classe dos relatórios

public class Produtocontroller { // controlador da tela

    @FXML private TextField txtConsulta; // campo da consulta por código
    @FXML private TextField txtCodigo; // campo do código
    @FXML private TextField txtNome; // campo do nome
    @FXML private TextField txtDescricao; // campo da descrição
    @FXML private DatePicker dpFabricacao; // data de fabricação
    @FXML private DatePicker dpValidade; // data de validade
    @FXML private TextField txtPrecoCompra; // campo do preço de compra
    @FXML private TextField txtPrecoVenda; // campo do preço de venda
    @FXML private TextField txtQuantidade; // campo do estoque
    @FXML private TextField txtCatId; // campo do id da categoria
    @FXML private TextField txtCatNome; // campo do nome da categoria
    @FXML private TextField txtCatSetor; // campo do setor da categoria
    @FXML private TextField txtCatDescricao; // campo da descrição da categoria
    @FXML private TableView<Produto> tabela; // tabela de produtos
    @FXML private TableColumn<Produto, String> colCodigo; // coluna do código
    @FXML private TableColumn<Produto, String> colNome; // coluna do nome
    @FXML private TableColumn<Produto, String> colCategoria; // coluna da categoria
    @FXML private TableColumn<Produto, String> colSetor; // coluna do setor
    @FXML private TableColumn<Produto, String> colPrecoCompra; // coluna do preço de compra
    @FXML private TableColumn<Produto, String> colPrecoVenda; // coluna do preço de venda
    @FXML private TableColumn<Produto, String> colEstoque; // coluna do estoque
    @FXML private TableColumn<Produto, String> colValidade; // coluna da validade
    @FXML private TextArea txtRelatorio; // exibição do relatório

    private Produtorepository repositorio = new Produtorepository(); // objeto que mexe no arquivo
    private Relatorio relatorio = new Relatorio(); // objeto dos relatórios
    private List<Produto> produtos; // lista com todos os produtos
    private ObservableList<Produto> dadosTabela = FXCollections.observableArrayList(); // lista exibida na tabela
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formato de data brasileiro

    @FXML // método chamado automaticamente ao abrir a tela
    public void initialize() { // inicializa a tela
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo())); // coluna mostra o código
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome())); // coluna mostra o nome
        colCategoria.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategoria().getNome())); // coluna mostra a categoria
        colSetor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategoria().getSetor())); // coluna mostra o setor
        colPrecoCompra.setCellValueFactory(c -> new SimpleStringProperty("R$ " + c.getValue().getPrecoCompra())); // coluna mostra o preço de compra
        colPrecoVenda.setCellValueFactory(c -> new SimpleStringProperty("R$ " + c.getValue().getPrecoVenda())); // coluna mostra o preço de venda
        colEstoque.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getQuantidadeEstoque()))); // coluna mostra o estoque
        colValidade.setCellValueFactory(c -> new SimpleStringProperty(formato.format(c.getValue().getDataValidade()))); // coluna mostra a validade formatada
        tabela.setItems(dadosTabela); // liga a tabela à lista observável
        configurarData(dpFabricacao); // define o formato dd/MM/yyyy na fabricação
        configurarData(dpValidade); // define o formato dd/MM/yyyy na validade
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> { // evento ao clicar em uma linha
            if (novo != null) { // se alguma linha foi selecionada
                preencherFormulario(novo); // carrega os dados no formulário
            } // fim do if
        }); // fim do evento da tabela
        produtos = repositorio.carregar(); // carrega os dados do arquivo ao iniciar
        dadosTabela.setAll(produtos); // mostra os produtos na tabela
    } // fim do initialize

    private void configurarData(DatePicker seletor) { // configura o formato de data de um DatePicker
        seletor.setConverter(new StringConverter<LocalDate>() { // cria o conversor de data
            @Override // sobrescreve o método toString
            public String toString(LocalDate data) { // converte data em texto
                return data == null ? "" : formato.format(data); // devolve texto vazio se não houver data
            } // fim do toString

            @Override // sobrescreve o método fromString
            public LocalDate fromString(String texto) { // converte texto em data
                if (texto == null || texto.trim().isEmpty()) { // se o texto está vazio
                    return null; // não há data
                } // fim do if
                try { // tenta converter
                    return LocalDate.parse(texto.trim(), formato); // converte o texto em data
                } catch (DateTimeParseException e) { // se o texto for inválido
                    return null; // devolve sem data
                } // fim do try-catch
            } // fim do fromString
        }); // fim do conversor
    } // fim do configurarData

    private Produto criarProdutoDoFormulario() { // monta um produto com os dados da tela
        Categoria categoria = new Categoria(Integer.parseInt(txtCatId.getText().trim()), txtCatNome.getText().replace(";", ","), txtCatDescricao.getText().replace(";", ","), txtCatSetor.getText().replace(";", ",")); // cria a categoria com os dados da tela
        return new Produto(txtCodigo.getText().trim(), txtNome.getText().replace(";", ","), txtDescricao.getText().replace(";", ","), dpFabricacao.getValue(), dpValidade.getValue(), new BigDecimal(txtPrecoCompra.getText().trim().replace(",", ".")), new BigDecimal(txtPrecoVenda.getText().trim().replace(",", ".")), Integer.parseInt(txtQuantidade.getText().trim()), categoria); // cria o produto (validações no construtor)
    } // fim do criarProdutoDoFormulario

    private void preencherFormulario(Produto p) { // coloca os dados de um produto no formulário
        txtCodigo.setText(p.getCodigo()); // preenche o código
        txtNome.setText(p.getNome()); // preenche o nome
        txtDescricao.setText(p.getDescricao()); // preenche a descrição
        dpFabricacao.setValue(p.getDataFabricacao()); // preenche a fabricação
        dpValidade.setValue(p.getDataValidade()); // preenche a validade
        txtPrecoCompra.setText(p.getPrecoCompra().toString()); // preenche o preço de compra
        txtPrecoVenda.setText(p.getPrecoVenda().toString()); // preenche o preço de venda
        txtQuantidade.setText(String.valueOf(p.getQuantidadeEstoque())); // preenche o estoque
        txtCatId.setText(String.valueOf(p.getCategoria().getId())); // preenche o id da categoria
        txtCatNome.setText(p.getCategoria().getNome()); // preenche o nome da categoria
        txtCatSetor.setText(p.getCategoria().getSetor()); // preenche o setor da categoria
        txtCatDescricao.setText(p.getCategoria().getDescricao()); // preenche a descrição da categoria
    } // fim do preencherFormulario

    @FXML // ligado ao botão Cadastrar
    private void cadastrar() { // cadastra um novo produto
        try { // tenta cadastrar
            Produto produto = criarProdutoDoFormulario(); // cria o produto com os dados da tela
            for (Produto p : produtos) { // percorre os produtos existentes
                if (p.getCodigo().equalsIgnoreCase(produto.getCodigo())) { // verifica se o código já existe
                    throw new IllegalArgumentException("Já existe um produto com esse código."); // código precisa ser único
                } // fim do if
            } // fim do for
            produtos.add(produto); // adiciona o produto na lista
            repositorio.salvar(produtos); // salva no arquivo CSV
            dadosTabela.setAll(produtos); // atualiza a tabela
            limpar(); // limpa o formulário
            mostrarMensagem(Alert.AlertType.INFORMATION, "Produto cadastrado com sucesso!"); // avisa o usuário
        } catch (NumberFormatException e) { // erro ao converter número
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha corretamente os campos numéricos."); // avisa o erro
        } catch (IllegalArgumentException e) { // erro de validação
            mostrarMensagem(Alert.AlertType.ERROR, e.getMessage()); // mostra a mensagem da validação
        } catch (IOException e) { // erro ao gravar arquivo
            mostrarMensagem(Alert.AlertType.ERROR, "Erro ao salvar no arquivo: " + e.getMessage()); // avisa o erro
        } // fim do try-catch
    } // fim do cadastrar

    @FXML // ligado ao botão Atualizar
    private void atualizar() { // atualiza um produto já cadastrado
        try { // tenta atualizar
            Produto novo = criarProdutoDoFormulario(); // cria o produto com os dados da tela
            int posicao = -1; // posição do produto na lista (-1 = não achou)
            for (int i = 0; i < produtos.size(); i++) { // percorre a lista pelas posições
                if (produtos.get(i).getCodigo().equalsIgnoreCase(novo.getCodigo())) { // compara o código
                    posicao = i; // guarda a posição encontrada
                } // fim do if
            } // fim do for
            if (posicao == -1) { // se não achou o produto
                throw new IllegalArgumentException("Produto não encontrado. Selecione uma linha da tabela (o código não pode ser alterado)."); // avisa o erro
            } // fim do if
            produtos.set(posicao, novo); // troca o produto antigo pelo novo
            repositorio.salvar(produtos); // salva no arquivo CSV
            dadosTabela.setAll(produtos); // atualiza a tabela
            limpar(); // limpa o formulário
            mostrarMensagem(Alert.AlertType.INFORMATION, "Produto atualizado com sucesso!"); // avisa o usuário
        } catch (NumberFormatException e) { // erro ao converter número
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha corretamente os campos numéricos."); // avisa o erro
        } catch (IllegalArgumentException e) { // erro de validação
            mostrarMensagem(Alert.AlertType.ERROR, e.getMessage()); // mostra a mensagem
        } catch (IOException e) { // erro ao gravar arquivo
            mostrarMensagem(Alert.AlertType.ERROR, "Erro ao salvar no arquivo: " + e.getMessage()); // avisa o erro
        } // fim do try-catch
    } // fim do atualizar

    @FXML // ligado ao botão Excluir
    private void excluir() { // exclui o produto selecionado na tabela
        Produto selecionado = tabela.getSelectionModel().getSelectedItem(); // pega o produto selecionado
        if (selecionado == null) { // se nada foi selecionado
            mostrarMensagem(Alert.AlertType.WARNING, "Selecione um produto na tabela."); // avisa o usuário
            return; // sai do método
        } // fim do if
        try { // tenta excluir
            produtos.remove(selecionado); // remove da lista
            repositorio.salvar(produtos); // salva no arquivo CSV
            dadosTabela.setAll(produtos); // atualiza a tabela
            limpar(); // limpa o formulário
            mostrarMensagem(Alert.AlertType.INFORMATION, "Produto excluído com sucesso!"); // avisa o usuário
        } catch (IOException e) { // erro ao gravar arquivo
            mostrarMensagem(Alert.AlertType.ERROR, "Erro ao salvar no arquivo: " + e.getMessage()); // avisa o erro
        } // fim do try-catch
    } // fim do excluir

    @FXML // ligado ao botão Consultar
    private void consultar() { // consulta um produto pelo código
        String codigo = txtConsulta.getText().trim(); // pega o código digitado
        Produto encontrado = null; // começa sem produto encontrado
        for (Produto p : produtos) { // percorre os produtos
            if (p.getCodigo().equalsIgnoreCase(codigo)) { // compara o código
                encontrado = p; // guarda o produto encontrado
            } // fim do if
        } // fim do for
        if (encontrado == null) { // se não achou
            mostrarMensagem(Alert.AlertType.WARNING, "Produto não encontrado."); // avisa o usuário
            return; // sai do método
        } // fim do if
        dadosTabela.setAll(encontrado); // mostra só o produto encontrado na tabela
        preencherFormulario(encontrado); // preenche o formulário com o produto
    } // fim do consultar

    @FXML // ligado ao botão Listar Todos
    private void listarTodos() { // lista todos os produtos
        txtConsulta.clear(); // limpa o campo de consulta
        dadosTabela.setAll(produtos); // mostra todos na tabela
    } // fim do listarTodos

    @FXML // ligado ao botão Limpar
    private void limpar() { // limpa todos os campos do formulário
        tabela.getSelectionModel().clearSelection(); // desmarca a linha da tabela
        txtCodigo.clear(); // limpa o código
        txtNome.clear(); // limpa o nome
        txtDescricao.clear(); // limpa a descrição
        dpFabricacao.setValue(null); // limpa a fabricação
        dpValidade.setValue(null); // limpa a validade
        txtPrecoCompra.clear(); // limpa o preço de compra
        txtPrecoVenda.clear(); // limpa o preço de venda
        txtQuantidade.clear(); // limpa o estoque
        txtCatId.clear(); // limpa o id da categoria
        txtCatNome.clear(); // limpa o nome da categoria
        txtCatSetor.clear(); // limpa o setor da categoria
        txtCatDescricao.clear(); // limpa a descrição da categoria
    } // fim do limpar

    @FXML // ligado ao botão de vencimento
    private void relatorioVencimento() { // gera o relatório de vencimento
        txtRelatorio.setText(relatorio.proximosVencimento(produtos)); // exibe o relatório
    } // fim do relatorioVencimento

    @FXML // ligado ao botão de estoque baixo
    private void relatorioEstoque() { // gera o relatório de estoque baixo
        txtRelatorio.setText(relatorio.estoqueBaixo(produtos)); // exibe o relatório
    } // fim do relatorioEstoque

    @FXML // ligado ao botão de margem
    private void relatorioMargem() { // gera o relatório de margem média
        txtRelatorio.setText(relatorio.margemMediaPorCategoria(produtos)); // exibe o relatório
    } // fim do relatorioMargem

    @FXML // ligado ao botão de setor
    private void relatorioSetor() { // gera o relatório por setor
        txtRelatorio.setText(relatorio.agrupadosPorSetor(produtos)); // exibe o relatório
    } // fim do relatorioSetor

    private void mostrarMensagem(Alert.AlertType tipo, String texto) { // mostra uma caixa de mensagem
        Alert alerta = new Alert(tipo); // cria o alerta
        alerta.setHeaderText(null); // remove o cabeçalho
        alerta.setContentText(texto); // define o texto
        alerta.showAndWait(); // exibe e espera o usuário fechar
    } // fim do mostrarMensagem
} // fim da classe Produtocontroller