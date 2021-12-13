

docker build -t illusionxd/service-communications:latest .
docker push illusionxd/service-communications
kubectl apply -f workloads.yaml

