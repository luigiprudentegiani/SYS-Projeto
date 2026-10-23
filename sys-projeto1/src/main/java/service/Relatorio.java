package service; // pacote dos relatórios

import java.time.LocalDate; // datas
import java.util.List; // lista
import java.util.Map; // mapa chave-valor
import java.util.stream.Collectors; // coletores da Stream API
import model.Produto; // classe Produto


public class Relatorio { // classe com os relatórios feitos com Stream API

    public String proximosVencimento(List<Produto> produtos) { // produtos que vencem nos próximos 60 dias
        LocalDate hoje = LocalDate.now(); // data de hoje
        LocalDate limite = hoje.plusDays(60); // data daqui a 60 dias
        String texto = produtos.stream() // cria a stream da lista
                .filter(p -> !p.getDataValidade().isBefore(hoje) && !p.getDataValidade().isAfter(limite)) // filtra os que vencem entre hoje e 60 dias
                .map(p -> p.getCodigo() + " - " + p.getNome() + " - vence em " + p.getDataValidade()) // transforma cada produto em texto
                .collect(Collectors.joining("\n")); // junta os textos separando por linha
        return "Produtos Próximos Ao Vencimento (60 dias)\n" + texto; // devolve o título mais o texto
    } // fim do proximosVencimento

    public String estoqueBaixo(List<Produto> produtos) { // produtos com menos de 10 unidades
        String texto = produtos.stream() // cria a stream da lista
                .filter(p -> p.getQuantidadeEstoque() < 10) // filtra estoque menor que 10
                .map(p -> p.getCodigo() + " - " + p.getNome() + " - estoque: " + p.getQuantidadeEstoque()) // transforma cada produto em texto
                .collect(Collectors.joining("\n")); // junta os textos separando por linha
        return "Produtos Com Estoque Baixo (menos de 10)\n" + texto; // devolve o título mais o texto
    } // fim do estoqueBaixo

    public String margemMediaPorCategoria(List<Produto> produtos) { // margem de lucro média por categoria
        Map<String, Double> medias = produtos.stream() // cria a stream da lista
                .collect(Collectors.groupingBy(p -> p.getCategoria().getNome(), Collectors.averagingDouble(Produto::getMargemLucro))); // agrupa por categoria e calcula a média da margem
        String texto = medias.entrySet().stream() // cria a stream das entradas do mapa
                .map(e -> e.getKey() + ": " + String.format("%.2f", e.getValue()) + "%") // transforma cada entrada em texto
                .collect(Collectors.joining("\n")); // junta os textos separando por linha
        return "Margem De Lucro Média Por Categoria\n" + texto; // devolve o título mais o texto
    } // fim do margemMediaPorCategoria

    public String agrupadosPorSetor(List<Produto> produtos) { // produtos agrupados por setor
        Map<String, List<Produto>> grupos = produtos.stream() // cria a stream da lista
                .collect(Collectors.groupingBy(p -> p.getCategoria().getSetor())); // agrupa os produtos pelo setor da categoria
        String texto = grupos.entrySet().stream() // cria a stream das entradas do mapa
                .map(e -> "Setor " + e.getKey() + ":\n" + e.getValue().stream().map(p -> "   - " + p.getNome()).collect(Collectors.joining("\n"))) // monta o texto do setor com seus produtos
                .collect(Collectors.joining("\n")); // junta os setores separando por linha
        return "Produtos Agrupados Por Setor\n" + texto; // devolve o título mais o texto
    } // fim do agrupadosPorSetor
} // fim da classe Relatorio