package com.gumaso.ScreenMatch.dto;

import com.gumaso.ScreenMatch.models.Categoria;

public record SerieDTO(Long id,
                       String titulo, Integer totaltemporadas, Double avaliacao,
                       Categoria genero,
                       String atores,
                       String poster,
                       String sinopse) {
}
