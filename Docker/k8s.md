# K8s

# Setup

## Softwares
1) Kubernetes : A framework for container orchestration
2) KVM        : A virutal machine (VM) manager (KVM UI -- Virutal Machine Manager 2.2.1)
3) minikube   : A tool that makes it easy to run Kubernetes locally.  
                Minikube runs a single-node Kubernetes cluster inside a VM on our laptop.



## Verify Setup

```bash
> minikube version 
minikube version: v1.7.2
commit: 50d543b5fcb0e1c0d7c27b1398a9a9790df09dfb

> minikube start

> minikube ip
192.168.39.190

> kubectl cluster-info
Kubernetes master is running at https://192.168.39.190:8443
KubeDNS is running at https://192.168.39.190:8443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy  
```

# Core Elements

## K8s object?
An object is created from a k8s config file and has a specific purpose. Some create containers (Pod), some may monitor a container other type of objects may setup netowrking (Service) etc.  
  - Each config file of k8s creates a 'K8s object' (A pod is just a type of object)
  - Following types of k8s objects exist -- Stateful, ReplicaController, Pod and Service 

## K8s Node
  - A k8s node is a machine (VM or physical).
  - A k8s node can run/contain one or more Pods.
  - A k8s node is gated by kube-proxy. Any communication to the k8s node from outside world is via the kube-proxy.

## K8s Master
  - Machines (or VMs) to manage worker nodes.
  - A master is responsible for maintaining the "desired state" specified by "Deployment"
    - A client tells a master about desired state (Like: number of copies of each pod)
    - The desired state of the client is captured in a file called "Deployment"
    - The master distributes the pods among the available worker nodes (to maximize availability).
    - The master keeps polling the nodes, restart pods and makes sure the "desired state" is ALWAYS met.

## K8s client (kubectl)  
  - A client command line (CLI) tool to interact with the master.

# Imperative Vs declarative approach

  - An imperative approach is to go to specific VM and/remote/update pods as required.
  - A declarative approach is to tell the K8s master of the desired state and let master do the dirty work.

## Imperative update: Modify container's image using kubectl apply

```bash
##
# Change the container image from "multi-web-server" to "multi-worker"
# --------------------------------------------------------------------
##
> git diff client-pod.yaml
diff --git a/Docker/L05_SimpleK8s/client-pod.yaml b/Docker/L05_SimpleK8s/client-pod.yaml
 spec:
   containers:
     - name: client
-      image: raghubs81/multi-web-server
+      image: raghubs81/multi-worker
       ports: 
         - containerPort: 3000

##
# Apply Objects
# --------------
##

# Apply says configured (not created) which means the existing object was updated with new config.
> kubectl apply -f client-pod.yaml
pod/client-pod configured

# Apply says created which means the existing object created.
> kubectl apply -f client-node-port.yaml
service/client-node-port created

##
# Get -- Status of object(s)
# --------------------------
##
> kubectl get pods
NAME         READY   STATUS    RESTARTS   AGE
client-pod   1/1     Running   9          46h

> kubectl get services
NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
client-node-port   NodePort    10.100.216.185   <none>        3050:31515/TCP   45m
kubernetes         ClusterIP   10.96.0.1        <none>        443/TCP          46m

##
# Describe an object
# ------------------
# Describe takes the object-type (in this case pod) and name of the object. 
# NOTE : "Image: raghubs81/multi-worker" indicates the image was updated imperatively
##
> kubectl describe pod client-pod
Name:         client-pod
Namespace:    default
Priority:     0
Node:         minikube/192.168.39.190
Start Time:   Mon, 02 Mar 2020 22:41:42 +0530
Labels:       component=web
Annotations:  kubectl.kubernetes.io/last-applied-configuration:
                {"apiVersion":"v1","kind":"Pod","metadata":{"annotations":{},"labels":{"component":"web"},"name":"client-pod","namespace":"default"},"spec...
Status:       Running
IP:           172.17.0.2
IPs:
  IP:  172.17.0.2
Containers:
  client:
    Container ID:   docker://ffd12dc77d6c75e33e903f5262d2e48c0931a8058dad5f41dcdd55f1c16f3b11
    Image:          raghubs81/multi-worker
    Image ID:       docker-pullable://raghubs81/multi-worker@sha256:933b30d5645b62b525b5ecf4ab9e78cd37cd65e293bec39397b2176bc5e967d3
    Port:           3000/TCP
    Host Port:      0/TCP
    State:          Running
...
```

## Imperative update: Modify container's port using kubectl apply

Certain properties can be modified using kubectl apply

```bash
##
# Change the container port
# -------------------------
##
> git diff client-pod.yaml
     - name: client
       image: raghubs81/multi-web-server
       ports: 
-        - containerPort: 3000
+        - containerPort: 3030

##
# Apply
# -----
# Apply fails 'port' field cannot be updated by 'apply' command
##
kubectl apply -f client-pod.yaml
The Pod "client-pod" is invalid: spec: Forbidden: pod updates may not change fields other than `spec.containers[*].image`, `spec.initContainers[*].image`, `spec.activeDeadlineSeconds` or `spec.tolerations` (only additions to existing tolerations)
```

Certain properties can't be modified using kubectl apply. The number of containers, the name of container and port cannot be updated using kubectl apply

```bash
spec:
  containers:                               # Can't add/del containers using 'kubectl apply'
    - name: client                          # Can't modify name using 'kubectl apply'
      image: raghubs81/multi-web-server     # Image CAN be updated  
      ports: 
        - containerPort: 3030               # Can't modify using 'kubectl apply'
```

## Imperative delete: Modify container's port using kubectl delete

The delete command gives 10s for the pod to perform pre-exit operations before it is forcefully removed.

```bash
> kubectl delete -f client-pod.yaml 
pod "client-pod" deleted
```

# Services

A k8s master manages several k8s worker nodes. 

## Worker Node and KubeProxy

- A k8s node is a machine (VM or physical).

- A k8s node can run/contain one or more Pods.

- A k8s node is gated by kube-proxy. Any communication to the k8s node from outside world is via the kube-proxy. The kube-proxy routes to a service object.

-  A Service (sub-type NodePort) k8s object uses the configured **selector** (In this case 'component:web') to find all k8s objects which have this tag/label. The service objects shall route requests to all matching k8s objects.

```bash
apiVersion: v1
kind: Service

metadata:
  name: client-node-port
  
spec:
  type: NodePort
  
  # port       : Port exposed for another k8s object inside our application (Internal listen port, for other k8s objects)
  # targetPort : Port targeted by this NodePort service (The origin-server port for this service)
  # nodePort   : Port exposed for outside world (External listen port, for outside world like browser)
  #              If not specified a random port in range 30000-32000 is assigned
  ports: 
    - port: 3050
      targetPort: 3000
      nodePort: 31515

  # This service is associated with object(s). 
  # Which one(es)? -- All those having the key:value pairs 'component:web'
  selector:
    component: web
```

## Why Services?

We were able to access the app using http://192.168.39.190:31515/index.html . We figured the IP of k8s node from minikube. Port 31515 is the port exposed for outside world. 

- A k8s node could be running several pods and each pod will have its own IP.
- The service abstracts the access to the pod. That is, even if a pod goes down and is automatically recreated giving it a new IP, the service will still be able to find and route requests to this newly created pod.

```bash
> minikube ip
192.168.39.190

> > kubectl get services
NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
client-node-port   NodePort    10.100.216.185   <none>        3050:31515/TCP   64m
kubernetes         ClusterIP   10.96.0.1        <none>        443/TCP          65m 

> kubectl get pods -o wide
NAME                                 READY   STATUS    RESTARTS   AGE   IP           NODE       NOMINATED NODE   READINESS GATES
client-deployment-85f7c489b8-ps8h7   1/1     Running   0          64m   172.17.0.5   minikube   <none>           <none>
```

# Deployment -- The declarative approach

A Deployment is a K8s object that **monitors** and **maintains** (ensure right number exist and are up and running) a set of **identical** pods (same config). 

- We operate at the granularity of pods mostly during dev. In production, we deal with deployment as it is easier to manage a set of pods.
- A development monitors the stage of each pod and updates as necessary. Any failed pod shall be restarted/recreated.

```bash
##
# Apply
# -----
#  - Apply says created which means new pod was created.
##
> kubectl apply -f client-deployment.yaml 
deployment.apps/client-deployment created

##
# Get status of the object
# ------------------------
# NOTE: The get command gets the status of a k8s "object". So, we can say get "deployments". 
#       Remember "Deployement" is a kind of k8s object.
##
> kubectl get pods
NAME                                 READY   STATUS    RESTARTS   AGE
client-deployment-85f7c489b8-ps8h7   1/1     Running   0          5m10s

> kubectl get deployments
NAME                READY   UP-TO-DATE   AVAILABLE   AGE
client-deployment   1/1     1            1           25m
```

## Scale Pods

```bash
> git diff client-deployment.yaml
 spec:
   # Number of identical pods to be maintained (using below template)
-  replicas: 1
+  replicas: 5
 
# Apply the updated deployment 
# NOTE: 'configured' indicates the existing deployement is updated (scaled up)
> kubectl apply -f client-deployment.yaml
deployment.apps/client-deployment configured

# Notice scaling
> kubectl get deployments
NAME                READY   UP-TO-DATE   AVAILABLE   AGE
client-deployment   2/5     5            2           102m

> kubectl get deployments
NAME                READY   UP-TO-DATE   AVAILABLE   AGE
client-deployment   5/5     5            5           103m   
```



## Update configuration on all pods

```bash
> git diff client-deployment.yaml
@@ -9,7 +9,7 @@ metadata:
-  replicas: 1
+  replicas: 5
 
@@ -33,5 +33,5 @@ spec:
         - name: client
           image: raghubs81/multi-web-server
           ports:
-            - containerPort: 3000
+            - containerPort: 3322

> kubectl apply -f client-deployment.yaml
deployment.apps/client-deployment configured

# Note that the pods have been newly created since port was updated and hence have not aged much.
> kubectl get pods
NAME                                 READY   STATUS    RESTARTS   AGE
client-deployment-77fb86dc87-5ztdh   1/1     Running   0          63s
client-deployment-77fb86dc87-bdrf7   1/1     Running   0          86s
client-deployment-77fb86dc87-dtvzb   1/1     Running   0          86s
client-deployment-77fb86dc87-lk7jz   1/1     Running   0          86s
client-deployment-77fb86dc87-rsxtj   1/1     Running   0          60s

# Get details of the pod to confirm
> kubectl describe pods client-deployment-77fb86dc87-5ztdh
Name:         client-deployment-77fb86dc87-5ztdh
...
Containers:
  client:
    Container ID:   docker://5aa4511902801545ef9f0adeef5e77042d82218182db6e3bb7cb82ab438861a4
    ...
    Port:           3322/TCP
    State:          Running
```



## Scale Down

```bash
> git diff client-deployment.yaml
@@ -9,7 +9,7 @@ metadata:
 
 spec:
   # Number of identical pods to be maintained (using below template)
-  replicas: 5
+  replicas: 2

> kubectl apply -f client-deployment.yaml
deployment.apps/client-deployment configured

> kubectl get pods
NAME                                 READY   STATUS        RESTARTS   AGE
client-deployment-77fb86dc87-5ztdh   0/1     Terminating   0          9m13s
client-deployment-77fb86dc87-bdrf7   1/1     Running       0          9m36s
client-deployment-77fb86dc87-dtvzb   0/1     Terminating   0          9m36s
client-deployment-77fb86dc87-lk7jz   1/1     Running       0          9m36s
client-deployment-77fb86dc87-rsxtj   0/1     Terminating   0          9m10s

> kubectl get pods
NAME                                 READY   STATUS    RESTARTS   AGE
client-deployment-77fb86dc87-bdrf7   1/1     Running   0          9m53s
client-deployment-77fb86dc87-lk7jz   1/1     Running   0          9m53s
```



## Update image

### Deployment won't do anything deployment configuration file remains same

Lets say the `raghubs81/multi-web-server` needs some changes. We made the changes and pushed it to docker-hub as follows.

```bash
# Update the contents of the image (multi-web-server) and push it to docker-hub
> cd ~/GitP/Learn/Docker/L04_MutliContainer/web-server
> docker build -t raghubs81/multi-web-server .
> docker login -u raghubs81
> docker push raghubs81/multi-web-server

# Try to update all pods to use this updated new image from the docker-hub
> cd ~/GitProjects/Learn/Docker/L05_SimpleK8s
> kubectl apply -f client-deployment.yaml
deployment.apps/client-deployment unchanged
```

All pods will continue to run with **old** image since the file `client-deployment.yaml` is unaltered. The underlying contents of the image are updated. In essence, if we update the image and tag it with the same tag (Default tag is 'latest') k8s thinks there is no change and hence does nothing.

> The tag 'latest' is just like any other  'foo' tag. The latest does NOT necessarily point the the most recent image! Any image can be tagged 'latest' just like any image can be tagged 'foo'.



### Workarounds for updating pods with new image

- We make code changes to the image
- We push the code changes to git
- The TravisCI picks the changes from git, builds the image and pushes to docker-hub
  - Now we need to make travis tag this image with a new tag (say v1, This can be done by updating travis.yml)
  - Otherwise, kubectl cannot differentiate this image from the previous (since the `client-deployment.yaml` file looks the same.)
- We now have to update the `client-deployment.yaml` to use the same version (v1)
- run `kubectl apply -f client-deployment.yaml`



### Using `kubectl set` (imperative command) to update image

```bash
# Kubectl set syntax
kubectl set image <object_type>/<object_name> <container_name>=<new_image_to_use>
```

See `client-deployment.yaml` for above details.

```bash
# Build with a version tag
> docker build -t raghubs81/multi-web-server:v4 .
...
Successfully tagged raghubs81/multi-web-server:v4

> docker push raghubs81/multi-web-server:v4 

> kubectl set image deployment/client-deployment client=raghubs81/multi-web-server:v4
deployment.apps/client-deployment image updated

> minikube ip 
192.168.39.190

# Access http://192.168.39.190:31515/index.html to ensure the changes are reflected.
```



# Pointing docker client to different server

By default docker client (The executable `docker`) tries to connect to the server (docker daemon) running on the same box.

```bash
> ps -ef | grep docker
root      1332     1  0 09:01 ?        00:00:43 /usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
```

However, to debug another node, we may want to connect to the corresponding docker server. In case of minikube run the following command.

```bash
> minikube docker-env
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.39.190:2376"
export DOCKER_CERT_PATH="/home/raghu/.minikube/certs"
export MINIKUBE_ACTIVE_DOCKERD="minikube"

# To point your shell to minikube's docker-daemon, run:
# eval $(minikube -p minikube docker-env)
```



