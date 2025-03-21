🚀 Spring Boot Tracking API

📌 Overview

This is a Scalable Tracking Number Generator API built with Spring Boot, using an H2 in-memory database for local development. It generates unique tracking numbers and provides a RESTful API for managing them.

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
│── Dockerfile
│── Procfile (For Heroku)
│── pom.xml
│── README.md

⚡ Getting Started

🔹 1️⃣ Prerequisites

Java 17

Maven

Docker (Optional for Containerization)

AWS CLI (For Deployment)

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

🐳 Docker Deployment

🔹 Build & Run with Docker

# Build the Docker Image
docker build -t tracking-api .

# Run the Container
docker run -p 8080:8080 tracking-api

📌 Now, access http://localhost:8080

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

http://HOST-NAME.us-east-1.elasticbeanstalk.com/
Will enable the URL as and when needed)
🧪 Running Tests

mvn test

📌 Uses JUnit 5 & Mockito for unit testing.
