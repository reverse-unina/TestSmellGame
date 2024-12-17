FROM node:lts-alpine3.18 AS builder
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
COPY nginx_docker.conf ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM nginx:1.15.8-alpine
COPY --from=builder /usr/src/app/dist/frontend-online /usr/share/nginx/html

COPY nginx_docker.conf /etc/nginx/conf.d/default.conf
#RUN systemctl restart nginx

