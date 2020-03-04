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

# Core Concepts

## Imperative Vs declarative approach
  - An imperative approach is to go to specific VM and/remote/update pods as required.
  - A declarative approach is to tell the K8s master of the desired state and let master do the dirty work.

# K8s Commands

```bash

# Apply
# -----
  
> kubectl apply -f client-pod.yaml
pod/client-pod created

> kubectl apply -f client-node-port.yaml
service/client-node-port created

# Get -- Status of object(s)
# -----------------------------
> kubectl get pods
NAME         READY   STATUS    RESTARTS   AGE
client-pod   1/1     Running   9          46h
  
```


  