## Maven Build
```
mvn clean install
```
## Docker Image Build 
```
docker build -t user_service .
```
## Docker Push
```
docker tag user_service loriszn/server-setup:user_service
```
```
docker push loriszn/server-setup:user_service
```
