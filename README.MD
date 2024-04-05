# Online Booking Application 



----

### Docker - Required Software
- Docker: Download and install the Docker from the official Docker website: https://docs.docker.com/get-docker/

### Docker - Instructions

1. Run docker command below, the image was been push to docker hub.
```Shell
docker run -p 8080:8080 taaesan/table-booking-service:latest
```
2. Once the application is running, you can access the OpenAPI documentation at:  
http://localhost:8080/swagger-ui/index.html  


---
### Maven - Required Software
- Java 17: Download and install the latest Java Development Kit (JDK) 17 from the official Oracle website: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html  
- Maven 3.9+: Download and install Apache Maven version 3.9 or later from the official Maven website: https://maven.apache.org/download.cgi  

### Maven - Instructions
1. Navigate to the directory named table-booking-service.
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


----
### Avaliable APIs

| Operation  | Resource | HTTP Verb | Success Code | Error Code |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Initialize Tables | /initializeTables | POST | 200  | 409 | 
| Reserve Tables | /reserveTable | POST | 200  | 409 | 
| Cancel Reservation | /cancelReservation | POST | 200  | 409 | 


