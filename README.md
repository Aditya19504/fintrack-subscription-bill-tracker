# FinTrack — Subscription & Bill Tracker

A REST API built with Java and Spring Boot to manage subscriptions and recurring bills, track upcoming payments, and calculate monthly expenses.

## Features

- Create, view, update, and delete subscriptions and bills
- Track billing cycles: weekly, monthly, and yearly
- View upcoming payments within a specified number of days
- Calculate total monthly expenses
- Mark payments as paid and update the next due date
- Store application data in PostgreSQL

## Tech Stack

- **Language:** Java
- **Backend:** Spring Boot, Spring Web
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA, Hibernate
- **Build Tool:** Maven
- **API Testing:** Postman
- **Version Control:** Git and GitHub

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/subscriptions` | Create a subscription or bill |
| GET | `/subscriptions` | Get all entries |
| GET | `/subscriptions/{id}` | Get an entry by ID |
| PUT | `/subscriptions/{id}` | Update an entry |
| DELETE | `/subscriptions/{id}` | Delete an entry |
| GET | `/subscriptions/upcoming?days=7` | Get upcoming payments |
| GET | `/subscriptions/monthly-total` | Calculate monthly expenses |
| PUT | `/subscriptions/{id}/pay` | Mark an entry as paid |

## Getting Started

### Prerequisites

- Java 17 or a compatible version required by the project
- PostgreSQL
- Maven, or the included Maven Wrapper

### Setup

1. Clone this repository:

   ```bash
   git clone https://github.com/Aditya19504/fintrack-subscription-bill-tracker.git
   cd fintrack-subscription-bill-tracker
   ```

2. Create a PostgreSQL database named `subscriptiontracker`.

3. Configure `src/main/resources/application.properties` with your database URL, username, and password. Set the `DB_PASSWORD` environment variable before starting the application.

4. Run the application:

   **Windows:**
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

   **macOS/Linux:**
   ```bash
   ./mvnw spring-boot:run
   ```

The API will run at `http://localhost:8080`, unless configured otherwise.

## Project Status

**Current stage:** Basic CRUD and payment-tracking functionality implemented. Additional backend improvements and features may be added as development continues.

## Author

Aditya Yeole

[GitHub Repository](https://github.com/Aditya19504/fintrack-subscription-bill-tracker)