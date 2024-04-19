<div align="center" name="readme-top">
  <h3 align="center">Seasonal Product Discounter</h3>
</div>


<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#run-project">Run Project</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



## About The Project

This project aims to simulate a clothing shop environment where various sales and discounts are offered based on product color and season. 
It involves generating random users and items and simulating transactions for a day.
Data is stored using PostgreSQL.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With


* [![Java][Java]][Java-url]
* [![PostgreSQL][PostgreSQL]][PostgreSQL-url]
* [![Docker][Docker]][Docker-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



## Getting Started

Clone the repository to your machine: https://github.com/CodecoolGlobal/seasonal-product-discounter-4-1q2023-java-Patrik200414

I assume you have Maven and JRE with PostgreSQL or Docker downloaded to your machine.
If not here are some links where you can download from :
- Maven: https://maven.apache.org/download.cgi
- JRE: https://www.java.com/en/download/manual.jsp
- PostgreSQL: https://www.postgresql.org/download/
- Docker: https://www.docker.com/products/docker-desktop/

### Prerequisites

For manual setup add the following environment variable:
  - `DATABASE_URL`: jdbc:postgresql://localhost:5432/[DATABASE_NAME]?user=[DATABASE_USER]&password=[DATABASE_PASSWORD]

For Docker setup add the following environment variables:
  - `DATABASE_USER`: [Your database username]
  - `DATABASE_PASSWORD`: [Your database password]


### Run Project

- Manual setup:
  1. To build project run `mvn clean package` command.
  2. To run project execute `mvn exec:java` or `java -jar target/seasonal-product-discounter-1.0-SNAPSHOT.jar`

- Docker setup:
  1. Run `docker-compose up` command

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Contact
[![LinkedIn][linkedin-shield]][linkedin-url]
[![Email][email-shield]][email-address]

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/patrik-martin2004/
[email-shield]: https://img.shields.io/badge/Email-address?style=for-the-badge&logo=gmail&color=555
[email-address]: mailto:martinbpatrik@gmail.com
[Java]: https://img.shields.io/badge/Java-21?logo=openjdk&color=%23FF0000
[Java-url]: https://www.java.com/en/
[PostgreSQL]: https://img.shields.io/badge/Postgres-SQL?style=flat&logo=postgresql&logoColor=%23FFFFFF&color=%23008bb9
[PostgreSQL-url]: https://www.postgresql.org/docs/
[Docker]: https://img.shields.io/badge/Docker-a?style=flat&logo=docker&logoColor=%23FFFFFF&color=%23384d54
[Docker-url]: https://docs.docker.com/