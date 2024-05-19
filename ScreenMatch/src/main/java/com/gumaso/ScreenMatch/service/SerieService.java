package com.gumaso.ScreenMatch.service;

import com.gumaso.ScreenMatch.dto.SerieDTO;
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
        return repositorio.findAll().stream().map(objSerie -> new SerieDTO(objSerie.getId(),
                objSerie.getTitulo(), objSerie.getTotaltemporadas(), objSerie.getAvaliacao(),
                objSerie.getGenero(), objSerie.getAtores(), objSerie.getPoster(),
                objSerie.getSinopse())).collect(Collectors.toList());
    }
}
