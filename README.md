# Sistema de Gerenciamento de Produtos (JavaFX)

> Projeto da avaliação **P1** da disciplina **Programação Orientada a Objetos III**
> **Centro Universitário Alfredo Nasser (UNIFAN)** – Engenharia de Software e Segurança da Informação
> **Professor:** Daniel Corrêa da Silva

| Campo | Informação |
|---|---|
| **Aluno(a)** | Luigi Prudente Giani |
| **Curso** | Engenharia de Software – 4º Período |
| **Data de entrega** | 23/10/2026 |

---

## 1. Descrição do projeto

Sistema desktop em **Java com interface gráfica JavaFX** (tela criada em FXML, editável no Scene Builder) para gerenciar o cadastro de produtos de uma loja ou supermercado.

O sistema aplica conceitos de programação orientada a objetos, manipulação de arquivos (CSV) e processamento de dados com a **Stream API** do Java.

## 2. Funcionalidades

### Operações básicas
- **Cadastrar** um novo produto com todos os seus dados (incluindo a categoria).
- **Atualizar** um produto já cadastrado (o código não pode ser alterado).
- **Excluir** o produto selecionado na tabela.
- **Consultar** um produto específico por código.
- **Listar todos** os produtos cadastrados.
- **Limpar** o formulário.
- Ao clicar em uma linha da tabela, os dados são carregados no formulário.

### Persistência
- Os dados são salvos no arquivo **`produtos.csv`**, com campos separados por **ponto-e-vírgula (`;`)**.
- O arquivo é **carregado automaticamente ao iniciar** o sistema.
- As alterações são **salvas após cada operação** (cadastrar, atualizar e excluir).

### Relatórios (Stream API – `filter`, `map`, `groupingBy`, `averagingDouble`, `joining`)
1. Produtos próximos ao vencimento (próximos 60 dias).
2. Produtos com estoque baixo (menos de 10 unidades).
3. Margem de lucro média por categoria.
4. Produtos agrupados por setor da categoria.

### Validações de dados
| Campo | Regra |
|---|---|
| Código | 8 caracteres alfanuméricos e único |
| Nome | Não vazio, mínimo de 3 caracteres |
| Data de fabricação | Não pode ser posterior à data atual |
| Data de validade | Não pode ser anterior à data de fabricação |
| Preços | Valores positivos; preço de venda maior que o de compra |
| Quantidade em estoque | Não negativa |
| Categoria | Nome e setor obrigatórios |

Os erros de validação e de arquivo são tratados com exceções e exibidos ao usuário em caixas de mensagem (`Alert`).

## 3. Tecnologias utilizadas
- Java **17 ou superior** (testado com JDK 25)
- JavaFX **21** (Controls e FXML)
- Maven
- Scene Builder (edição do arquivo FXML)
- IntelliJ IDEA

## 4. Estrutura do projeto

```
sys-projeto1
├── .mvn
│   └── wrapper                         (Maven Wrapper – permite rodar sem instalar o Maven)
├── src
│   └── main
│       ├── java
│       │   ├── module-info.java        (declaração do módulo)
│       │   ├── app
│       │   │   └── Main.java           (inicia o JavaFX e carrega a tela)
│       │   ├── controller
│       │   │   └── Produtocontroller.java   (eventos da tela)
│       │   ├── model
│       │   │   ├── Categoria.java      (modelo de negócio)
│       │   │   └── Produto.java        (modelo de negócio + validações)
│       │   ├── repository
│       │   │   └── Produtorepository.java   (leitura e gravação do CSV)
│       │   └── service
│       │       └── Relatorio.java      (relatórios com Stream API)
│       └── resources
│           └── view
│               └── Produto.fxml        (tela – Scene Builder)
├── .gitignore
├── mvnw                                (Maven Wrapper – Linux/macOS)
├── mvnw.cmd                            (Maven Wrapper – Windows)
├── pom.xml                             (configuração do Maven)
├── produtos.csv                        (criado automaticamente ao cadastrar)
├── README.md
└── RELATORIO.md
```

## 5. Como executar

### Pré-requisitos
- **JDK 17 ou superior** instalado.
- **Maven** (já incluso no IntelliJ IDEA).
- **IntelliJ IDEA** (ou outra IDE com suporte a Maven).

### Opção A – Pelo IntelliJ IDEA
1. Baixe o projeto (ZIP ou, depois que o repositório for criado, `git clone <link do repositório>`) e extraia a pasta.
2. No IntelliJ, escolha **File > Open** e selecione a pasta do projeto (onde está o `pom.xml`).
3. Aguarde o Maven baixar as dependências (ou clique em *Reload All Maven Projects*).
4. Confirme que o arquivo da tela está em `src/main/resources/view/Produto.fxml`.
5. Execute **Build > Rebuild Project**.
6. Abra `src/main/java/app/Main.java`, clique com o botão direito e escolha **Run 'Main.main()'**.

### Opção B – Pelo terminal
Na pasta do projeto, execute:

```
.\mvnw.cmd clean javafx:run
```

### Arquivo `module-info.java`
O projeto usa módulos. O conteúdo deve ser:

```java
module org.sysprojeto {
    requires javafx.controls;
    requires javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    exports app;
}
```

> Se você alterar o nome do módulo, mantenha o mesmo nome no `pom.xml` (`mainClass`).

### Editando a tela no Scene Builder
Clique com o botão direito em `Produto.fxml` > **Open in SceneBuilder**.

## 6. Como usar o sistema

1. **Cadastrar:** na aba *Produtos*, preencha *Dados do Produto* e *Dados da Categoria* e clique em **Cadastrar**.
2. **Consultar:** digite o código em *Consultar por Código* e clique em **Consultar**.
3. **Listar:** clique em **Listar Todos** para exibir todos os produtos.
4. **Atualizar:** clique em uma linha da tabela, altere os campos (menos o código) e clique em **Atualizar**.
5. **Excluir:** selecione uma linha da tabela e clique em **Excluir**.
6. **Relatórios:** abra a aba *Relatórios* e clique no relatório desejado.

## 7. Formato do arquivo CSV

Cada linha representa um produto, com os campos separados por `;` na ordem:

```
codigo;nome;descricao;dataFabricacao;dataValidade;precoCompra;precoVenda;quantidadeEstoque;categoriaId;categoriaNome;categoriaDescricao;categoriaSetor
```

Exemplo:

```
ABC12345;Arroz Branco 5kg;Arroz tipo 1;2026-01-10;2027-01-10;20.00;28.50;50;1;Graos e Cereais;Cereais em geral;Mercearia
```

> Datas no formato `aaaa-mm-dd`. O caractere `;` digitado nos campos de texto é trocado por `,` para não quebrar o arquivo.

## 8. Decisões de design
- **Organização em camadas:** `model` (regras e dados), `repository` (arquivo), `service` (relatórios), `controller` (eventos) e `app` (inicialização).
- **Validação no modelo:** as regras ficam nos construtores de `Produto` e `Categoria`, que lançam `IllegalArgumentException`. Assim nenhum objeto inválido é criado.
- **Composição:** `Produto` possui uma `Categoria`, gravada na mesma linha do CSV.
- **Interface separada da lógica:** a tela está no FXML e o `ProdutoController` apenas liga os eventos às classes do sistema.
- **Dinheiro com `BigDecimal`** e datas com `LocalDate`, conforme o enunciado.

---
Luigi Prudente Giani – 2026 