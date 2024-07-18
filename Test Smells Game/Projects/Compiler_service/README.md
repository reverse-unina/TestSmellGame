## Docker Image Build 
```
docker build . -t compiler_service
```
## Docker Push
```
docker tag compiler_service loriszn/server-setup:compiler_service
```
```
docker push loriszn/server-setup:compiler_service
```
