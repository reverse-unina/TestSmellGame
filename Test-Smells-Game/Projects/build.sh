# Compiler_service
echo Building Docker image for compiler_service...
cd Compiler_service
docker build -t compiler_service .
echo Tagging Docker image for compiler_service...
docker tag compiler_service loriszn/server-setup:compiler_service

# Exercise_service
echo Building Maven project for exercise_service...
cd ../Exercise_service
mvn clean install -f pom.xml
echo Building Docker image for exercise_service...
docker build -t exercise_service .
echo Tagging Docker image for exercise_service...
docker tag exercise_service loriszn/server-setup:exercise_service

# Frontend
echo Building Docker image for frontend...
cd ../Frontend
docker build -t frontend .
echo Tagging Docker image for frontend...
docker tag frontend loriszn/server-setup:frontend

# Leaderboard_service
echo Building Maven project for leaderboard_service...
cd ../Leaderboard_service
mvn clean install -f pom.xml
echo Building Docker image for leaderboard_service...
docker build -t leaderboard_service .
echo Tagging Docker image for leaderboard_service...
docker tag leaderboard_service loriszn/server-setup:leaderboard_service

# User_service
echo Building Maven project for user_service...
cd ../User_service
mvn clean install -f pom.xml
echo Building Docker image for user_service...
docker build -t user_service .
echo Tagging Docker image for user_service...
docker tag user_service loriszn/server-setup:user_service

# Api_gateway
echo Building Maven project for api_gateway...
cd ../Api_gateway
mvn clean install -f pom.xml
echo Building Docker image for api_gateway...
docker build -t api_gateway .
echo Tagging Docker image for api_gateway...
docker tag api_gateway loriszn/server-setup:api_gateway

# Server_Setup
echo Creating Docker volume for assets...
docker volume create assets
echo Building Docker image for assets...
cd ../Server-Setup
docker build -f Dockerfile.assets -t assets-image .
echo Populating Docker volume with assets files...
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

echo Build process complete.