package com.gumaso.ScreenMatch;

import com.gumaso.ScreenMatch.principal.Principal;
import com.gumaso.ScreenMatch.repository.EpisodioRepository;
import com.gumaso.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository serieRepository;
	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(serieRepository);
		principal.exibeMenu();
	}
}
