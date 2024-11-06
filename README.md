
# Exchanger Service

A Kotlin-based Spring Boot application that provides a REST API for managing accounts and performing currency conversions.

## Features

- **Account Management**: Create and retrieve accounts.
- **Currency Conversion**: Convert between PLN and USD, updating balances accordingly.
- **Validation and Error Handling**: Ensures valid data input.

## Prerequisites

- **JDK 21**
- **Gradle**

## Running the Application

To build and run the application:

```bash
./gradlew build
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

## API Endpoints

### 1. Create an Account

**Endpoint**: `POST /api/accounts`

**Example**:
```bash
curl --location 'http://localhost:8080/api/accounts' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Name",
    "surname": "Surname",
    "initialPlnBalance": 1000
}'
```

### 2. Retrieve Account Details

**Endpoint**: `GET /api/accounts/{accountUuid}`

**Example**:
```bash
curl --location 'http://localhost:8080/api/accounts/ff9f4e2b-beae-4240-ac36-8a63a97b4ca2'
```

### 3. Currency Conversion

**Endpoint**: `POST /api/convert/{accountUuid}`

**Example**:
```bash
curl --location 'http://localhost:8080/api/convert/ff9f4e2b-beae-4240-ac36-8a63a97b4ca2' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCurrency": "USD",
    "targetCurrency": "PLN",
    "amount": 200
}'
```
