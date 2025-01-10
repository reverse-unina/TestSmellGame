/*
export const environment = {
  production: true,
  userServiceUrl: "http://localhost:8081",
  tokenServiceUrl: "http://localhost:8082",
  compilerServiceUrl: "http://localhost:8083",
  exerciseServiceUrl: "http://localhost:9090",
  leaderboardServiceUrl: "http://localhost:8087",
};
 */

export const environment = {
  production: true,
  userServiceUrl: window.location.origin === "http://localhost:4200"? "http://localhost:9000/user-service" : window.location.origin + "/user-service",
  tokenServiceUrl: "http://localhost:8082",
  compilerServiceUrl: window.location.origin === "http://localhost:4200"? "http://localhost:9000" : window.location.origin,
  exerciseServiceUrl: window.location.origin === "http://localhost:4200"? "http://localhost:9000/exercise-service" : window.location.origin + "/exercise-service",
  leaderboardServiceUrl: window.location.origin === "http://localhost:4200"? "http://localhost:9000/leaderboard-service" : window.location.origin + "/leaderboard-service",
};
