FROM node:alpine

WORKDIR /home/app

COPY package.json .
RUN npm install
COPY . .

# dev, start are script names defined in package.json
CMD ["npm", "run", "start"]