package com.soues.favoritesproject;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.service.impl.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@SpringBootApplication
public class FavoritesProjectApplication {

	@Autowired
	private static FavoriteService favoriteService;

	public FavoritesProjectApplication(FavoriteService favoriteService) {
		FavoritesProjectApplication.favoriteService = favoriteService;
	}

	public static void main(String[] args) {

		SpringApplication.run(FavoritesProjectApplication.class, args);
		Timer timer = new Timer () ;
		timer.scheduleAtFixedRate (new MinuteTask (favoriteService), 0, 60 * 1000) ; // Une tâche exécutée chaques minutes.

	}

	static class MinuteTask extends TimerTask {
		private final FavoriteService favoriteService;

		MinuteTask(FavoriteService favoriteService) {
			this.favoriteService = favoriteService;
		}

		@Override
		public void run () {
			int nbMinutesToWait = 15;
			if (new Date().getMinutes () % nbMinutesToWait == 0 ) {
				System.out.println("=== Début de la validation de chaque lien ===");
				CheckValidityURL();
				System.out.println("=== Fin de la validation de chaque lien ===");
			}

			int heure = Calendar.getInstance ().get (Calendar.HOUR_OF_DAY) ;
			if ((heure == 12|| heure == 20) && Calendar.getInstance ().get (Calendar.MINUTE) == 0) {
				System.out.println("=== Début de la sauvegarde de la BD ===");
				getDatabaseBackup();
				System.out.println("=== Fin de la sauvegarde de la BD ===");
			}

		}

		private static void getDatabaseBackup() {
		}

		private void CheckValidityURL() {
			for (FavoriteItem favoriteItem: favoriteService.findAll()) {
				String lien = favoriteItem.getLink() ;
				Long Id = favoriteItem.getCategory().getId() ;

				FavoriteDefinition definition = new FavoriteDefinition(favoriteItem.getId(),favoriteItem.getLabel(),lien);
				try {
					URL url = new URL (lien) ;
					HttpURLConnection connection = (HttpURLConnection) url.openConnection ();
					connection.setRequestMethod ("GET") ;
					// C'est ce if qui se charge de tester reponse du lien.
					if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
						favoriteService.save(definition,Id,true);
						System.out.println (lien + " " + Id + " --> Status == TRUE");
					}
				} catch (IOException e) {
					favoriteService.save(definition,Id,false);
					System.out.println (lien + " " + Id + " --> Status == FALSE");
				}
			}
		}
	}

}
