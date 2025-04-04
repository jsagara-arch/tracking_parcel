🚀 Spring Boot Tracking API

📌 Overview

This is a Scalable Tracking Number Generator API built with Spring Boot, using an H2 in-memory database for local development. It generates unique tracking numbers and provides a RESTful API for managing them added basic validations , Exceptions.

✅ Features

📌 Spring Boot 3 & Java 17

📌 REST API with Spring Web

📌 Unique Tracking Number Generation

📌 H2 In-Memory Database for Local Development

📌 Docker Support

📌 AWS Elastic Beanstalk Deployment

📌 JUnit 5 Tests with Mockito

📂 Project Structure

tracking-api/
│── src/main/java/com/example/tracking
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   ├── service/
│── src/main/resources
│   ├── application.yml
│── pom.xml
│── README.md

⚡ Getting Started

🔹 1️⃣ Prerequisites

Java 17

Maven

AWS EBS (For Deployment)

🔹 2️⃣ Running Locally

# Clone the Repository
git clone https://github.com/your-repo/tracking-api.git
cd tracking-api

# Build the Application
mvn clean package

# Run the Application
mvn spring-boot:run

📌 The API will be available at: http://localhost:8080

🔥 API Endpoints

🔹 Generate Tracking Number

POST /api/next-tracking-number

{
"originCountryId": "US",
"destinationCountryId": "CA",
"weight": 1.5,
"customerId": "de619854-b59b-425e-9db4-943979e1bd49",
"customerName": "RedBox Logistics",
"customerSlug": "redbox-logistics"
}

📌 Response:

{
"trackingNumber": "USCA1234567890",
"createdAt": "2025-03-19T12:45:00Z"
}


☁️ AWS Deployment (Elastic Beanstalk)

🔹 Deploy to AWS Elastic Beanstalk (Free for 12 Months)

1️⃣ Install Elastic Beanstalk CLI

pip install awsebcli --upgrade

2️⃣ Initialize the Application

eb init -p java-17 tracking-api

3️⃣ Deploy the JAR File

eb create tracking-api-env
eb deploy

📌 Access the API:

http://track-parcel-app-env.eba-cmxkxpej.ap-southeast-1.elasticbeanstalk.com/swagger-ui/index.html
(Currently I have paused application run, Will enable the URL while testing)
🧪 Running Tests

mvn test

📌 Uses JUnit 5 & Mockito for unit testing.

NOTE:-
To avoid exposing credentials in application.yml, we can use the following methods:

1️⃣ Environment Variables – Store database credentials securely in OS-level environment variables, preventing direct exposure in the code.
2️⃣ AWS Secrets Manager – For cloud deployments, store and fetch secrets dynamically using AWS services, reducing hardcoded credentials.
