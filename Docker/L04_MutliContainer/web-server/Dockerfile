# -------------------------------------------------------------------------------------------------
# Build the production files of the node application
# -------------------------------------------------------------------------------------------------
FROM node:alpine as builder

WORKDIR /home/app

COPY package.json .
RUN npm install
COPY . .

# Build the production version
# We are not using the default start command (i.e CMD) as the default start command (for container created using this image) 
# is provided by nginx that follows
RUN npm run build

# -------------------------------------------------------------------------------------------------
# Deploy production files in nginx server
# -------------------------------------------------------------------------------------------------
FROM nginx:latest
EXPOSE 3000
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /home/app/build /usr/share/nginx/html