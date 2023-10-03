@echo off
setlocal
cls
set MYSQL_USER=favuser
set MYSQL_DATABASE=favorites
echo 1 Backup
echo 2 Restaure
echo 3 Initialisation
echo autre valeur pour sortir 
echo .
set /p Value="Choisir l'action a realiser : "
IF "%Value%"=="1" (
  echo Realisation du backup de la base de donnees.
  for /f "tokens=1-3 delims=/ " %%a in ("%DATE%") do (
    set "year=%%c"
    set "month=%%a"
    set "day=%%b"
  )
  for /f "tokens=1-2 delims=:.," %%a in ("%TIME%") do (
    set "hour=%%a"
    set "minute=%%b"
  )
  set "BACKUP_FILENAME=%MYSQL_DATABASE%_%year%-%month%-%day%_%hour%-%minute%.sql"
  echo Fournir le mot de passe de favuser
  mysqldump -u %MYSQL_USER% -p %MYSQL_DATABASE% > %BACKUP_FILENAME%
  if     %errorlevel% equ 0 echo Sauvegarde reussie.
  if not %errorlevel% equ 0 echo Erreur lors de la sauvegarde.
  pause
) ELSE IF "%Value%"=="2" (
  echo Restaurer la base de donnees favorites.
  dir "%MYSQL_DATABASE%*" /B
  set /p FILENAME="Choisir le fichier a restaurer : "
  echo Fournir le mot de passe de favuser
  mysql -u %MYSQL_USER% -p %BACKUP_FILENAME% < %FILENAME%
  pause
) ELSE IF "%Value%"=="3" (
  REM ROOT vérifie si le super utilisateur de la db favorites existe. s'il n'existe pas il le crée.
  REM ROOT verifie si la base de donnée favorites existe, il elle n'existe pas il la crée.
  REM ROOT affecte les droits au super utilisateur de la db notamment les droits PROCESS.
    echo Fournir le mot de passe de root
  mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS %MYSQL_DATABASE% ; CREATE USER IF NOT EXISTS '%MYSQL_USER%'@'localhost' IDENTIFIED BY '%MYSQL_PASSWORD%'; GRANT ALL PRIVILEGES ON %MYSQL_DATABASE%.* TO '%MYSQL_USER%'@'localhost' ; GRANT PROCESS ON *.* TO '%MYSQL_USER%'@'localhost' ; FLUSH PRIVILEGES ; "
  pause
)
endlocal
