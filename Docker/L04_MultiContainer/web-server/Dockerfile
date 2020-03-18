# -------------------------------------------------------------------------------------------------
# Build the production files of the node application
# -------------------------------------------------------------------------------------------------
FROM node:alpine as builder

WORKDIR /home/app

COPY package.json .
RUN npm install
COPY . .

# Build the production version
# We are not using the default start command (i.e CMD npm run start) since npm is not a production server.
# We instead build the production ready files and copy them nginx which is a production ready server.
RUN npm run build

# -------------------------------------------------------------------------------------------------
# Deploy production files in nginx server
# -------------------------------------------------------------------------------------------------

# NOTE: 
#  - This is the second 'FROM' in the Dockerfile. This is possible because the eariler 'FROM' is referenced as 'builder'.
#  - Using the 'builder' reference we copy the production ready files generated in /home/app/build directory.
FROM nginx:latest
EXPOSE 3000
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /home/app/build /usr/share/nginx/html