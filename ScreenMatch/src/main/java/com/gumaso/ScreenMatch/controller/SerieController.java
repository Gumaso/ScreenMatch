package com.gumaso.ScreenMatch.controller;

import com.gumaso.ScreenMatch.dto.SerieDTO;
import com.gumaso.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository repositorio;

    @GetMapping("series/")
    public List<SerieDTO> listarSeries() {
        return repositorio.findAll().stream().map(objSerie -> new SerieDTO(objSerie.getId(),
                objSerie.getTitulo(), objSerie.getTotaltemporadas(), objSerie.getAvaliacao(),
                objSerie.getGenero(), objSerie.getAtores(), objSerie.getPoster(),
                objSerie.getSinopse())).collect(Collectors.toList());
    }
}
