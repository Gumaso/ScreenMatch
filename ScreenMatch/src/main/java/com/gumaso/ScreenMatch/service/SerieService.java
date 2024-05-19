package com.gumaso.ScreenMatch.service;

import com.gumaso.ScreenMatch.dto.SerieDTO;
import com.gumaso.ScreenMatch.models.Serie;
import com.gumaso.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
