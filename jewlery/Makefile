# Define variables
DOCKER_IMAGE = ismahanearris/handmadeglow
DOCKER_TAG = latest
JAR_FILE = target/*.jar
MINIKUBE_PATH = minikube
MINIKUBE_HANDMADEGLOW_DEPLOYMENT = $(MINIKUBE_PATH)/handmadeglow-deployment.yaml
MINIKUBE_POSTGRES_DEPLOYMENT = $(MINIKUBE_PATH)/postgres-deployment.yaml
MINIKUBE_SERVICES = $(MINIKUBE_PATH)/postgres-service.yaml
MINIKUBE_HANDMADEGLOW_SERVICES = $(MINIKUBE_PATH)/handmadeglow-service.yaml
MINIKUBE_PVC = $(MINIKUBE_PATH)/postgres-pvc.yaml
MINIKUBE_IP = $(shell minikube ip)
SERVICE_PORT = 8080

# Default target
.PHONY: all
all: clean build push deploy

.PHONY: clean
clean:
	mvn clean package

# Step 1: Build the Docker image
.PHONY: build
build:
	docker build --build-arg JAR_FILE=$(JAR_FILE) -t $(DOCKER_IMAGE):$(DOCKER_TAG) .

# Step 2: Push the Docker image to DockerHub
.PHONY: push
push:
	docker push $(DOCKER_IMAGE):$(DOCKER_TAG)

# Step 3: Deploy to Minikube
.PHONY: deploy
deploy:
	# Apply Postgres PVC and Service first
	kubectl apply -f $(MINIKUBE_PVC)
	kubectl apply -f $(MINIKUBE_SERVICES)
	kubectl apply -f $(MINIKUBE_HANDMADEGLOW_SERVICES)

	# Apply the Postgres and HandmadeGlow deployments individually
	kubectl apply -f $(MINIKUBE_POSTGRES_DEPLOYMENT)
	kubectl apply -f $(MINIKUBE_HANDMADEGLOW_DEPLOYMENT)

	# Wait for the deployment rollout to complete
	kubectl rollout restart deployment handmadeglow-deployment
	kubectl rollout restart deployment postgres-deployment

	# Check the status of the services
	kubectl get svc

# Step 4: Access the application
.PHONY: access
access:
	# Expose the service URL for internal access (kubectl port-forward)
	@echo "Access the app internally via port-forwarding"
	kubectl port-forward svc/handmadeglow-service 8080:8080

	# Alternatively, check the service status
	@echo "Check the service status with kubectl get svc"
	kubectl get svc handmadeglow-service