package com.gumaso.ScreenMatch.controller;

import com.gumaso.ScreenMatch.dto.SerieDTO;
import com.gumaso.ScreenMatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService service;
    @GetMapping
    public List<SerieDTO> listarSeries() {
        return service.obterSeries();
    }
    @GetMapping("top5/")
    public List<SerieDTO> obterTop5(){
        return service.obterTop5Series();

    }
    @GetMapping("lancamentos/")
    public List<SerieDTO> ultimosLancamentos(){
        return service.obterUltimosLancamentos();
    }
    @GetMapping("{id}/")
    public SerieDTO obterPorId(@PathVariable Long id){
        return service.obterSeriePorId(id);

    }
}
