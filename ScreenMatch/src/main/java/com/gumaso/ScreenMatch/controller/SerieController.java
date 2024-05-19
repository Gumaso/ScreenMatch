package com.gumaso.ScreenMatch.controller;

import com.gumaso.ScreenMatch.dto.SerieDTO;
import com.gumaso.ScreenMatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {
    @Autowired
    private SerieService service;
    @GetMapping("series/")
    public List<SerieDTO> listarSeries() {
        return service.obterSeries();
    }
    
}
