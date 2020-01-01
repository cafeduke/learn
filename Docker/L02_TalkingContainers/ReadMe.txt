# -------------------------------------------------------------------------------------------------
# Containers and images
# -------------------------------------------------------------------------------------------------
~/Projects/Docker/L02_TalkingContainers> docker images                                                                                                                                    
REPOSITORY            TAG                 IMAGE ID            CREATED             SIZE
raghubs81/simpleweb   latest              35e1e59315e6        7 days ago          109MB
node                  alpine              fac3d6a8e034        2 weeks ago         106MB
redis                 latest              17a9b6c90ffd        3 weeks ago         98.2MB
busybox               latest              020584afccce        6 weeks ago         1.22MB
hello-world           latest              fce289e99eb9        11 months ago       1.84kB

 ~/Projects/Docker/L02_TalkingContainers> docker ps --all                                                                                                                                  
CONTAINER ID        IMAGE                 COMMAND                  CREATED             STATUS                     PORTS               NAMES
36d777872a43        raghubs81/simpleweb   "docker-entrypoint.s…"   7 days ago          Exited (0) 7 days ago                          my-simpleweb
d84ef6fba19a        busybox               "sh"                     2 weeks ago         Exited (137) 12 days ago                       my-shell
830e382944df        redis                 "docker-entrypoint.s…"   2 weeks ago         Exited (0) 9 days ago                          my-cache
e33e941adabf        busybox               "cal"                    2 weeks ago         Exited (0) 2 weeks ago                         my-cal
6792008bf11b        hello-world           "/hello"                 2 weeks ago         Exited (0) 2 weeks ago                         my-hello

# -------------------------------------------------------------------------------------------------
# docker-compose up
# -------------------------------------------------------------------------------------------------
~/Projects/Docker/L02_TalkingContainers> sudo docker-compose up                                                                                                        
Creating network "l02_talkingcontainers_default" with the default driver
Building node-app
Step 1/6 : FROM node:alpine
 ---> fac3d6a8e034
Step 2/6 : WORKDIR /home/app
 ---> Using cache
 ---> aafd4f3c0f1a
Step 3/6 : COPY package.json .
 ---> f0352a02fecf
Step 4/6 : RUN npm install
 ---> Running in 963e56eaba8e
npm notice created a lockfile as package-lock.json. You should commit this file.
npm WARN app No description
npm WARN app No repository field.
npm WARN app No license field.
added 54 packages from 41 contributors and audited 130 packages in 2.586s
found 0 vulnerabilities
Removing intermediate container 963e56eaba8e
 ---> 6796dec46526
Step 5/6 : COPY . .
 ---> 094fc5b72d83
Step 6/6 : CMD ["npm", "start"]
 ---> Running in db5278117afb
Removing intermediate container db5278117afb
 ---> 0c721aec4558
Successfully built 0c721aec4558
Successfully tagged l02_talkingcontainers_node-app:latest
WARNING: Image for service node-app was built because it did not already exist. To rebuild this image you must use `docker-compose build` or `docker-compose up --build`.
Creating l02_talkingcontainers_node-app_1     ... done
Creating l02_talkingcontainers_redis-server_1 ... done

Attaching to l02_talkingcontainers_redis-server_1, l02_talkingcontainers_node-app_1

redis-server_1  | 1:C 13 Dec 2019 19:24:54.001 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
redis-server_1  | 1:C 13 Dec 2019 19:24:54.001 # Redis version=5.0.7, bits=64, commit=00000000, modified=0, pid=1, just started
redis-server_1  | 1:C 13 Dec 2019 19:24:54.001 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf
redis-server_1  | 1:M 13 Dec 2019 19:24:54.008 * Running mode=standalone, port=6379.
redis-server_1  | 1:M 13 Dec 2019 19:24:54.008 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
redis-server_1  | 1:M 13 Dec 2019 19:24:54.008 # Server initialized
redis-server_1  | 1:M 13 Dec 2019 19:24:54.008 # WARNING overcommit_memory is set to 0! Background save may fail under low memory condition. To fix this issue add 'vm.overcommit_memory = 1' to /etc/sysctl.conf and then reboot or run the command 'sysctl vm.overcommit_memory=1' for this to take effect.
redis-server_1  | 1:M 13 Dec 2019 19:24:54.008 # WARNING you have Transparent Huge Pages (THP) support enabled in your kernel. This will create latency and memory usage issues with Redis. To fix this issue run the command 'echo never > /sys/kernel/mm/transparent_hugepage/enabled' as root, and add it to your /etc/rc.local in order to retain the setting after a reboot. Redis must be restarted after THP is disabled.
redis-server_1  | 1:M 13 Dec 2019 19:24:54.008 * Ready to accept connections
node-app_1      | 
node-app_1      | > @ start /home/app
node-app_1      | > node index.js
node-app_1      | 
node-app_1      | Listening at port 8080

# -------------------------------------------------------------------------------------------------
# docker compose start/stop -- Stop start in background
# -------------------------------------------------------------------------------------------------

~/Projects/Docker/L02_TalkingContainers> docker-compose stop                                                                                                                                ✔ 
Stopping l02_talkingcontainers_redis-server_1 ... done
Stopping l02_talkingcontainers_node-app_1     ... done

~/Projects/Docker/L02_TalkingContainers> docker-compose start                                                                                                                    ✔  1m 28s  
Starting redis-server ... done
Starting node-app     ... done

~/Projects/Docker/L02_TalkingContainers> docker ps                                                                                                                ✔  1m 28s  
CONTAINER ID        IMAGE                            COMMAND                  CREATED             STATUS              PORTS                    NAMES
0282aec6b8da        redis                            "docker-entrypoint.s…"   About an hour ago   Up About a minute   6379/tcp                 l02_talkingcontainers_redis-server_1
87304e09b2ce        l02_talkingcontainers_node-app   "docker-entrypoint.s…"   About an hour ago   Up About a minute   0.0.0.0:9080->8080/tcp   l02_talkingcontainers_node-app_1

# -------------------------------------------------------------------------------------------------
# Containers and images after docker-compose start
# -------------------------------------------------------------------------------------------------

~/Projects/Docker/L02_TalkingContainers> docker images        
REPOSITORY                       TAG                 IMAGE ID            CREATED             SIZE
l02_talkingcontainers_node-app   latest              0c721aec4558        56 minutes ago      110MB
raghubs81/simpleweb              latest              35e1e59315e6        7 days ago          109MB
node                             alpine              fac3d6a8e034        2 weeks ago         106MB
redis                            latest              17a9b6c90ffd        3 weeks ago         98.2MB
busybox                          latest              020584afccce        6 weeks ago         1.22MB
hello-world                      latest              fce289e99eb9        11 months ago       1.84kB

 ~/Projects/Docker/L02_TalkingContainers> docker ps --all                                                                                                                                  
CONTAINER ID        IMAGE                            COMMAND                  CREATED             STATUS                      PORTS                    NAMES
0282aec6b8da        redis                            "docker-entrypoint.s…"   52 minutes ago      Up 52 minutes               6379/tcp                 l02_talkingcontainers_redis-server_1
87304e09b2ce        l02_talkingcontainers_node-app   "docker-entrypoint.s…"   52 minutes ago      Up 52 minutes               0.0.0.0:9080->8080/tcp   l02_talkingcontainers_node-app_1
36d777872a43        raghubs81/simpleweb              "docker-entrypoint.s…"   7 days ago          Exited (0) 7 days ago                                my-simpleweb
d84ef6fba19a        busybox                          "sh"                     2 weeks ago         Exited (137) 12 days ago                             my-shell
830e382944df        redis                            "docker-entrypoint.s…"   2 weeks ago         Exited (0) 10 days ago                               my-cache
e33e941adabf        busybox                          "cal"                    2 weeks ago         Exited (0) 2 weeks ago                               my-cal
6792008bf11b        hello-world                      "/hello"                 2 weeks ago         Exited (0) 57 minutes ago                            my-hello

# -------------------------------------------------------------------------------------------------
# docker-compose ps -- Listing only ps in the pwd's yaml
# -------------------------------------------------------------------------------------------------

# Note this displays all running containers including the simpleweb
~/Projects/Docker/L02_TalkingContainers> docker ps                                                                                                                                          ✔ 
CONTAINER ID        IMAGE                            COMMAND                  CREATED             STATUS              PORTS                    NAMES
0282aec6b8da        redis                            "docker-entrypoint.s…"   2 hours ago         Up 7 seconds        6379/tcp                 l02_talkingcontainers_redis-server_1
87304e09b2ce        l02_talkingcontainers_node-app   "docker-entrypoint.s…"   2 hours ago         Up 7 seconds        0.0.0.0:9080->8080/tcp   l02_talkingcontainers_node-app_1
36d777872a43        raghubs81/simpleweb              "docker-entrypoint.s…"   7 days ago          Up 39 seconds       0.0.0.0:8080->8080/tcp   my-simpleweb

 ~/Projects/Docker/L02_TalkingContainers> docker-compose ps                                                                                                                                  ✔ 
                Name                              Command               State           Ports         
------------------------------------------------------------------------------------------------------
l02_talkingcontainers_node-app_1       docker-entrypoint.sh npm start   Up      0.0.0.0:9080->8080/tcp
l02_talkingcontainers_redis-server_1   docker-entrypoint.sh redis ...   Up      6379/tcp              







