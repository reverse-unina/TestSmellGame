@echo off

REM Compiler_service
echo Building Docker image for compiler_service...
cd Compiler_service
docker build -t compiler_service .
echo Tagging Docker image for compiler_service...
docker tag compiler_service loriszn/server-setup:compiler_service

REM Exercise_service
echo Building Maven project for exercise_service...
cd ../Exercise_service
call mvn clean install -f pom.xml
echo Building Docker image for exercise_service...
docker build -t exercise_service .
echo Tagging Docker image for exercise_service...
docker tag exercise_service loriszn/server-setup:exercise_service

REM Frontend
echo Building Docker image for frontend...
cd ../Frontend
docker build -t frontend .
echo Tagging Docker image for frontend...
docker tag frontend loriszn/server-setup:frontend

REM Leaderboard_service
echo Building Maven project for leaderboard_service...
cd ../Leaderboard_service
call mvn clean install -f pom.xml
echo Building Docker image for leaderboard_service...
docker build -t leaderboard_service .
echo Tagging Docker image for leaderboard_service...
docker tag leaderboard_service loriszn/server-setup:leaderboard_service

REM User_service
echo Building Maven project for user_service...
cd ../User_service
call mvn clean install -f pom.xml
echo Building Docker image for user_service...
docker build -t user_service .
echo Tagging Docker image for user_service...
docker tag user_service loriszn/server-setup:user_service

REM Server-Setup
echo Creating Docker volume for assignments...
docker volume create assignments
echo Building Docker image for assignments...
cd ../Server-Setup
docker build -f Dockerfile.assignments -t assignments-image .
echo Populating Docker volume with assignment files...
docker run --rm -v assignments:/mnt assignments-image sh -c "cp /assignments/esperimento1.json /mnt/ && cp /assignments/esperimento2.json /mnt/"

REM Docker Compose
echo Starting Docker containers with docker-compose...
docker-compose up -d

echo Installation complete.

pause