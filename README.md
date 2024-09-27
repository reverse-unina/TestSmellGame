# Istruzioni

Di seguito sono elencate le operazioni per installare il software.

All'interno della directory "Test Smells Game/Projects" è presente un file "setup.bat" che, una volta avviato, crea
l'infrastruttura server eseguendo i comandi elencati in modo sequenziale e automatico.

# N.B.
I comandi presenti all'interno di questo file e nel file di setup permettono di installare la versione Thin Client del sistema (frontend distribuito sul container docker) e non comprendono l'installazione della versione Electron, che è possibile installare a parte seguento i comandi mostrati nell'apposita sezione di questo documento.

# Compiler_service

## - Docker Image Build 
```
docker build -t compiler_service .
```
## - Docker Tag
```
docker tag compiler_service loriszn/server-setup:compiler_service
```


# Exercise_service

## - Maven Build
```
mvn clean install
```
## - Docker Image Build
```
docker build -t exercise_service .
```
## - Docker Tag
```
docker tag exercise_service loriszn/server-setup:exercise_service
```


# Frontend 

## - Docker Image Build 
```
docker build -t frontend .
```
## - Docker Tag
```
docker tag frontend loriszn/server-setup:frontend
```


# Leaderboard_service

## - Maven Build
```
mvn clean install
```
## - Docker Image Build
```
docker build -t leaderboard_service .
```
## - Docker Tag
```
docker tag leaderboard_service loriszn/server-setup:leaderboard_service
```


# User_service

## - Maven Build
```
mvn clean install
```
## - Docker Image Build 
```
docker build -t user_service .
```
## - Docker Tag
```
docker tag user_service loriszn/server-setup:user_service
```


# Server-Setup

## - Docker Volume Create
```
docker volume create assignments
```
## - Docker Image Build 
```
docker build -f Dockerfile.assignments -t assignments-image .
```
## - Docker Volume Populate

```
docker run --rm -v assignments:/mnt assignments-image sh -c "cp /assignments/esperimento1.json /mnt/ && cp /assignments/esperimento2.json /mnt/ && mkdir -p /mnt/levelconfig &&
 cp /assignments/levelconfig/levelConfig.json /mnt/levelconfig/
&& cp /assignments/levelconfig/badge_bronze.png /mnt/levelconfig/ && cp /assignments/levelconfig/badge_silver.png /mnt/levelconfig/ && cp /assignments/levelconfig/badge_gold.png /mnt/levelconfig/" 
```

La popolazione del volume per gli assignments avviene copiando i file json nell'immagine "assignments-image" (con il comando "COPY nome_file.json /assignments/" nel file "Dockerfile.assignments")
ed eseguendo il comando "docker run" citato sopra, dando come parametri i nomi dei file json appena inseriti. Inoltre, bisogna aggiungere manualmente il nome del file json nel 
metodo "getAssignments()"
della classe "AssignmentsService" all'interno del frontend.

## - Docker Compose
```
docker-compose up -d
```


Con il comando compose avverrà la creazione di tutti i container necessari alla creazione dell’infrastruttura che permette di ospitare una sessione di gioco.

In seguito all’avvio di tutti i container è possibile accedere all’applicativo, partendo dal calcolatore server, al seguente indirizzo:

http://localhost:4200/

In questo scenario avremo quindi installato correttamente il server in locale. 


# Installazione Client Electron

Per l'installazione della versione frontend Electron bisogna aprire il progetto presente in "Test Smells Game/Projects/Frontend_electron" (con un IDE appropriato) ed 
eseguire i seguenti comandi:

## - Electron Build & Run
```
npm run electron-build
```

## - Electron Packager
```
npx electron-packager ./ "Test Smells Game" --platform=win32
```

Se desideri eseguire il packaging su piattaforme diverse, quali Linux o macOS, puoi consultare la guida di Electron, disponibile al seguente link:
https://github.com/electron/packager.
