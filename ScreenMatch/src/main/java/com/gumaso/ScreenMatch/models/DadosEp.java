package com.gumaso.ScreenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEp(@JsonAlias("Title")String titulo,
                      @JsonAlias("Episode")Integer numeroEp,
                      @JsonAlias("imdbRating")String avaliacao,
                      @JsonAlias("Released")String dataDeLancamento) {
}

