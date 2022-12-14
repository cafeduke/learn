#!/bin/bash
for x in mongodb-secret.yml mongodb-configmap.yml mongodb.yml mongo-express.yml
do
  echo ""
  echo "Executing: minikube kubectl -- apply -f $x"
  minikube kubectl -- apply -f $x
done
