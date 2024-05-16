package com.gumaso.ScreenMatch.models;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
@Entity
@Table(name = "episodios")
public class Episodio {
    public Episodio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String tituloEp;
    private Integer numeroEp;
    private Double avaliacao;
    private LocalDate dataDeLancamento;
    @ManyToOne
    private Serie serie;


    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = Integer.valueOf(temporada);
    }

    public String getTituloEp() {
        return tituloEp;
    }

    public void setTituloEp(String tituloEp) {
        this.tituloEp = tituloEp;
    }

    public Integer getNumeroEp() {
        return numeroEp;
    }

    public void setNumeroEp(Integer numeroEp) {
        this.numeroEp = numeroEp;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }
    @Override
    public String toString() {
        return "Episodio {" +
                "temporada= " + temporada +
                ", tituloEp= '" + tituloEp + '\'' +
                ", numeroEp= " + numeroEp +
                ", avaliacao= " + avaliacao +
                ", dataDeLancamento= " + dataDeLancamento +
                '}';
    }
    public Episodio(Integer temporada, DadosEp dadosEp){
        this.temporada = temporada;
        this.tituloEp = dadosEp.titulo();
        this.numeroEp = dadosEp.numeroEp();
        try{
            this.avaliacao = Double.valueOf(dadosEp.avaliacao());
        }catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }
        try {
            this.dataDeLancamento = LocalDate.parse(dadosEp.dataDeLancamento());

        }catch (DateTimeException e){
            this.dataDeLancamento = null;
        }

    }
}