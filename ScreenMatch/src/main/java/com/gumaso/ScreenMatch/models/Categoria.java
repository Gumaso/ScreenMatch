package com.gumaso.ScreenMatch.models;

import java.awt.*;

public enum Categoria {
    ACAO("Action", "Ação"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    ROMANCE("Romance", "Romance"),
    AVENTURA("Aventure", "Aventura");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;

    }

    public static Categoria fromString(String texto){
        for (Categoria categoria: Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a serie");
    }

    public static Categoria fromStringPortugues(String texto){
        for (Categoria categoria: Categoria.values()){
            if (categoria.categoriaPortugues.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a serie");
    }
}
