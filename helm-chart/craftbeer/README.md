# craftbeer

![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square)  ![Version: 1.0.0](https://img.shields.io/badge/Version-1.0.0-informational?style=flat-square)

Helm chart for install craftbeer on Kubernetes

# TL;DR - just show me how to install

If you have already carefully read all the below and just want to get reminded on how to install, please jump directly to the [Installation](#installation) section.

# Requirements

## Kubernetes Cluster

You will need to create a Kubernetes cluster locally using [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube), [microk8s](https://microk8s.io), [kind](https://kind.sigs.k8s.io), [k3s](https://k3s.io) or other tools.

Or use Kubernetes cluster in [EKS](https://aws.amazon.com/eks), [GKE](https://cloud.google.com/kubernetes-engine), [AKS](https://docs.microsoft.com/en-us/azure/aks), [DOKS](https://www.digitalocean.com/products/kubernetes) or other cloud provider.

## Kubectl

Install ``kubectl`` command.

```bash
VERSION=v1.22.2
KUBECTL_BIN='kubectl'

curl -LO https://storage.googleapis.com/kubernetes-release/release/${VERSION}/bin/linux/amd64/${KUBECTL_BIN}

chmod +x ${KUBECTL_BIN}

sudo mv ${KUBECTL_BIN} /usr/local/bin/${KUBECTL_BIN}

sudo ln -sf /usr/local/bin/${KUBECTL_BIN} /usr/bin/${KUBECTL_BIN}

kubectl version --client
```

## Helm

Install ``helm`` command.

```bash
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3

chmod 700 get_helm.sh

DESIRED_VERSION=v3.7.1 ./get_helm.sh

helm version
```

# Installation

* First, access a Kubernetes cluster.

* Create namespace ``craftbeer`` in Kubernetes cluster.

```bash
kubectl create namespace craftbeer
```

Create postgresql database:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade --install mypostgresql bitnami/postgresql --version 11.9.10 -n craftbeer \
 --set auth.username='craftbeer' \
 --set auth.password='craftbeer' \
 --set auth.database='craftbeer'
```

Create status of pods of the postgresql database:

```bash
kubectl get pods -n craftbeer
```

* Change the values according to the need of the environment in ``craftbeer/values.yaml`` file.

* Test the installation with command:

```bash
helm upgrade --install craftbeer -f craftbeer/values.yaml craftbeer/ -n craftbeer --dry-run
```

* To install/upgrade the chart with the release name `craftbeer`:

```bash
helm upgrade --install craftbeer -f craftbeer/values.yaml craftbeer/ -n craftbeer
```

These commands install craftbeer on the Kubernetes cluster in the default configuration. The [Parameters](#parameters) section lists the parameters that can be configured during installation.

# Accessing the application

Create port-forward:

```bash
kubectl port-forward svc/craftbeer 9000:9000 -n craftbeer
```

Access the application:

```bash
echo "Visit http://127.0.0.1:9000/beerhouse/swagger-ui.html to use swagger editor"

Or:

echo "Visit http://127.0.0.1:9000/beerhouse/monitoring/prometheus to get metrics in prometheus format"
```

# Troubleshooting

* List pods:

```bash
kubectl get pods -n craftbeer -l 'app.kubernetes.io/name=craftbeer'
```

* Get logs of deploy:

```bash
kubectl logs -f deploy/'craftbeer' -n craftbeer
```

* List all releases using follow command:

```bash
helm list --all --all-namespaces

#or

helm list -f 'craftbeer' -n craftbeer
```

* See status of the installation of application:

```bash
helm status craftbeer -n craftbeer
```

* See the history of versions of ``craftbeer`` application with command.

```bash
helm history craftbeer -n craftbeer
```

## Uninstallation

To uninstall/delete the `craftbeer` deployment:

```bash
helm uninstall craftbeer -n craftbeer
```

The command removes all the Kubernetes components associated with the chart and deletes the release.

> If the application is removed without the ``--keep-history`` option, the history will be lost and it will not be possible to roll back.

## Parameters

The following tables lists the configurable parameters of the chart and their default values.

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| affinity | object | `{}` |  |
| autoscaling.enabled | bool | `false` |  |
| autoscaling.maxReplicas | int | `20` |  |
| autoscaling.minReplicas | int | `1` |  |
| autoscaling.targetCPUUtilizationPercentage | int | `80` |  |
| fullnameOverride | string | `""` |  |
| image.pullPolicy | string | `"IfNotPresent"` |  |
| image.repository | string | `"jmilitao/craft-beer"` |  |
| image.tag | string | `"3.1.0"` |  |
| imagePullSecrets | list | `[]` |  |
| ingress.annotations | object | `{}` |  |
| ingress.className | string | `""` |  |
| ingress.enabled | bool | `false` |  |
| ingress.hosts[0].host | string | `"chart-example.local"` |  |
| ingress.hosts[0].paths[0].path | string | `"/"` |  |
| ingress.hosts[0].paths[0].pathType | string | `"ImplementationSpecific"` |  |
| ingress.tls | list | `[]` |  |
| livenessProbe.failureThreshold | int | `3` |  |
| livenessProbe.initialDelaySeconds | int | `30` |  |
| livenessProbe.path | string | `"/beerhouse/monitoring/health"` |  |
| livenessProbe.periodSeconds | int | `10` |  |
| livenessProbe.successThreshold | int | `1` |  |
| livenessProbe.timeoutSeconds | int | `5` |  |
| nameOverride | string | `""` |  |
| nodeSelector | object | `{}` |  |
| podAnnotations | object | `{}` |  |
| podSecurityContext | object | `{}` |  |
| properties.database.password | string | `"craftbeer"` |  |
| properties.database.url | string | `"postgresql://mypostgresql.craftbeer.svc.cluster.local:5432/craftbeer"` |  |
| properties.database.username | string | `"craftbeer"` |  |
| readinessProbe.failureThreshold | int | `3` |  |
| readinessProbe.initialDelaySeconds | int | `30` |  |
| readinessProbe.path | string | `"/beerhouse/monitoring/health"` |  |
| readinessProbe.periodSeconds | int | `10` |  |
| readinessProbe.successThreshold | int | `1` |  |
| readinessProbe.timeoutSeconds | int | `5` |  |
| replicaCount | int | `1` |  |
| resources.limits.cpu | string | `"1000m"` |  |
| resources.limits.memory | string | `"1Gi"` |  |
| resources.requests.cpu | string | `"100m"` |  |
| resources.requests.memory | string | `"128Mi"` |  |
| securityContext | object | `{}` |  |
| service.port | int | `9000` |  |
| service.type | string | `"ClusterIP"` |  |
| serviceAccount.annotations | object | `{}` |  |
| serviceAccount.create | bool | `true` |  |
| serviceAccount.name | string | `""` |  |
| tolerations | list | `[]` |  |

Change the values according to the need of the environment in ``craftbeer/values.yaml`` file.

# Documentation of Helm Chart

* Install helm-docs following the instructions on this page https://github.com/norwoodj/helm-docs

* Generate docs with helm-docs for this chart.

```bash
cd craftbeer

helm-docs
```

The markdown generation is entirely gotemplate driven. The tool parses metadata from charts and generates a number of sub-templates that can be referenced in a template file (by default ``README.md.gotmpl``). If no template file is provided, the tool has a default internal template that will generate a reasonably formatted README.