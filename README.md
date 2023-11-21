## Contract Test using PACT 

### Overview
This repository contains an example implementation of contract testing using PACT. Contract testing is a method to ensure that services (APIs, microservices, etc.) can communicate with each other as expected. PACT is a popular contract testing tool that helps in creating and managing these contracts.

This repository demonstrates how to use Pact for consumer-driven contract testing between Payment and Order services.

## Use Case: Payment and Order Service with Pact Consumer-Driven Contract Testing

This repository demonstrates how to use Pact for consumer-driven contract testing between Payment and Order services.

### Services

- **Payment Service:** Handles payment processing for orders.
- **Order Service:** Manages customer orders.

## Consumer-Driven Contract Testing with Pact

### Prerequisites

- Java Development Kit (JDK) installed
- Gradle build tool
- IDE of your choice

### Getting Started

To run consumer-driven contract tests with Pact:

1. **Start by Cloning the Repository**

   ```bash
   git clone https://github.com/ISanjeevKumar/contract-test-pact.git
   ```

2. **Building Services

Build the Payment and Order services using Gradle.
   
```bash
cd payment-service
./gradlew build
cd ../order-service
./gradlew build

```
