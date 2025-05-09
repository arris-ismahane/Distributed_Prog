# Define variables
DOCKER_IMAGE = ismahanearris/emperia_api
DOCKER_TAG = latest
JAR_FILE = target/*.jar
MINIKUBE_PATH = minikube
MINIKUBE_DEPLOYMENT = $(MINIKUBE_PATH)/emperia-deployment.yaml
MINIKUBE_SERVICE = $(MINIKUBE_PATH)/emperia-service.yaml
MINIKUBE_GATEWAY = $(MINIKUBE_PATH)/emperia-gateway.yaml
MINIKUBE_IP = $(shell minikube ip)
SERVICE_PORT = 80  # This is the external port exposed by the LoadBalancer service

# Default target
.PHONY: all
all: clean build push deploy

.PHONY: clean
clean:
	mvn clean package

# Step 1: Build the Docker image
.PHONY: build
build:
	docker build --no-cache --build-arg JAR_FILE=$(JAR_FILE) -t $(DOCKER_IMAGE):$(DOCKER_TAG) .

# Step 2: Push the Docker image to DockerHub
.PHONY: push
push:
	docker push $(DOCKER_IMAGE):$(DOCKER_TAG)

# Step 3: Deploy to Minikube
.PHONY: deploy
deploy:
	# Apply the Emperia service
	kubectl apply -f $(MINIKUBE_SERVICE)

	# Apply the Emperia deployment
	kubectl apply -f $(MINIKUBE_DEPLOYMENT)

	# Apply Gateway and VirtualService (Istio setup)
	kubectl apply -f $(MINIKUBE_GATEWAY)

	# Wait for the deployment rollout to complete
	kubectl rollout restart deployment emperia-deployment

	# Check the status of the services
	kubectl get svc

# Step 4: Access the application
.PHONY: access
access:
	# Expose the service URL
	@echo "Access the app at http://$(MINIKUBE_IP):$(SERVICE_PORT)"
	minikube service emperia-service
