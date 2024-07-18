## Maven Build
```
mvn clean install
```
## Docker Image Build
```
docker build -t exercise_service .
```
## Docker Push
```
docker tag exercise_service loriszn/server-setup:exercise-service
```
```
docker push loriszn/server-setup:exercise_service
```
