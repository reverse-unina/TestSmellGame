# Server_Setup
echo Creating Docker volume for assets...
docker volume create assets
echo Building Docker image for assets...
cd ./Server-Setup
docker build -f Dockerfile.assets -t assets-image .
echo Populating Docker volume with assets files...
docker run --rm -v assets:/mnt assets-image sh -c "
  mkdir -p /mnt/assignments &&
  cp /assignments/assignment1.json /mnt/assignments/ &&
  cp /assignments/assignment2.json /mnt/assignments/ &&
  cp /assignments/assignment3.json /mnt/assignments/ &&
  mkdir -p /mnt/levelconfig &&
  cp /levelconfig/levelConfig.json /mnt/levelconfig/ &&
  mkdir -p /mnt/badges &&
  cp /badges/peak.png /mnt/badges/ &&
  cp /badges/badge_bronze.png /mnt/badges/ &&
  cp /badges/badge_gold.png /mnt/badges/ &&
  cp /badges/badge_silver.png /mnt/badges/ &&
  cp /badges/climbing.png /mnt/badges/ &&
  cp /badges/quiz_master.png /mnt/badges/ &&
  cp /badges/starting-point.png /mnt/badges/ &&
  mkdir -p /mnt/missions &&
  cp /missions/mission1.json /mnt/missions/ &&
  cp /missions/mission2.json /mnt/missions/ &&
  cp /missions/mission3.json /mnt/missions/ &&
  cp /missions/mission4.json /mnt/missions/
"

# Docker Compose
echo Starting Docker containers with docker-compose...
docker-compose up -d

echo Deployment complete.
