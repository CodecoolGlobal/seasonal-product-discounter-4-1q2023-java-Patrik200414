# seasonal-product-discounter

This project aims to simulate a clothing shop environment where various sales and discounts are offered based on product color and season. 
It involves generating random users and items and simulating transactions for a day.
Data is stored using PostgreSQL.

# Technologies Used
- Java
- JDBC
- PostgreSQL
- Junit

# Overview
The project is implemented in plain Java, serving as a simulation exercise for a specific retail scenario. 
It focuses on practicing Object-Oriented Programming (OOP) and SOLID principles.
Additionally, it provides learning opportunities in SQL integration with Java.

# Challenges
The primary challenge encountered was creating the necessary components for the simulation to function effectively. 
Additionally, integrating the database with Java posed a significant hurdle.

# Setup Instructions
Manual setup:
- Clone the repo
- Run the program from your IDE (You don't need to do any configuration)

Maven setup:
- Clone the repo
- Add `DATABASE_URL` environment variable
  - export 'DATABASE_URL=jdbc:postgresql://localhost:5432/seasonal_product?user=<database user>&password=<database password>'
- Build project
  - mvn clean install
- Run project
  - mvn exec:java
