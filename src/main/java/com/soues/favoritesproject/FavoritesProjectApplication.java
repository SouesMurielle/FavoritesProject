package com.soues.favoritesproject;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.service.impl.FavoriteService;

import java.io.File ;
import java.nio.file.Files ;
import java.nio.file.Paths ;
import java.time.LocalDateTime ;
import java.time.format.DateTimeFormatter ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Timer ;
import java.util.Calendar ;
import java.util.TimerTask ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FavoritesProjectApplication {

  @Autowired
  private static FavoriteService favoriteService ;

	public FavoritesProjectApplication(FavoriteService favoriteService) {
		FavoritesProjectApplication.favoriteService = favoriteService;
	}

	public static void main (String [] args) {
		SpringApplication.run (FavoritesProjectApplication.class, args) ;
		// Declanche Une tache par minutes.
		new Timer ().scheduleAtFixedRate (new Robot (favoriteService), 0, 60 * 1000) ;
	}

	static class Robot extends TimerTask {
		private final FavoriteService favoriteService;
		Robot (FavoriteService favoriteService) {
			this.favoriteService = favoriteService ;
		}
		@Override
		public void run () {
			int minutes = Calendar.getInstance ().get (Calendar.MINUTE) ;
			int heure = Calendar.getInstance ().get (Calendar.HOUR_OF_DAY) ;
			if (minutes % 15 == 0)
				CheckValidityURL () ;
			if ((heure == 8 || heure == 20) && minutes == 3)
				getDatabaseBackup () ;
		}

		private static void getDatabaseBackup () {
			System.out.println ("Backup") ;
			// vérifier si le dossier "Backups" existe et le créer s'il n'existe pas.
			String pathName = "Backups" ;
			File dossier = new File (pathName) ;
			if ( ! Files.isDirectory (Paths.get (pathName))) dossier.mkdir () ;
			try {
				// Réation du nom du fichier.
				String currentDate = LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("_yyyy-MM-dd_HH-mm")) ;
				// création de la commande à passer à mysql.
				List <String> command = new ArrayList <> () ;
				command.add ("mysqldump") ;
				command.add ("-ufavuser") ;
				command.add ("-pfavuser") ;
				command.add ("favorites") ;
				// Création du processus qui exécute la commande, sa sortie est routé vers le fichier.
				ProcessBuilder procBuild = new ProcessBuilder (command) ;
				procBuild.redirectOutput (
					ProcessBuilder.Redirect.to (
						new File (pathName + "/" + "favorites" + currentDate + ".sql"
				))).redirectErrorStream (true) ;
				procBuild.start ().waitFor () ;
			} catch (Exception e) {
				System.out.println ("Probleme de sauvegarde.") ;
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
