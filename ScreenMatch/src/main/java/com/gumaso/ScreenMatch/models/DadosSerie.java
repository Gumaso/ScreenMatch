package com.gumaso.ScreenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer Totaltemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse
                         ) {
}
//Gênero = Genre
//Atores = Actors
//Pôster = Poster
//Sinopse = Plot