@echo off

REM Docker Compose
echo Starting Docker containers with docker-compose...
cd ./Server-Setup
docker-compose up -d

echo Deployment complete.

pause
