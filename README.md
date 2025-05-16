# 🏦 Banking Application

A comprehensive banking application for the National Bank of Rwanda's customer account management system. This application provides a secure and efficient way to manage customer accounts, process transactions, and handle notifications.

## 🛠️ Technologies Used

- ![Java](https://img.shields.io/badge/Java-21-orange) Core programming language
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen) Application framework
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-latest-green) Authentication and authorization
- ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-latest-yellowgreen) Database access and ORM
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue) Relational database
- ![JWT](https://img.shields.io/badge/JWT-latest-purple) Secure authentication
- ![Spring Mail](https://img.shields.io/badge/Spring%20Mail-latest-lightblue) Email notifications
- ![Maven](https://img.shields.io/badge/Maven-latest-red) Build and dependency management

## ✨ Features

- 👥 **Customer Management**
  - Register new customers
  - View customer details
  - Update customer information
  - Delete customer accounts

- 💰 **Account Management**
  - Generate unique account numbers
  - Track account balances
  - View transaction history

- 💸 **Transaction Processing**
  - Deposit funds (savings)
  - Withdraw funds
  - Transfer money between accounts
  - Receive transfers

- 🔒 **Security**
  - JWT-based authentication
  - Role-based access control
  - Secure password handling

- 📬 **Notifications**
  - Transaction notifications
  - Account status updates
  - Retry mechanism for failed notifications

## 🔌 API Endpoints

### 🔐 Authentication
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login with credentials

### 👤 Customer Management
- `POST /customers/register` - Create a new customer
- `GET /customers` - Get all customers
- `GET /customers/{id}` - Get a customer by ID
- `DELETE /customers/{id}` - Delete a customer by ID
- `PATCH /customers/{id}` - Update a customer by ID

### 💱 Transaction Processing
- `POST /transactions/save` - Deposit money into an account
- `POST /transactions/withdraw` - Withdraw money from an account
- `POST /transactions/transfer` - Transfer money between accounts

### 📨 Notifications
- `GET /messages/customer/{customerId}` - Get messages for a specific customer
- `GET /messages/{messageId}` - Get a specific message by ID
- `POST /messages/{messageId}/retry` - Retry sending a failed message

## 🚀 Setup Instructions

### 📋 Prerequisites
- ☕ Java 21 or higher
- 🐘 PostgreSQL database
- 🔧 Maven

### ⚙️ Installation
1. 📥 Clone the repository
2. 🔄 Configure the database connection in `src/main/resources/application.yaml`
3. 🏗️ Build the project: `mvn clean install`
4. ▶️ Run the application: `mvn spring-boot:run`

### 🔧 Configuration
The application can be configured through the `application.yaml` file:
- 🗄️ Database connection settings
- 🔑 JWT secret and expiration
- 📧 Email server configuration
- 📝 Logging settings

## 📂 Project Structure

```
src/main/java/com/bruce/banking/
├── BankingApplication.java          # Main application class
├── config/                          # Configuration classes
│   ├── JwtAuthFilter.java           # JWT authentication filter
│   └── SecurityConfig.java          # Security configuration
├── controllers/                     # REST API controllers
│   ├── AuthController.java          # Authentication endpoints
│   ├── CustomerController.java      # Customer management endpoints
│   ├── MessageController.java       # Notification endpoints
│   └── TransactionsController.java  # Transaction endpoints
├── dtos/                           # Data Transfer Objects
├── exceptions/                     # Custom exceptions
├── models/                         # Entity models
│   ├── Customer.java               # Customer entity
│   ├── Message.java                # Notification message entity
│   ├── Transaction.java            # Transaction entity
│   └── TransactionType.java        # Transaction type enum
├── repositories/                   # Data access layer
├── services/                       # Business logic layer
└── utils/                          # Utility classes
```

## 📜 License

This project is proprietary software owned by the National Bank of Rwanda.
