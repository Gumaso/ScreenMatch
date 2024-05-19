package com.gumaso.ScreenMatch.controller;

import com.gumaso.ScreenMatch.models.Serie;
import com.gumaso.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository repositorio;

    @GetMapping("series/")
    public List<Serie> listarSeries(){
        return repositorio.findAll();
    }
}
