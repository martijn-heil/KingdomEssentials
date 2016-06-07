@echo off

call mvn install:install-file -Dfile=lib/Factions.jar -DgroupId=com.massivecraft -DartifactId=factions -Dversion=SNAPSHOT -Dpackaging=jar

call mvn install:install-file -Dfile=lib/Massivecore.jar -DgroupId=com.massivecraft -DartifactId=massivecore -Dversion=SNAPSHOT -Dpackaging=jar

pause