Wallet API - Java Spring Boot Assessment

This is a Spring Boot API that simulates a digital wallet system with basic operations such as:

- Creating wallets
- Linking bank accounts
- Funding wallets via payment gateways (Flutterwave, Paystack — simulated)
- Retrieving wallet transactions
- Logic task: palindrome number check (find code in the util folder)

This project uses **H2 in-memory database**, so no external setup is required.

How to Run

1. Clone the repository:
2. Run the project
```bash
# From project root
./gradlew bootRun

 Prerequisites
- Java 17+
- Gradle (or use included `./gradlew` script)
- Postman or CURL for testing

Import the included Postman collection:
📄 postman_collection.json

 API Endpoints
1. Create Wallet
POST /api/wallet/create

2. Get Wallet by Email
GET /api/wallet/by-email?email={email}

3. Link Bank Account
POST /api/wallet/{walletId}/link-bank

4. Fund Wallet
POST /api/wallet/{walletId}/fund
Supports: FLUTTERWAVE, PAYSTACK

5. Get Linked Bank Accounts
GET /api/wallet/{walletId}/banks

6. Get Wallet Transactions
GET /api/wallet/{walletId}/transactions