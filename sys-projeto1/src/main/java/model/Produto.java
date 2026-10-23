package model; // pacote das classes do modelo de negócio

import java.math.BigDecimal; // classe para valores em dinheiro
import java.time.LocalDate; // classe para datas

public class Produto { // classe que representa o produto

    private String codigo; // identificador único do produto
    private String nome; // nome do produto
    private String descricao; // descrição do produto
    private LocalDate dataFabricacao; // data de fabricação
    private LocalDate dataValidade; // data de validade
    private BigDecimal precoCompra; // preço de compra
    private BigDecimal precoVenda; // preço de venda
    private int quantidadeEstoque; // quantidade em estoque
    private Categoria categoria; // composição com a classe Categoria

    public Produto(String codigo, String nome, String descricao, LocalDate dataFabricacao, LocalDate dataValidade, BigDecimal precoCompra, BigDecimal precoVenda, int quantidadeEstoque, Categoria categoria) { // construtor com todos os dados
        if (codigo == null || !codigo.matches("[A-Za-z0-9]{8}")) { // código precisa ter 8 caracteres alfanuméricos
            throw new IllegalArgumentException("Código deve ter 8 caracteres alfanuméricos."); // lança erro de validação
        } // fim da validação do código
        if (nome == null || nome.trim().length() < 3) { // nome não pode ser vazio e precisa ter no mínimo 3 letras
            throw new IllegalArgumentException("Nome deve ter no mínimo 3 caracteres."); // lança erro de validação
        } // fim da validação do nome
        if (dataFabricacao == null || dataValidade == null) { // as duas datas são obrigatórias
            throw new IllegalArgumentException("Informe as datas de fabricação e validade."); // lança erro de validação
        } // fim da validação de datas nulas
        if (dataFabricacao.isAfter(LocalDate.now())) { // fabricação não pode ser depois de hoje
            throw new IllegalArgumentException("Data de fabricação não pode ser posterior à data atual."); // lança erro de validação
        } // fim da validação da fabricação
        if (dataValidade.isBefore(dataFabricacao)) { // validade não pode ser antes da fabricação
            throw new IllegalArgumentException("Data de validade não pode ser anterior à data de fabricação."); // lança erro de validação
        } // fim da validação da validade
        if (precoCompra == null || precoVenda == null || precoCompra.compareTo(BigDecimal.ZERO) <= 0 || precoVenda.compareTo(BigDecimal.ZERO) <= 0) { // preços precisam ser positivos
            throw new IllegalArgumentException("Os preços devem ser valores positivos."); // lança erro de validação
        } // fim da validação de preços positivos
        if (precoVenda.compareTo(precoCompra) <= 0) { // venda precisa ser maior que compra
            throw new IllegalArgumentException("Preço de venda deve ser maior que o preço de compra."); // lança erro de validação
        } // fim da validação venda > compra
        if (quantidadeEstoque < 0) { // estoque não pode ser negativo
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa."); // lança erro de validação
        } // fim da validação do estoque
        if (categoria == null) { // categoria é obrigatória
            throw new IllegalArgumentException("Categoria é obrigatória."); // lança erro de validação
        } // fim da validação da categoria
        this.codigo = codigo; // guarda o código
        this.nome = nome; // guarda o nome
        this.descricao = descricao; // guarda a descrição
        this.dataFabricacao = dataFabricacao; // guarda a data de fabricação
        this.dataValidade = dataValidade; // guarda a data de validade
        this.precoCompra = precoCompra; // guarda o preço de compra
        this.precoVenda = precoVenda; // guarda o preço de venda
        this.quantidadeEstoque = quantidadeEstoque; // guarda o estoque
        this.categoria = categoria; // guarda a categoria
    } // fim do construtor

    public String getCodigo() { // getter do código
        return codigo; // devolve o código
    } // fim do getCodigo

    public String getNome() { // getter do nome
        return nome; // devolve o nome
    } // fim do getNome

    public String getDescricao() { // getter da descrição
        return descricao; // devolve a descrição
    } // fim do getDescricao

    public LocalDate getDataFabricacao() { // getter da data de fabricação
        return dataFabricacao; // devolve a data de fabricação
    } // fim do getDataFabricacao

    public LocalDate getDataValidade() { // getter da data de validade
        return dataValidade; // devolve a data de validade
    } // fim do getDataValidade

    public BigDecimal getPrecoCompra() { // getter do preço de compra
        return precoCompra; // devolve o preço de compra
    } // fim do getPrecoCompra

    public BigDecimal getPrecoVenda() { // getter do preço de venda
        return precoVenda; // devolve o preço de venda
    } // fim do getPrecoVenda

    public int getQuantidadeEstoque() { // getter do estoque
        return quantidadeEstoque; // devolve o estoque
    } // fim do getQuantidadeEstoque

    public Categoria getCategoria() { // getter da categoria
        return categoria; // devolve a categoria
    } // fim do getCategoria

    public double getMargemLucro() { // calcula a margem de lucro em porcentagem
        return precoVenda.subtract(precoCompra).divide(precoCompra, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100; // (venda - compra) / compra * 100
    } // fim do getMargemLucro

    public String toCsv() { // converte o produto em uma linha de CSV
        return codigo + ";" + nome + ";" + descricao + ";" + dataFabricacao + ";" + dataValidade + ";" + precoCompra + ";" + precoVenda + ";" + quantidadeEstoque + ";" + categoria.getId() + ";" + categoria.getNome() + ";" + categoria.getDescricao() + ";" + categoria.getSetor(); // junta todos os campos com ponto-e-vírgula
    } // fim do toCsv
} // fim da classe Produto