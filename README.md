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
Manual Setup:
- Clone the repository.

- Add the following environment variable:
  - `DATABASE_URL`: `jdbc:postgresql://localhost:5432/<DATABASE_NAME>?user=<DATABASE_USER>&password=<DATABASE_PASSWORD>`

- To build project run `mvn clean package` command.
- To run project run `mvn exec:java` or `java -jar target/seasonal-product-discounter-1.0-SNAPSHOT.jar`

Docker Setup:
- Clone the repository.

- Add the following environment variable:
  - `DATABASE_USER`
  - `DATABASE_PASSWORD`
 
- To run project in docker run `docker-compose up` command
