package com.gumaso.ScreenMatch.principal;


import com.gumaso.ScreenMatch.models.*;
import com.gumaso.ScreenMatch.repository.SerieRepository;
import com.gumaso.ScreenMatch.service.ConsumoAPI;
import com.gumaso.ScreenMatch.service.ConverterDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    //dark&season=1
    private final String API_KEY = "&apikey=818fb498";
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private ConverterDados converterDados = new ConverterDados();
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner sc = new Scanner(System.in);
    private List<DadosSerie> dadosSerieLista = new ArrayList<>();

    private SerieRepository serieRepository;
    private List<Serie> serieLista;
    private Optional<Serie> serieProcurada;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;

    }
    public Principal() {
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listas as series buscadas
                    4 - Buscar serie pelo nome
                    5 - Buscar serie mais avaliada por categoria
                    6 - Buscar serie pela categoria
                    7 - Buscar series por ator
                    8 - Top 3 Series mais avaliadas
                    9 - Buscar serie pela quantidade de temporada e uma nota minima de avaliação
                    10 - Buscar episodios por trecho
                    11 - Buscar top 5 episodios de uma serie
                    12 - Buscar serie por ano
                    0 - Sair                                 
                    """;
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePeloNome();
                    break;
                case 5:
                    buscarSerieMaisAvaliadasPeloGenero();
                    break;

                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    buscarSeriePorAtor();
                    break;
                case 8:
                    buscarTop5Series();
                    break;
                case 9:
                    buscarSeriePorTotalTemporadasAvaliacaoMinina();
                    break;
                case 10:
                    buscarEpisodioPorTrecho();
                    break;
                case 11:
                    buscarTop5EpisodiosPorSerie();
                    break;
                case 12:
                    episodiosPorSerieAno();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }




    private void buscarTop5Series() {
        List<Serie> top5Series = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        top5Series.stream().forEach(obj ->
                System.out.println(String.format("""
                        Nome da serie: %s
                        Avaliação: %s
                        %n
                        """, obj.getTitulo(), obj.getAvaliacao())
                ));
    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator: ");
        var nomeAtor = sc.nextLine();

        List<Serie> serieEncontrada = serieRepository.findByAtoresContainingIgnoreCase(nomeAtor);
        serieEncontrada.stream().forEach(System.out::println);
    }

    private void buscarSeriePorCategoria() {

        System.out.println("Digite a categoria desejada: ");
        var categoriaResposta = sc.nextLine();
        Categoria categoriaBuscada = Categoria.fromStringPortugues(categoriaResposta);
        List<Serie> categoriaEncontrada = serieRepository.findByGenero(categoriaBuscada);
        categoriaEncontrada.stream().forEach(System.out::println);
    }

    private void buscarSerieMaisAvaliadasPeloGenero() {
        System.out.println("Qual categoria deseja ver as series mais avaliadas?");
        var categoriaBuscada = sc.nextLine();
        Categoria categoriaEncontrada = Categoria.fromStringPortugues(categoriaBuscada);
        System.out.println("Qual a nota minima para ser comparada?");
        var avaliacaoBuscada = sc.nextDouble();
        sc.nextLine();
        List<Serie> seriesEncontradas = serieRepository.findByGeneroAndAvaliacaoGreaterThanEqual(categoriaEncontrada, avaliacaoBuscada);
        seriesEncontradas.stream().sorted(Comparator.comparing(Serie::getAvaliacao).reversed()).forEach(System.out::println);
    }

    private void buscarSeriePeloNome() {

        System.out.println("Digite um trecho do nome da Serie: ");
        var nomeSerie = sc.nextLine();
        serieProcurada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
        if (serieProcurada.isPresent()) {
            var serieVar = serieProcurada.get();
            System.out.println("Serie presente: ");
            System.out.println(String.format("""
                    Nome: %s
                    Episodios: %s
                    Avaliacao: %s
                    Genero: %s
                                        
                    """, serieVar.getTitulo(), serieVar.getEpisodioLista().size(), serieVar.getAvaliacao(), serieVar.getGenero()));
        } else {
            System.out.println("Serie não localizada!");
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        List<Serie> seriesInclusas = serieRepository.findAll();
        List<String> nomesSerie = new ArrayList<>();
        seriesInclusas.stream().forEach(obj -> nomesSerie.add(obj.getTitulo()));
        if (nomesSerie.contains(dados.titulo())) {
            System.out.println("Serie já inclusa no banco de dados");
        } else {
            System.out.println("Serie incluida com sucesso!");
            serieRepository.save(serie);
        }

    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = sc.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = converterDados.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = sc.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotaltemporadas(); i++) {
                var json = consumoAPI.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = converterDados.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodioLista(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas() {

        serieLista = serieRepository.findAll();

        serieLista
                .stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTotalTemporadasAvaliacaoMinina() {

        System.out.println("Número de temporadas:");
        var totalTemps = sc.nextInt();
        sc.nextLine();
        System.out.println("Média de avaliacação");
        var notaAvaliacao = sc.nextInt();
        sc.nextLine();
        List<Serie> lista2 = serieRepository.encontrarTemporadaAvaliacao2(totalTemps, notaAvaliacao);
        lista2.forEach(System.out::println);

    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Digite o trecho do episodio: ");
        var trechoEp = sc.nextLine();
        List<Episodio> episodiosEncontradoLista = serieRepository.buscarEpisodioPorTrecho(trechoEp);
        System.out.println(episodiosEncontradoLista);
    }

    private void buscarTop5EpisodiosPorSerie() {

        buscarSeriePeloNome();
        if (serieProcurada.isPresent()){
            Serie buscandoSerie = serieProcurada.get();
            List<Episodio> top5Eps = serieRepository.buscarTop5EpisodiosPorSerie(buscandoSerie);
            top5Eps.forEach(System.out::println);
        }


    }
    private void episodiosPorSerieAno() {
        buscarSeriePeloNome();
        if (serieProcurada.isPresent()){
            Serie buscandoSerie = serieProcurada.get();
            System.out.println("Digite o ano: ");
            var anoLancamento = sc.nextInt();
            sc.nextLine();
            var serieAchada = serieRepository.episodiosPorSerieAno(buscandoSerie, anoLancamento);
            System.out.println(serieAchada);

        }
    }


}