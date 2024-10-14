## Maven Build
```
mvn clean install
```
## Docker Image Build
```
docker build -t leaderboard_service .
```
## Docker Push
```
docker tag leaderboard_service loriszn/server-setup:leaderboard_service
```
```
docker push loriszn/server-setup:leaderboard_service
```
