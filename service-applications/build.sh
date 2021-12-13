#!/usr/bin/env bash

docker build -t illusionxd/service-applications:latest .
docker push illusionxd/service-applications
kubectl apply -f workloads.yaml
