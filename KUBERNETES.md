# Kubernetes Deployment - National Blood Transfusion Service Management System (NBT-SMS)

## 📦 Prerequisites

- Kubernetes Cluster (Minikube, kind, or production-grade cluster)
- `kubectl` CLI installed and configured
- Docker images of all microservices pushed to a container registry accessible by the cluster
- ConfigMaps or Secrets for environment variables management

---

## 🗂️ Suggested Structure for manifests

```plaintext
k8s/
├── manifests/
│   ├── infrastructure/
│   │   ├── deployment-postgres.yaml
│   │   ├── service-postgres.yaml
│   │   ├── secrets.yaml
│   │   └── ...
│   ├── microservices/
│   └── ...
├── kind/
│   ├── create-kind-cluster.sh
│   ├── delete-microservices-cluster.sh
│   └── kind-config.yaml
```


---

## ⚙️ Step-by-step instructions

### 1. Create the Postgres Deployment manifest

You can generate the manifest with:

```bash
kubectl create deployment postgres \
  --image=postgres:17 \
  --port=5432 \
  --replicas=1 \
  --dry-run=client -o yaml > k8s/manifests/infrastructure/deployment-postgres.yaml
```

### 2. Create the Postgres Service manifest

Expose Postgres on port 5434 (cluster-internal):

```bash
kubectl create service clusterip postgres \
  --tcp=5434:5432 \
  --dry-run=client -o yaml > k8s/manifests/infrastructure/service-postgres.yaml
```

### 3. Create the Kubernetes Secret from your existing .env
Assuming .env is located at the root project folder (../../../.env from infrastructure folder):
```bash
    kubectl create secret generic app-secrets --from-env-file=../../../.env --dry-run=client -o yaml > secrets.yaml
```

### Applying the manifests
Apply all manifests in order:
```bash
  kubectl apply -f secrets.yaml
  kubectl apply -f deployment-postgres.yaml
  kubectl apply -f service-postgres.yaml
```

### Stopping / Cleaning up
If you want to delete the Postgres deployment, service, and secrets:
```bash
    kubectl delete deployment postgres
    kubectl delete service postgres
    kubectl delete secret app-secrets
```