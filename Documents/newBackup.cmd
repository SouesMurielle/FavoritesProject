
@echo off
setlocal

cls
set MYSQL_USER=favuser
set MYSQL_DATABASE=favorites
set BACKUP_FILENAME=BackupInitOrUpdate.sql

echo 1 Backup
echo 2 Restaure
echo 3 Initialisation
echo autre valeur pour sortir 
echo .
set /p Value="Choisir l'action a realiser : "

IF "%Value%"=="1" (
  echo Realisation du backup de la base de donnees.
  mysqldump -u %MYSQL_USER% -p %MYSQL_DATABASE% > %BACKUP_FILENAME%

  REM       mysqldump -u favuser -p favorites > Dump.backup

  if     %errorlevel% equ 0 echo Sauvegarde reussie.
  if not %errorlevel% equ 0 echo Erreur lors de la sauvegarde.
  rem scp -P port utilisateur@serveur:chemin/du/fichier distant_nom_destination
  pause
) ELSE IF "%Value%"=="2" (
  echo Realisation de restauration la base de donnees.
  mysql -u %MYSQL_USER% -p %BACKUP_FILENAME% < %BACKUP_FILENAME%
  pause
) ELSE IF "%Value%"=="3" (
  REM ROOT vérifie si le super utilisateur de la db favorites existe. s'il n'existe pas il le crée.
  REM ROOT verifie si la base de donnée favorites existe, il elle n'existe pas il la crée.
  REM ROOT affecte les droits au super utilisateur de la db notamment les droits PROCESS et SELECT.
  REM ROOT se deconnecte.
  mysql -u root -p -e "GRANT ALL PRIVILEGES ON '$MYSQL_DATABASE'.* TO '$MYSQL_USER'@'localhost'; FLUSH PRIVILEGES;"

mysql -u root -p -e "CREATE USER IF NOT EXISTS '%MYSQL_USER%'@'localhost' IDENTIFIED BY '%MYSQL_PASSWORD%';       CREATE DATABASE IF NOT EXISTS '%MYSQL_DATABASE%';      GRANT ALL PRIVILEGES ON '%MYSQL_DATABASE%'.* TO '%MYSQL_USER%'@'localhost';        GRANT SELECT ON favorites.* TO '%MYSQL_USER%'@'localhost';    GRANT PROCESS ON *.* TO '%MYSQL_USER%'@'localhost'; FLUSH PRIVILEGES;";

)

endlocal
