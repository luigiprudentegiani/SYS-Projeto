# Relatório do Projeto – Sistema de Gerenciamento de Produtos

| Campo | Informação |
|---|---|
| **Aluno(a)** | Luigi Prudente Giani |
| **Curso** | Engenharia de Software – 4º Período |
| **Disciplina** | Programação Orientada a Objetos III |
| **Professor** | Daniel Corrêa da Silva |
| **Avaliação** | P1 |
| **Data de entrega** | 23/10/2026 |

## 1. Objetivo
Desenvolver um sistema em Java com interface gráfica JavaFX para gerenciar o cadastro de produtos de uma loja ou supermercado, aplicando programação orientada a objetos, manipulação de arquivos e a Stream API.

## 2. Funcionalidades implementadas
- **Cadastrar** um produto com todos os seus dados, incluindo a categoria.
- **Atualizar** um produto já cadastrado (o código não pode ser alterado).
- **Excluir** o produto selecionado na tabela.
- **Consultar** um produto específico pelo código.
- **Listar todos** os produtos cadastrados em uma tabela.
- **Limpar** o formulário.
- Ao clicar em uma linha da tabela, os dados são carregados no formulário.
- **Persistência em CSV** separado por ponto-e-vírgula (`;`): os dados são carregados ao iniciar e salvos após cada operação.
- **Quatro relatórios**, exibidos na aba "Relatórios":
    - produtos próximos ao vencimento (próximos 60 dias);
    - produtos com estoque baixo (menos de 10 unidades);
    - margem de lucro média por categoria;
    - produtos agrupados por setor da categoria.

## 3. Estrutura e decisões de design
O projeto foi dividido em pacotes, cada um com uma responsabilidade:

| Pacote | Função                                                        |
|---|---------------------------------------------------------------|
| `model` | Classes `Categoria` e `Produto`, com os dados e as validações |
| `repository` | Classe `Produtorepository`, que lê e grava o arquivo CSV      |
| `service` | Classe `Relatorio`, com os relatórios feitos com Stream API   |
| `controller` | Classe `Produtocontroller`, que trata os eventos da tela      |
| `app` | Classe `Main`, que inicia o JavaFX e abre a janela            |

Principais decisões:
- **Composição:** `Produto` possui um objeto `Categoria`, e os dois são gravados na mesma linha do CSV.
- **Validação no modelo:** as regras ficam nos construtores de `Produto` e `Categoria`. Assim, um objeto inválido nunca é criado, seja pelo formulário ou pela leitura do arquivo.
- **Encapsulamento:** os atributos são privados e acessados por métodos `get`.
- **Tipos adequados:** valores em dinheiro usam `BigDecimal` e datas usam `LocalDate`.
- **Interface separada da lógica:** a tela foi feita em FXML (Scene Builder) e o controlador apenas liga os eventos às demais classes.
- **Organização da tela:** duas abas, "Produtos" (formulário, consulta e tabela) e "Relatórios".

## 4. Validações e tratamento de exceções
- Código: 8 caracteres alfanuméricos e único.
- Nome: não vazio, com no mínimo 3 caracteres.
- Data de fabricação: não pode ser posterior à data atual.
- Data de validade: não pode ser anterior à data de fabricação.
- Preços: valores positivos, com preço de venda maior que o de compra.
- Quantidade em estoque: não negativa.
- Categoria: nome e setor obrigatórios.

As validações lançam `IllegalArgumentException`. Erros de conversão de números (`NumberFormatException`) e de arquivo (`IOException`) também são tratados com `try-catch`, e as mensagens são mostradas ao usuário em caixas de diálogo (`Alert`), sem encerrar o programa.

## 5. Uso da Stream API
Os relatórios usam `filter` (escolher os produtos que atendem a uma condição), `map` (transformar cada produto em texto), `collect` com `joining`, e `groupingBy` com `averagingDouble` (agrupar por categoria e calcular a média da margem de lucro).

## 6. Persistência
Os dados ficam no arquivo `produtos.csv`, com um produto por linha e os campos separados por `;`. O caractere `;` digitado nos campos de texto é trocado por `,` para não quebrar o formato do arquivo.

## 7. Como executar
As instruções estão no arquivo `README.md`.

## 8. Conclusão
O sistema atende aos requisitos do enunciado: operações de cadastro, consulta, exclusão e listagem, persistência em CSV, validação de dados, tratamento de exceções e relatórios com Stream API, organizados em camadas para facilitar a leitura e a manutenção do código.