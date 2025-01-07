## Docker Volume Create
```
docker volume create assets
```
## Docker Image Build 
```
docker build -f Dockerfile.assets -t assets-image .
```
## Docker Volume Populate
```
docker run --rm -v assets:/mnt assets-image sh -c "cp /assets/esperimento1.json /mnt/ && cp /assets/esperimento2.json /mnt/" 
```
## Docker Compose
```
docker compose-up -d
```