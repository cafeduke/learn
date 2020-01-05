# Typical Workflow
A typical workflow involves incremental and continuous  development, testing and deployment.

1. The project is hosted on a Git repository
2. A feature branch is created off the master branch
3. The user adds a new commit to the feature branch
    - A user pulls the feature branch to the desktop modifies several files.
    - The files are grouped into a "commit" that is pushed to the feature branch
4. Repeat #3 until the feature is completed.
5. Create a pull-request for the feature to be pulled into the master.
    - The pull-request shall trigger a sequence of actions (workflow)
    - The exact sequence of actions depends on the workflow that is setup.
    - Typically, the pull-request shall push the feature code to a service called "Travis CI"
    - TravisCI --- A continuous integration provider -- Run tests on the code base.
6. Feature is merged to master  
	- If the tests run by TravisCI are successful the feature is merged to master.
7. Update deployment with the commits to master
    - Code is pushed to TravisCI from master
    - Run tests  
    - Deploy (Eg: AWS Elastic Beanstalk)

# Basic react app

## Install and create app

- Install node

  ```bash
  curl -sL https://deb.nodesource.com/setup_10.x | sudo -E bash -
  sudo apt install nodejs
  ```

  

- Create react app

  ```bash
  npx create-react-app frontend  
  ```

## Basic operations

1. npm run start 
    - Starts a dev server (For DEVELOPMENT only)
    - Note this brings up a URL using which the app can be accessed. By default, the node app starts at port 3000.

2. npm run test --- Runs tests
```bash
PASS  src/App.test.js
  ✓ renders learn react link (36ms)

Test Suites: 1 passed, 1 total
Tests:       1 passed, 1 total
Snapshots:   0 total
Time:        2.201s
Ran all test suites.

Watch Usage: Press w to show more.  
```

3. npm run build
    - Creates a build directory with PRODUCTION version of application.
    - The build dir will have all the JS and files ready for production.

# Dev and Prod envs
A typical workflow shall have development (dev) and production (prod) environment.
Similarly in the docker world we shall have two docker files
    - Dockerfile.dev : For dev environment
    - Dockerfile     : For prod environment

## Dev environment

Dockerfile.dev is used to generate the image for the development (dev) environment.

### Docker compose

Docker compose provides a composite way to run docker commands -- It would otherwise require several docker commands to achieve the same. In this case, the docker-compose.dev.yml shall reference Dockerfile.dev.  Build and start docker dev container(s) for development in detached mode.

```shell
> docker-compose -f docker-compose.dev.yml up -d 
Starting docker-react-dev-web  ... done
Starting docker-react-dev-test ... done
```

The docker compose builds two containers (test) and (web)

```shell
> docker-compose -f docker-compose.dev.yml ps
        Name                       Command               State           Ports         
---------------------------------------------------------------------------------------
docker-react-dev-test   docker-entrypoint.sh npm r ...   Up                            
docker-react-dev-web    docker-entrypoint.sh npm r ...   Up      0.0.0.0:8801->8801/tcp
```

Stop all the containers

```shell
> docker-compose -f docker-compose.dev.yml stop
Stopping docker-react-dev-test ... done
Stopping docker-react-dev-web  ... done
```

Perform container up/stop several times and ensure that the same containers are reused instead of creating new containers (As it would have in the case of `docker run` command)

### About the containers

The web container

- The web container sets up the node web-app source code and installs dependencies (See Dockerfile.dev)
- Exposes port 3000 to access the app
- Creates volumes/bookmarks to map paths inside container to project directory. Any edits to project files will reflect in the container.

The test container

- Similar to the web container -- Has source code, lib and volumes setup.
- Uses startup command "npm run test" to run unit tests against the sources.
- Any change to project files will now reflect in two containers web and tests. 
  - Since the changes are in web container, they are evident in the browser
  - Since the changes are in test container, the tests run against them.

### The docker compose developer YAML

```yaml
version: "3"

# A custom name of the service/container
# In the below example, we have two containers 
#   - web : Has instructions to build/create/start a node web-app for the dev environment.
services: 
  
  web:
    build:
      context: "."
      dockerfile: Dockerfile.dev

    # Tag the image built  
    image: raghubs81/docker-react-dev-web:1.0
    container_name: "docker-react-dev-web"
    
    # <outside world port>:<container port> ==> Map outside world port to container port.
    # By default, the node app starts at port 3000 in the contianer. This is mapped to port 8080 in the outside world.
    ports: 
      - "8080:3000"
    
    # <outside world path>:<container path> 
    #   - Map outside world path to container. 
    #   - Any changes to the <outside world path> will reflect in the container -- Useful during development.
    # <container path>                    
    #   - These are container private files. Do NOT map this to anything outside container.
    volumes:
      - /home/app/node_modules
      - .:/home/app

  tests:
    
    build:
      context: "."
      dockerfile: Dockerfile.dev

    # Tag the image built  
    image: raghubs81/docker-react-dev-test:1.0
    container_name: "docker-react-dev-test"

    volumes:
      - /home/app/node_modules
      - .:/home/app

    # The default start command for this container
    command:
      ["npm", "run", "test"]

```

### The Dockerfile for developer

```dockerfile
# Select <image>:<version> as base image from https://hub.docker.com > Official Images
#   - apline picks a vanilla (basic) version
FROM node:alpine

# WORKDIR -- The pwd in the container
# Any relative operation we do (like copy) shall be relative to WORKDIR
#   - "cp foo foo" ===> "cp ./foo <WORKDIR>/foo"
#   - "cp foo foo" shall put the file foo from project dir in current machine to <WORKDIR>/foo in the container.
WORKDIR /home/app

# Copy package.json -- The dependency file descriptor for npm
# Install dependencies (requires package.json)
# Copy project files
COPY package.json .
RUN npm install
COPY . .

# Contianer startup command -- The command followed by options to be invoked by container after startup.
CMD ["npm", "run", "start"]
```

## Prod environment

Dockerfile is used generate the image for the production (prod) environment. We need to build the node project which created production level files in the `build` directory. We don't need the test container. Instead, we shall create a container based off nginx and transfer all the files in the `build` directory to the nginx hosting directory.

### The docker compose production YAML

```yaml
version: "3"

# A custom name of the service/container
# In the below example, we have two containers 
#   - web : Has instructions to build/create/start a node web-app for the environment.
services: 
  
  web:
    build:
      context: "."
      dockerfile: Dockerfile
    
    # <outside world port>:<container port> ==> Map outside world port to container port.
    # We are using nginx as production container which listens on port 80
    ports: 
      - "8080:80"
```

### The Dockerfile for production

Note that the purpose of the `Dockerfile` is to create an image. In this case, the image finally created shall be based off `nginx` with the hosting directory `/usr/share/nginx/html` having the production ready files that were copied from the build directory (After executing `npm run build`)

```dockerfile
# -------------------------------------------------------------------------------------------------
# Node web-app
# -------------------------------------------------------------------------------------------------

# Select <image>:<version> as base image from https://hub.docker.com > Official Images
#   - apline picks a vanilla (basic) version
#   - 'as' lets you reference the image. 
FROM node:alpine as builder

# WORKDIR -- The pwd in the container
# Any relative operation we do (like copy) shall be relative to WORKDIR
#   - "cp foo foo" ===> "cp ./foo <WORKDIR>/foo"
#   - "cp foo foo" shall put the file foo from project dir in current machine to <WORKDIR>/foo in the container.
WORKDIR /home/app

# Copy package.json -- The dependency file descriptor for npm
# Install dependencies (requires package.json)
# Copy project files
COPY package.json .
RUN npm install
COPY . .

# Contianer startup command -- The command followed by options to be invoked by container after startup.
# Here, we are using "npm run build" which shall create a build dir having production ready files.
RUN npm run build

# -------------------------------------------------------------------------------------------------
# nginx -- A production web server
# -------------------------------------------------------------------------------------------------
FROM nginx

# Copy the production ready files built in 'builder' container at path <WORKDIR>/build 
# to the destination /usr/share/nginx/html in 'nginx' container
#   - The destination is got by looking at the documentation of nginx container
#   - Accordingly, all files in /usr/share/nginx/html shall be served by the server.
COPY --from=builder /home/app/build /usr/share/nginx/html
```

