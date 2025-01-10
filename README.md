## Installazione

TestSmellGame prevede due modalità di installazione:
- Thin Clien, basato su un Frontend web dove tutti i servizi vengono eseguiri su container docker;
- Thick Client, dove Frontend e Compiler Service cono embeddati in un applicativo locale generato tramite Electron, mentre i restanti servizi vengono forniti tramite con.


### Installazione Thin Client
L'installazione Thin Client può essere automatizzata tramite l'utilizzo degli scritp di build e deploy, situati all'interno della directory `Test Smells Game/Projects`. I passi da eseguire se si opta per questa opzione sono i seguenti:
1. Compilazione e generazione delle immagini docker:
    - Su Linux:
       ```bash
       ./build.sh
       ```
    - Su Windows:
       ```
       build.bat
       ```
    Lo script di build si occupa di compilare i servizi che compongono il tool e generare le immagini associate.
2. Deployment dei container:
    - Su Linux:
       ```bash
       ./deploy.sh
       ```
    - Su Windows:
       ```
       deploy.bat
       ```
    Lo scritp di deploy si occupa di generare i container a partire dalle immagini dei servizi e creare la rete locale con cui i container comunicheranno.

**Nota bene**: lo script di deploy è eseguibile indipendentemente dallo script di build. Se le immagini non sono presenti localmente, lo script di deploy le scaricherà da dockerhub.

2. **Compilare i servizi e generare le immagini Docker**
   - Su Linux/MacOS:
     ```bash
     ./build.sh
     ```
   - Su Windows:
     ```
     build.bat
     ```
   Questo script compila ciascun servizio del tool e genera le relative immagini Docker.

Se si opta per build e deploy manuali, i passi da eseguire sono i seguenti:
1. Generazione delle immagini:
    - Compiler Service
        ```bash
        docker build -t compiler_service .
        docker tag compiler_service mick0974/testsmellsgame:compiler_service
        ```
    - Exercise Service
        ```bash
        docker build -t compiler_service .
        mvn clean install -f pom.xml
        docker build -t exercise_service .
        docker tag exercise_service mick0974/testsmellsgame:exercise_service
        ```
    - Frontend
        ```bash
        docker build -t frontend .
        docker tag frontend mick0974/testsmellsgame:frontend
        ```    
    - Leaderboard Service
        ```bash
        mvn clean install -f pom.xml
        docker build -t leaderboard_service .
        docker tag leaderboard_service mick0974/testsmellsgame:leaderboard_service
        ``` 
    - User Service
        ```bash
        mvn clean install -f pom.xml
        docker build -t user_service .
        docker tag user_service mick0974/testsmellsgame:user_service
        ``` 
    - Api Gateway
       ```bash
        mvn clean install -f pom.xml
        docker build -t api_gateway .
        docker tag api_gateway mick0974/testsmellsgame:api_gateway
        ```

2. Caricamento dati iniziali:
  ```bash
    docker volume create assets
    docker build -f Dockerfile.assets -t assets-image .
    docker run --rm -v assets:/mnt assets-image sh -c "
    mkdir -p /mnt/assignments &&
    cp /assignments/prova1.json /mnt/assignments/ &&
    cp /assignments/prova2.json /mnt/assignments/ &&
    cp /assignments/prova3.json /mnt/assignments/ &&
    mkdir -p /mnt/assignments/levelconfig &&
    cp /assignments/levelconfig/levelConfig.json /mnt/assignments/levelconfig/ &&
    mkdir -p /mnt/badges &&
    cp /badges/achieving-goal.png /mnt/badges/ &&
    cp /badges/badge_bronze.png /mnt/badges/ &&
    cp /badges/badge_gold.png /mnt/badges/ &&
    cp /badges/badge_silver.png /mnt/badges/ &&
    cp /badges/climbing.png /mnt/badges/ &&
    cp /badges/mission-statement.png /mnt/badges/ &&
    cp /badges/objective.png /mnt/badges/ &&
    mkdir -p /mnt/missions &&
    cp /missions/mission1.json /mnt/missions/ &&
    cp /missions/mission2.json /mnt/missions/
  "
  ```
3. Generazione dei container e della rete interna:
  ```bash
  docker-compose up -d
  ```


### Installazione Thick Client 
L'installazione Thick Client richiede la compilazione in locale dell'eseguibile Electron:
```bash
cd TestSmellGame/Projects/Frontend_electron
npm install
npm update
npm run electron-build
electron-packager .
```

I restanti servizi possono essere compilati e serviti come descritto nel punto precedente.


### Endpoint Servizi

Grazie all’utilizzo di Nginx e di un API Gateway, è stata implementata una configurazione di reverse proxy, consentendo di accedere a tutti i servizi tramite un unico punto di ingresso sulla porta `8080`. Di seguito, per completezza, sono riportati gli endpoint di tutti i servizi:

![endpoints](https://github.com/user-attachments/assets/b74ce769-944f-4c83-b84b-abffed51ae91)
