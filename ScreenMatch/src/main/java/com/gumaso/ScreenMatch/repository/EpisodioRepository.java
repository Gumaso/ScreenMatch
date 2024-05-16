package com.gumaso.ScreenMatch.repository;

import com.gumaso.ScreenMatch.models.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
}

