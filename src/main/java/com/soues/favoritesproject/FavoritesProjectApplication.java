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
			try {

				// Définit les variables de l'utilisateur et de la base a dumper.
				String MYSQL_USER = "favuser" ;      // "favuser";
				String MYSQL_PASSWORD = "favuser" ;  // "favuser";
				String MYSQL_DATABASE = "favorites" ;
				String BACKUP_FILENAME = "backup.sql";
				String BACKUP_PATH = "src/main/resources/";
				// Crée la liste des arguments de la commande

				List <String> command = new ArrayList <> () ;

				command.add ("mysqldump") ;
				command.add ("-u " + MYSQL_USER) ;
				command.add ("-p" + MYSQL_PASSWORD) ;
				command.add (MYSQL_DATABASE) ;
				command.add ("> " + BACKUP_PATH+BACKUP_FILENAME) ;
				// Crée un processus pour exécuter la commande.
				ProcessBuilder processBuilder = new ProcessBuilder (command) ;
				// Redirige la sortie de la commande vers un fichier
				processBuilder.redirectOutput (ProcessBuilder.Redirect.to (new File(BACKUP_PATH+BACKUP_FILENAME))) ;
				processBuilder.redirectErrorStream (true) ; // Redirige la sortie d'erreur vers la sortie standard
				Process process = processBuilder.start () ; // Démarre le processus
				int exitCode = process.waitFor () ;         // Attend que le processus se termine
				if (exitCode == 0) System.out.println ("Sauvegarde réussie.") ;
				else System.out.println ("Error : " + exitCode) ;
			} catch (IOException | InterruptedException ignored) {
				// Les exceptions sont ignorées
				System.out.println ("Error AU CAS OU") ;
				System.out.println(ignored.getMessage());
			}
		}

		private void CheckValidityURL() {
			for (FavoriteItem favoriteItem: favoriteService.findAll()) {
				Long Id = favoriteItem.getCategory().getId();
				FavoriteDefinition definition = new FavoriteDefinition(favoriteItem.getId(),favoriteItem.getLabel(),favoriteItem.getLink());
				favoriteService.saveRobot(definition,Id);
			}
		}
	}

}
