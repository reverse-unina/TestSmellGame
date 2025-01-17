# Server_Setup
echo Creating Docker volume for assets...
docker volume create assets
echo Building Docker image for assets...
cd ./Server-Setup
docker build -f Dockerfile.assets -t assets-image .
echo Populating Docker volume with assets files...
docker run --rm -v assets:/mnt assets-image sh -c "
  mkdir -p /mnt/assignments &&
  cp /assignments/*.json /mnt/assignments/ &&
  mkdir -p /mnt/levelconfig &&
  cp /levelconfig/levelConfig.json /mnt/levelconfig/ &&
  mkdir -p /mnt/badges &&
  cp /badges/*.png /mnt/badges/ &&
  mkdir -p /mnt/missions &&
  cp /missions/*.json /mnt/missions/
"

# Docker Compose
echo Starting Docker containers with docker-compose...
docker-compose up -d

echo Deployment complete.
