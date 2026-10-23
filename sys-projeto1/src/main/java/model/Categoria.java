package model; // pacote das classes do modelo de negócio

public class Categoria { // classe que representa a categoria do produto

    private int id; // identificador da categoria
    private String nome; // nome da categoria
    private String descricao; // descrição da categoria
    private String setor; // setor da categoria

    public Categoria(int id, String nome, String descricao, String setor) { // construtor com todos os atributos
        if (nome == null || nome.trim().isEmpty()) { // verifica se o nome está vazio
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio."); // lança erro de validação
        } // fim do if do nome
        if (setor == null || setor.trim().isEmpty()) { // verifica se o setor está vazio
            throw new IllegalArgumentException("Setor da categoria não pode ser vazio."); // lança erro de validação
        } // fim do if do setor
        this.id = id; // guarda o id
        this.nome = nome; // guarda o nome
        this.descricao = descricao; // guarda a descrição
        this.setor = setor; // guarda o setor
    } // fim do construtor

    public int getId() { // getter do id
        return id; // devolve o id
    } // fim do getId

    public String getNome() { // getter do nome
        return nome; // devolve o nome
    } // fim do getNome

    public String getDescricao() { // getter da descrição
        return descricao; // devolve a descrição
    } // fim do getDescricao

    public String getSetor() { // getter do setor
        return setor; // devolve o setor
    } // fim do getSetor
} // fim da classe Categoria