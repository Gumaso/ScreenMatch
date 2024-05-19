package com.gumaso.ScreenMatch.service;

import com.gumaso.ScreenMatch.dto.EpisodioDTO;
import com.gumaso.ScreenMatch.dto.SerieDTO;
import com.gumaso.ScreenMatch.models.Categoria;
import com.gumaso.ScreenMatch.models.Serie;
import com.gumaso.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;
    public List<SerieDTO> obterSeries(){
        return converteDadosSerie(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDadosSerie(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDadosSerie(List<Serie> series){
        return series.stream().map(objSerie -> new SerieDTO(objSerie.getId(),
                objSerie.getTitulo(), objSerie.getTotaltemporadas(), objSerie.getAvaliacao(),
                objSerie.getGenero(), objSerie.getAtores(), objSerie.getPoster(),
                objSerie.getSinopse())).collect(Collectors.toList());
    }

    public List<SerieDTO> obterUltimosLancamentos() {
        return converteDadosSerie(repositorio.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serieOptional = repositorio.findById(id);
        if (serieOptional.isPresent()){
            Serie objSerie = serieOptional.get();
            return new SerieDTO(objSerie.getId(),
                    objSerie.getTitulo(), objSerie.getTotaltemporadas(), objSerie.getAvaliacao(),
                    objSerie.getGenero(), objSerie.getAtores(), objSerie.getPoster(),
                    objSerie.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasAsTemporadas(Long id) {

        Optional<Serie> serieOptional = repositorio.findById(id);
        if (serieOptional.isPresent()){
            Serie objSerie = serieOptional.get();
            return objSerie.getEpisodioLista().stream().map(obj -> new EpisodioDTO(obj.getTemporada(), obj.getNumeroEp(), obj.getTituloEp())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEp(), e.getTituloEp()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromStringPortugues(nomeGenero);
        return converteDadosSerie(repositorio.findByGenero(categoria));
    }
}
