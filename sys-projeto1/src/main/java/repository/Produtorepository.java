package repository; // pacote da persistência

import java.io.BufferedReader; // leitor de arquivo com linhas
import java.io.File; // representa o arquivo
import java.io.FileReader; // abre o arquivo para leitura
import java.io.IOException; // erro de entrada e saída
import java.io.PrintWriter; // escritor de arquivo
import java.math.BigDecimal; // valores em dinheiro
import java.time.LocalDate; // datas
import java.util.ArrayList; // lista dinâmica
import java.util.List; // interface de lista
import model.Categoria; // classe Categoria
import model.Produto;


public class Produtorepository { // classe que lê e grava o arquivo CSV

    private final String nomeArquivo = "produtos.csv"; // nome do arquivo CSV

    public List<Produto> carregar() { // lê todos os produtos do arquivo
        List<Produto> lista = new ArrayList<>(); // cria a lista que será devolvida
        File arquivo = new File(nomeArquivo); // objeto do arquivo
        if (!arquivo.exists()) { // se o arquivo ainda não existe
            return lista; // devolve lista vazia
        } // fim do if
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) { // abre o arquivo para leitura
            String linha; // guarda cada linha lida
            while ((linha = leitor.readLine()) != null) { // lê linha por linha até o fim
                if (linha.trim().isEmpty()) { // ignora linhas em branco
                    continue; // vai para a próxima linha
                } // fim do if
                String[] c = linha.split(";"); // separa os campos pelo ponto-e-vírgula
                Categoria categoria = new Categoria(Integer.parseInt(c[8]), c[9], c[10], c[11]); // monta a categoria com os campos 8 a 11
                Produto produto = new Produto(c[0], c[1], c[2], LocalDate.parse(c[3]), LocalDate.parse(c[4]), new BigDecimal(c[5]), new BigDecimal(c[6]), Integer.parseInt(c[7]), categoria); // monta o produto
                lista.add(produto); // adiciona na lista
            } // fim do while
        } catch (IOException | RuntimeException e) { // trata erro de leitura ou de formato
            System.out.println("Erro ao carregar arquivo: " + e.getMessage()); // mostra o erro no console
        } // fim do try-catch
        return lista; // devolve a lista de produtos
    } // fim do carregar

    public void salvar(List<Produto> lista) throws IOException { // grava todos os produtos no arquivo
        try (PrintWriter escritor = new PrintWriter(nomeArquivo)) { // abre o arquivo para escrita (sobrescreve)
            for (Produto p : lista) { // percorre cada produto
                escritor.println(p.toCsv()); // grava a linha CSV do produto
            } // fim do for
        } // fim do try (fecha o arquivo sozinho)
    } // fim do salvar
} // fim da classe Produtorepository