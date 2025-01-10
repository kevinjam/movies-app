
# CI/CD and Deployment with Minikube

This project is an application that runs a movie service connected to a MongoDB database. It includes a local setup using Docker Compose for faster development and deployment on Minikube for Kubernetes.

## Reference Documentation
1. [Build and Run Locally with Docker](#1-build-and-run-locally-with-docker)
2. [Deploy to Minikube](#2-deploy-to-minikube)
3. [Required Minikube Setup Commands](#3-required-minikube-setup-commands)
4. [Access the Application](#4-access-the-application)
5. [CI/CD Pipeline with GitHub Actions](#5-cicd-pipeline-with-github-actions)
6. [Decisions, Assumptions, and Challenges](#6-decisions-assumptions-and-challenges)

## 1. Build and Run Locally with Docker

To run the application locally, Docker and Docker Compose are used. The database (MongoDB) is started alongside the application using Docker Compose for speed and ease.

### Prerequisites
- Docker (version 4.37.2)
- Docker Compose (version v2.31.0-desktop.2)
- JDK 21 (or later): Spring Boot supports Java 21 and above.
- Maven: Used for building the project

### Steps:
1. **Clone the repository:**
   ```bash
   git clone https://github.com/kevinjam/movies-app.git
   cd movies-app
   ```

2. **Build the application:**
   Run the following command to build the Spring Boot application:
   ```bash
   mvn clean install
   ```

3. **Dockerize the application:**
   Create the Docker image by running:
   ```bash
   docker build -t movies-app:latest .
   ```

4. **Verify the image:**
   Ensure the image is built successfully by listing all Docker images:
   ```bash
   docker images
   ```

5. **Run the Docker Compose:**
   Run the application in a Docker container:
   ```bash
   docker compose up (MacOs)
   ```
6. **Access the application:**
      Verify the application is running by going to:
      [http://localhost:9090/movies](http://localhost:9090/movies)

      [Postman collection link](https://github.com/kevinjam/movies-app/blob/main/postman_collection.json)

---

## 2. Deploy to Minikube

Minikube allows to run Kubernetes clusters locally for testing and development purposes.

### Steps:
1. **Install Minikube:** if not already installed.

   - **For macOS:**
     ```bash
     brew install minikube
     ```
   - **For Ubuntu:**
     ```bash
     sudo apt install minikube
     ```

2. **Start Minikube:**
   Start the Minikube cluster by running:
   ```bash
   minikube start
   ```
You can also check the status of the cluster by running:
   ```bash
   minikube status
   ```
3. **Navigate to the Kubernetes directory:**
   Go to the `k8s` directory inside your project root that contains the Kubernetes deployment files:
   - `mongodb-deployment.yaml`
   - `mongodb-service.yaml`
   - `movies-deployment.yaml`
   - `movies-service.yaml`

4. **Deploy to Minikube:**
   Apply the Kubernetes deployments with the following command:
   ```bash
   kubectl apply -f ./
   ```

5. **Verify the Deployments:**
   Check the services and deployments to ensure everything was created correctly:
   ```bash
   kubectl get services
   kubectl get pods
   kubectl describe pod movies-service-app
   ```

6. **Access the Minikube Dashboard (optional):**
   To access the Minikube dashboard, run:
   ```bash
   minikube dashboard
   ```
## Screenshots

*Kubernetes Deployment.*
![Kubernetes Deployment](https://i.imgur.com/t3XtF7S_d.webp?maxwidth=760&fidelity=grand?raw=true)

---

## 3. Required Minikube Setup Commands

Ensure you have the following commands and setups ready to work with Minikube:

1. **Start Minikube:**
   ```bash
   minikube start
   ```

2. **Check Kubernetes clusters:**
   ```bash
   kubectl config get-contexts
   ```

3. **Apply Kubernetes configurations (from your `k8s` folder):**
   ```bash
   kubectl apply -f .
   ```

4. **Get service and pod information:**
   ```bash
   kubectl get services
   kubectl get pods
   kubectl describe pod <pod-name>
   kubectl get all
   ```
*Commands.*
![Minikube Setup Commands](https://i.imgur.com/t3XtF7S_d.webp?maxwidth=720&fidelity=grand?raw=true)

---

## 4. Access the Application

Once Minikube is running and the deployments are successful, access the application via the following command:

```bash
minikube service movies-app-svc
```

This will display the URL where your application is running.

---

## 5. CI/CD Pipeline with GitHub Actions

The CI/CD pipeline is configured in GitHub Actions for continuous integration and deployment.

### CI/CD Configuration:
The pipeline configuration is located in the `.github/workflows/deploy.yaml` file in the project directory.

### Steps:
1. **Set up secrets:**
   Ensure that `DOCKER_USERNAME` and `DOCKER_PASSWORD` are provided in the repository secrets on GitHub under Secrets and Variables , click Actions.

2. **Trigger GitHub Actions:**
   Once you push code to the `main` branch, the GitHub Actions workflow will be triggered. It will:
   - Build the Docker image.
   - Push the Docker image to Docker Hub.

---

## 6. Decisions, Assumptions, and Challenges

### Decisions:
- **Minikube** was chosen for local Kubernetes testing due to its lightweight nature and ease of setup.
- **Docker Compose** was used to speed up the MongoDB setup locally for faster development cycles.
- **MongoDB** was selected as the database due to its flexibility and scalability for storing movie data.

### Assumptions:
- Kubernetes and Minikube are installed and set up correctly locally.
- Docker Hub credentials and access are properly configured for pushing and pulling images.

### Challenges:
- **Image Pull Issues:** There were issues with pulling MongoDB images in Minikube due to rate limiting by Docker Hub.i got the same issue with movie-app image too
---

## Screenshots

*Minikube Dashboard.*
![Minikube Dashboard](https://i.imgur.com/WJ6SwlB_d.webp?maxwidth=720&fidelity=grand?raw=true)

*[Minikube Service ].*
![Start](https://i.imgur.com/WM9qRXV_d.webp?maxwidth=720&fidelity=grand?raw=true)

All rights reserved.
by Kevin JC
---
You can create a Pull Request to this repository to contribute or report issues.
