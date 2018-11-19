FROM node:8.12.0

WORKDIR /usr/src/app

COPY package.json /usr/src/app
COPY package-lock.json /usr/src/app


RUN npm install

CMD npm run start