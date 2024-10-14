FROM alpine:3.18.0


RUN apk update
RUN apk add maven
RUN apk add nodejs
RUN apk add npm
RUN apk add git
RUN apk --no-cache add openjdk17 --repository=https://dl-cdn.alpinelinux.org/alpine/v3.17/community
ENV HOME /root
ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8

WORKDIR .

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./



RUN npm install
# If you are building your code for production
# RUN npm ci --omit=dev


# Bundle app source
COPY . .

EXPOSE 8083
CMD [ "npm", "start" ]
