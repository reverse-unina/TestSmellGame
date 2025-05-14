## TSGame

The presence of test smells related to low-quality test cases is a known factor contributing to problems in maintaining both test suites and production code.  The  TSGame (Test Smell Game) capsule provides a serious game where students can familiarize with test smells by practicing with their detection and removal from JUnit test code. TSGame has been implemented as a Web-based application that allows a teacher to assign students test smell detection and refactoring tasks that they have to accomplish in game sessions. Upon completion of the tasks they have the possibility to gain rewards. 

TSGame is realized in the context of the ENACTEST Erasmus+ project (https://enactest-project.eu/) financed by the European Commission (Project Number 101055874) and by the GATT project financed by the University of Naples Federico II.

## How To Play
The instructions to play the game are reported in the `TS_Game Instructions` pdf file.

## Initial Configuration
The initial configuration of the game is reported in the `Initial Configuration.md` file in the wiki folder.

## Game Modes
The description of the Game Modes with further details about scores and achievements are reported in the `Game modes` file in the wiki folder.

## Editing Exercises
A teacher can edit the set of available exercises by following the instructions reported in the `Editing Exercises` file in the wiki folder.

## Prerequisites
TSGame is composed of several containers implementing the needed service and several different front-ends.
The installation of TSGame is based on the deploy of the containers in an updated Docker Container instance. The Web front-end expose the application on the http port 8080

## Installing TSGame

TestSmellGame has two installation modes:
- Thin Client, based on a web Frontend where all services are executed on docker containers;
- Thick Client, where Frontend and Compiler Service are embedded in a local application generated via Electron, while the remaining services are provided locally. This last mode is more efficient but it requires more resources on the client machine.

### Thin Client Installation
Thin Client installation can be automated using the build and deploy scripts, located inside the `Test Smells Game/Projects` directory. The steps to follow if you choose this option are the following:
1. Compiling and generating docker images (only for users that want to modify the source code):
    - On Linux:
       ```bash
       ./build.sh
       ```
    - On Windows:
       ```
       build.bat
       ```
    The build script is responsible for compiling the services that make up the tool and generating the associated images.
2. Containers Deployment (for all users) :
    - On Linux:
       ```bash
       ./deploy.sh
       ```
    - On Windows:
       ```
       deploy.bat
       ```
    The deploy script takes care of generating the containers from the service images and creating the local network with which the containers will communicate. In case you skip the first step, the predeined docker container images uploaded on DockerHub will be used.

## Manual build and deploy

It is possible to build and deploy manually each of the services (in particular if you want to modify just some of them) :

1. docker image generation:
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

2. Loading initial data (exercises):
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
3. Container generation and internal net enabling:
  ```bash
  docker-compose up -d
  ```


### Thick Client Installation
Thick Client installation requires compiling the Electron executable:

```bash
cd TestSmellGame/Projects/Frontend_electron
npm install
npm update
npm run electron-build
electron-packager .
```

The other services can be generated or deployed as in the Thin Client Installation


### Services Endpoints

Using Nginx and an API Gateway, a reverse proxy configuration was implemented, allowing access to all services through a single entry point on port `8080`. For completeness, the endpoints of all services are listed below:
![endpoints](https://github.com/user-attachments/assets/b74ce769-944f-4c83-b84b-abffed51ae91)
