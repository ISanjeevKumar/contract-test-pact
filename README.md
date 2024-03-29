## Pact: Consumer-Driven Contract Testing in Kotlin

### Overview

This repository contains an example implementation of contract testing using PACT. Contract testing is a method to
ensure that services (APIs, microservices, etc.) can communicate with each other as expected. PACT is a popular contract
testing tool that helps in creating and managing these contracts.

This repository demonstrates how to use Pact for consumer-driven contract testing between Payment and Order services.

### Prerequisites

- Java Development Kit (JDK) installed
- Gradle build tool
- IDE of your choice

### Payment Service

#### Overview

The Payment Service is a simple Spring Boot application developed in Kotlin that simulates a payment processing system. It includes REST API endpoints to handle payment requests and responses between a consumer (client) and the payment service provider.

#### Use Case

The Payment Service acts as a provider in a payment processing scenario:

- **Consumer (Client Perspective):** The consumer, sends payment details to the Payment Service for processing a transaction.
- **Provider (Payment Service Perspective):** The Payment Service receives payment requests from consumers, processes them, and returns a response indicating the status of the payment transaction.

####  Setup Payment Service on local machine

1. Clone this repository:
```bash
git clone https://github.com/ISanjeevKumar/contract-test-pact.git
````
2. Navigate to the project directory:
```bash
cd path/payment-service
````
3. Build the project using Gradle:
```bash
cd  ./gradlew build
````
4. Run the application:
```bash
cd  ./gradlew bootRun
````
#### Payment API Endpoints
* POST /process-payment: Endpoint for processing payment requests. Send a JSON payload with payment details to simulate a payment transaction.

Request Body:
```json
{
  "transactionId": "1234567890",
  "amount": 50.00,
  "currency": "INR",
  "paymentMethod": "credit_card",
  "cardNumber": "1234 5678 9101 1121",
  "expiryDate": "12/25",
  "cvv": "123"
}
```
Response for a Successful Payment:
```json
{
   "transactionId": "1234567890",
   "status": "success",
   "message": "Payment successful."
}
```
Response for a Failed Payment:
```json
{
  "transactionId": "1234567890",
  "status": "failed",
  "message": "Payment declined."
}
```
### Pact Tests
The repository includes Pact sample tests:
* `OrderServicePaymentConsumerTest` for the Order Service (Consumer)
* `PaymentServiceProviderTest` for the Payment Service (Provider)

#### About Consumer Pact Test
* The `OrderServicePaymentConsumerTest` contains Pact tests for the Order Service, validating its interactions with the Payment Service. These tests define expected requests and responses, ensuring contract compliance.

#### About Provider Pact Test
The `PaymentServiceProviderTest` contains Pact tests for the Payment Service, validating its compliance with contracts specified by the Order Service. These tests confirm that the Payment Service behaves as expected.

### How to Run Pact Test in Local Machine
1. Consumer Test Execution
* Ensure the Pact Broker is running. use the below docker command:
 ```bash
docker-compose -f localpath-to-repo/pact-broker-docker-compose.yml up
```
* Run the `OrderServicePaymentConsumerTest` and generate a pact file.
* Publish the pact aka contract to PACT broker using below command
```bash
 ./gradlew pactPublish
```

2. Provider Test Execution
* Ensure the Pact Broker is running and contract is published.
* Run the `PaymentServiceProviderTest` to validate the Payment Service contracts set by the Order Service.

