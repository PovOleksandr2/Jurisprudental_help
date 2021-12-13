#!/usr/bin/env bash

docker build -t illusionxd/service-identities:latest .
docker push illusionxd/service-identities

minikube delete
minikube start --vm-driver=docker
kubectl apply -f workloads.yaml
