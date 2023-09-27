package com.soues.favoritesproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// cette annotation indique à Spring que cette classe contient l'entry point qui va être excétuée par Spring
// il en faut obligatoirement une et une seule dans le projet
public class FavoritesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoritesProjectApplication.class, args);
	}

}
