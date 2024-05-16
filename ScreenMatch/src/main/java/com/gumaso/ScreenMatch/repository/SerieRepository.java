package com.gumaso.ScreenMatch.repository;

import com.gumaso.ScreenMatch.models.Categoria;
import com.gumaso.ScreenMatch.models.Episodio;
import com.gumaso.ScreenMatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    List<Serie> findByGeneroAndAvaliacaoGreaterThanEqual(Categoria genero, Double avaliacao);
    List<Serie> findByGenero(Categoria genero);
    List<Serie> findByAtoresContainingIgnoreCase(String atores);
    List<Serie> findTop3ByOrderByAvaliacaoDesc();
    List<Serie> findByTotaltemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalTemporadas, Double avaliacao);
    @Query(value = "SELECT * FROM series as s WHERE s.totaltemporadas >= 5 AND s.avaliacao >= 8;", nativeQuery = true)
    List<Serie> encontrarTemporadaAvaliacao();

    @Query(value = "SELECT s FROM Serie s WHERE s.totaltemporadas >= :totalDeTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> encontrarTemporadaAvaliacao2(int totalDeTemporadas, double avaliacao);
    @Query(value = "SELECT e FROM Serie s JOIN s.episodioLista e WHERE e.tituloEp ILIKE %:trechoEp%")
    List<Episodio> buscarEpisodioPorTrecho(String trechoEp);

    @Query(value = "SELECT e FROM Serie s JOIN s.episodioLista e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> buscarTop5EpisodiosPorSerie(Serie serie);


    @Query("SELECT e FROM Serie s JOIN s.episodioLista e WHERE s = :serie AND YEAR(e.dataDeLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieAno(Serie serie, int anoLancamento);


}
