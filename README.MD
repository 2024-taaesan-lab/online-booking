# Online Booking Application

## Overview

For this project, there are three types of instructions: Docker, Maven, and Kubernetes.
The simplest method to run this project requires only the Docker engine, as I have already built and packaged the software on Docker Hub.

For Maven, it is necessary to execute Maven commands.

Setting up Kubernetes is the most complex process, but it provides the ability to manage multiple containers to handle heavier workloads.

## Key Features
- API-First Approach: The development process prioritizes API design, fostering collaboration between designers and developers while streamlining workflow.
- Parallel Development: The API-first methodology enables simultaneous work by designers and developers, enhancing efficiency and reducing time-to-market.
- Robust Documentation: OpenAPI documentation is provided for clear understanding and seamless integration of the application.

![Online Booking Application](./resources/K7.gif)

## Folder Structure
- **client**: This directory contains client scripts for the Rest Client plugin in Visual Studio Code. These scripts may be used for testing RESTful APIs or making HTTP requests.

- **k6**: This directory contains testing scripts for load testing using the k6 library. k6 is a modern load testing tool that allows you to write and execute performance tests in JavaScript.

- **k8s**: This directory contains Kubernetes configuration files. These files define the deployment, service, and other resources required for deploying and managing the online booking application in a Kubernetes cluster.

- **resources**: This directory contains miscellaneous resources related to the project. It may include documentation, design assets, or any other project-related materials.

- **table-booking-service**: This directory contains the backend service code for the online booking application. The backend service is written in Java with the Spring Boot framework. It includes server-side code, configuration files, tests, and other related resources specific to the table booking service.

## Docker Instructions

### Required Software
- Docker: [Download Docker](https://docs.docker.com/get-docker/)

### Usage
1. Run the following Docker command to deploy the application:
```Shell
docker run -p 8080:8080 taaesan/table-booking-service:latest
```
2. Once the application is running, you can access the OpenAPI documentation at:  
http://localhost:8080/swagger-ui/index.html  

## Maven Instructions  

### Required Software
Java 17:[ Download JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
Maven 3.9+: [Download Maven](https://maven.apache.org/download.cgi)

### Usage
1. Navigate to the 'table-booking-service' directory:
```Shell
cd table-booking-service
```  

2. Install the project dependencies and execute unit tests using the following command:  
```Shell
mvn clean install
```  

3. To run the application:  
```Shell
mvn spring-boot:run
```

4. Once the application is running, you can access the OpenAPI documentation at:  
http://localhost:8080/swagger-ui/index.html  


---
### APIs Contract

| Operation  | Resource | HTTP Verb | Success Code | Error Code |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Initialize Tables | /initializeTables | POST | 200  | 409 - Tables already initialized | 
| Reserve Tables | /reserveTable | POST | 200  | 400 - Not enough tables for all customers <br>  503 - Not enough tables for all customers | 
| Cancel Reservation | /cancelReservation | POST | 200  | 404 - Booking ID not found | 


## Kubernetes Instructions  

![Kubernetest](./resources/X10.jpg)

### Required Software
- Minikube https://minikube.sigs.k8s.io/docs/start/
- Kubctl https://kubernetes.io/docs/tasks/tools/
- Helm https://github.com/helm/helm/releases
- K6 https://k6.io/docs/get-started/installation/
- Ingress https://kubernetes.github.io/ingress-nginx/deploy/#minikube

### Usage

1. Ensure successful execution of the following commands:
```Shell
minikube start  
minikube addons enable ingress  
minikube addons list 
|ingress| minikube | enabled ✅| Kubernetes|
```
2. Execute the following commands to set up the Postgres cluster:

```Shell
kubectl apply -f k8s/postgres-configmap.yaml
kubectl apply -f k8s/postgres-volumn.yaml
kubectl apply -f k8s/postgres-deployment.yaml
```

3. Deploy the application by executing the following commands:
```Shell
kubectl apply -f k8s/table-booking-service.yaml
kubectl apply -f k8s/ingress.yaml
```

## Asynchronous Call Performance Optimization

There's significant potential for improving speed within Java Spring by customizing task handling, which allows certain tasks to be executed in the background.

Let's compare the duration for the /initializeTables endpoint:

Before: it took 1232 milliseconds.
```Shell
2024-04-09T23:20:45.377Z  INFO 1 --- [online-booking] [nio-8080-exec-1] c.x.o.c.InitializeTablesController       : The current Thread is : http-nio-8080-exec-1
2024-04-09T23:20:45.378Z  INFO 1 --- [online-booking] [nio-8080-exec-1] c.x.o.c.InitializeTablesController       : Elapsed time: 1232

```

After: it took only 5 milliseconds.
```Shell
2024-04-09T23:38:00.859Z  INFO 1 --- [online-booking] [nio-8080-exec-1] c.x.o.c.InitializeTablesController       : The current Thread is : http-nio-8080-exec-1
2024-04-09T23:38:00.859Z  INFO 1 --- [online-booking] [nio-8080-exec-1] c.x.o.c.InitializeTablesController       : Elapsed time: 5
```

Here's the difference:

That's a staggering **99.6% improvement!** This significant boost in performance was achieved by optimizing the system to utilize asynchronous calls, resulting in a remarkably faster initialization process for the database tables.


## Miscellaneous

### Prostgres on K8
https://www.digitalocean.com/community/tutorials/how-to-deploy-postgres-to-kubernetes-cluster


```Shell
k exec -it postgres-588b6d4dd8-cx28p -- psql -h localhost -U ps_user --password -p 5432 ps_db

\c <db-name> - Connect database
\l - List database
\d <table-name> - Describe table
\d+ <table-name> - More details
```

Install ingress controller (Minikube)
https://kubernetes.github.io/ingress-nginx/deploy/#minikube

```Shell
docker rmi -f $(docker images 'taaesan/table-booking-service-k8' -a -q)

mvn package -Dmaven.test.skip=true 
docker buildx build -t taaesan/table-booking-service-k8:14 .
docker push taaesan/table-booking-service-k8:0.0.14

k roll out restart deployment 

docker tag taaesan/table-booking-service-k8:0.0.4 taaesan/table-booking-service-k8:latest

docker push taaesan/table-booking-service-k8:0.0.5
docker push taaesan/table-booking-service-k8:latest
```

### Load Test
`k6 run k6/script.k8s.js`  



docker buildx build -t taaesan/postgres-table-booking:latest db/

docker run --name pg-test -p 5432:5432 -e POSTGRES_USER=ps_user -e POSTGRES_PASSWORD=p@ssw0rd -e POSTGRES_DB=ps_db taaesan/postgres-table-booking:latest


docker exec -it pg-test psql -U ps_user -d ps_db

k exec -it postgres-588b6d4dd8-8mz8h -- psql -h localhost -U ps_user --password -p 5432 ps_db




