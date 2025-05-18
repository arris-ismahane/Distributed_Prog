# Emperia E-Commerce Microservices Deployment Guide

This project implements a simple e-commerce application for **Jewelry** products, composed of three microservices deployed on a local Kubernetes cluster (Minikube):

- **Jewelry Service:** Manages jewelry items (CRUD operations like posting new jewelry, listing, and buying). Built with Spring Boot and connects to its own database.
- **Emperia Backend Service:** Acts as an intermediate backend, handling business logic and orchestrating communication with the Jewelry Service via REST.
- **Emperia Frontend:** An Angular application providing a user interface to browse jewelry, post new items, and buy products, connecting to the backend via HTTP through a Kubernetes Gateway.

## Project Architecture

[Emperia Frontend (Angular)]  <--HTTP/Gateway-->  [Emperia Backend (Spring Boot)]  <--REST Template-->  [Jewelry Service (Spring Boot + DB)]

## Prerequisites

- Minikube installed and running  
- kubectl CLI installed and configured  
- Docker installed and Docker Hub account  
- (Optional) Use Minikube’s Docker daemon:  
  eval $(minikube docker-env)

## Microservices Components

| Service           | Technology          | Responsibilities                                   |
|------------------|---------------------|----------------------------------------------------|
| Jewelry Service  | Spring Boot + DB    | Manage jewelry items: create, list, buy           |
| Emperia Backend  | Spring Boot         | Business logic, connects Jewelry Service via REST |
| Emperia Frontend | Angular             | User interface to browse, post, and buy jewelry   |

## Deployment Steps

### 1. Deploy Jewelry Service

cd jewelry  
make all

### 2. Deploy Emperia Backend

cd emperia  
make all

## Accessing the Application

minikube service emperia-frontend-service

## Makefile Template (used in each service)

IMAGE_NAME = arrisismahane/(image-name  e.g emperia_api}
TAG = latest

build:  
	docker build -t $(IMAGE NAME):$(TAG) .

push:  
	docker push $(IMAGE NAME):$(TAG)

deploy:  
	kubectl apply -f deployment.yaml  
	kubectl apply -f service.yaml
or:
  cd minikube/
	kubectl apply -f .

clean:  
	mvn clean package


## REST API Endpoints

### Jewelry controllers

- GET /jewelry - List all jewelry  
- POST /jewelry/create - Add a new item  

### Emperia Backend

- GET /api/jewelry - Proxy to list jewelry  
- POST /api/jewelry - Proxy to add jewelry  
- POST /api/jewelry/{id}/buy - Proxy to buy jewelry

## Notes

- Make sure each image is pushed to Docker Hub before running make deploy  
- Use kubectl get all to check pod/service status  
- Frontend app should point to the correct backend gateway endpoint in environment.ts
