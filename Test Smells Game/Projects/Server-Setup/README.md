## Docker Volume Create
```
docker volume create assignments
```
## Docker Image Build 
```
docker build -f Dockerfile.assignments -t assignments-image .
```
## Docker Volume Populate
```
docker run --rm -v assignments:/mnt assignments-image sh -c "cp /assignments/esperimento1.json /mnt/ && cp /assignments/esperimento2.json /mnt/" 
```
## Docker Compose
```
docker compose-up -d
```