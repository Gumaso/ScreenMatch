package com.gumaso.ScreenMatch.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totaltemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodioLista = new ArrayList<>();

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotaltemporadas() {
        return totaltemporadas;
    }

    public void setTotaltemporadas(Integer totaltemporadas) {
        this.totaltemporadas = totaltemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
    public List<Episodio> getEpisodioLista() {
        return episodioLista;
    }

    public void setEpisodioLista(List<Episodio> episodioLista) {
        episodioLista.forEach(e -> e.setSerie(this));
        this.episodioLista = episodioLista;
    }
    @Override
    public String toString() {
        return "genero=" + genero +
                " titulo='" + titulo + '\'' +
                " totaltemporadas=" + totaltemporadas +
                " avaliacao=" + avaliacao +
                " atores='" + atores + '\'' +
                " poster='" + poster + '\'' +
                " sinopse='" + sinopse + '\''+
                " episodios='" + episodioLista;
    }

    public Serie(DadosSerie serie) {
        this.titulo = serie.titulo();
        this.totaltemporadas = serie.Totaltemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(serie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(serie.genero().split(",")[0].trim());
        this.atores = serie.atores();
        this.poster = serie.poster();
        this.sinopse = serie.sinopse();
    }
    public Serie() {
    }

}
