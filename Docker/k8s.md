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
# Apply
# -----
#  - Apply says configured (not created) which means the existing pod was updated with new config.
##
> kubectl apply -f client-pod.yaml
pod/client-pod configured

##
# Get -- Status of object(s)
# --------------------------
##
> kubectl get pods
NAME         READY   STATUS    RESTARTS   AGE
client-pod   1/1     Running   9          46h

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



The number of containers, the name of container and port cannot be updated using kubectl apply

```bash
spec:
  containers:                               # Can't add/del containers using 'kubectl apply'
    - name: client                          # Can't modify name using 'kubectl apply'
      image: raghubs81/multi-web-server     # Image CAN be updated  
      ports: 
        - containerPort: 3030               # Can't modify using 'kubectl apply'
```



# Deployment

A Deployment is a K8s object that **monitors** and **maintains** (ensure right number exist and are up and running) a set of **identical** pods (same config). 

- We operate at the granularity of pods mostly during dev. In production, we deal with deployment as it is easier to manage a set of pods.
- A development monitors the stage of each pod and updates as necessary. Any failed pod shall be restarted/recreated.