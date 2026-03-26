<div align="center">
  <h1>🏦 Console Banking System</h1>
  <p>A robust console-based Banking System built in Java and connected to a MySQL database using JDBC.</p>
</div>

---

## ✨ Features

- **🔐 User Authentication**: Secure Sign-up and Login functions for user registration and access.
- **💼 Account Management**: Functionality for an authenticated user to open a new Bank Account. Each new account generates a unique sequential account number. 
- **💰 Core Banking Operations**:
  - **💳 Credit Money**: Add deposits to the account securely using a custom security pin.
  - **💸 Debit Money**: Withdraw money securely ensuring adequate account balance to avoid overdrafts.
  - **🔄 Transfer Money**: Seamlessly transfer funds between two registered bank accounts, managed via robust database transactions (commit/rollback) to maintain data consistency.
  - **📊 Check Balance**: Securely query your current account balance.
- **🛡️ Transaction Safety**: All sensitive banking operations are protected with an additional layer of security by prompting for a custom security pin. JDBC transactions employ manual commits and rollbacks to avoid partial updates on database failures.

## 🛠️ Technologies Used

- **Language**: Java (`jdk-11` or higher recommended for stability)
- **Database**: MySQL Server
- **Database Connectivity**: Java Database Connectivity (JDBC) Driver (`mysql-connector-j.jar`)
- **IDE Environment**: Visual Studio Code

## 🚀 Setting Up and Running the Application

### 📋 Prerequisites
1. Installed **Java Development Kit (JDK)**
2. Installed **MySQL Server**
3. MySQL JDBC Driver (`mysql-connector-j.jar` file), which must be loaded in the `lib` folder and referenced in your classpath for the project.

### 🗄️ Database Schema
Before running the application, make sure the database is correctly initialized on `localhost:3306`:
1. Create a MySQL database named `banking_system`.
2. Configure credentials in the source code (currently `username="your user name "` and `password="your password"`).
3. The schema relies on at least two key tables: 
   - `user` (full_name, email, password)
   - `accounts` (account_number, full_name, email, balance, security_pin)

### 💻 How to Run

1. **Compilation**: Compile the Java code making sure to provide the path to your JDBC driver in your classpath. For example:
   ```bash
   javac -d bin -cp "lib/mysql-connector-j-9.0.0.jar" src/*.java
   ```
2. **Execution**: Run the main application entry point.    
   ```bash
   java -cp "bin;lib/mysql-connector-j-9.0.0.jar" bankingsystem
   ```
3. Follow the on-screen console commands to register an account, log in, create a bank account, and start banking.

## 🏗️ Architecture & Logic

- 📝 **`bankingsystem.java`**: The main entry point, containing the master console loop, and main navigation logic.
- 👤 **`User.java`**: Handles registration and login validations linked directly with the `user` table.
- 💳 **`Accounts.java`**: Manages opening new accounts and handling the incremental generation of account numbers.
- ⚙️ **`AccountManager.java`**: Implements the business logic behind debits, credits, and transfers utilizing database-level transactions to guarantee money isn't lost if an error interrupts a process.

---
<div align="center">
  <i>Developed using Java & MySQL</i>
</div>