# Delivery fee calculator
## Requirements

For building and running the application you need:

- [JDK 18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)
- [Intellij](https://www.jetbrains.com/idea/)


## Running the application locally
Before running the application, make sure check and change application properties regarding H2 database.
You can change application properties in `src.main.resources.application.properties`

```
spring.datasource.url=jdbc:h2:file:/data/demo;AUTO_SERVER=TRUE
spring.datasource.username=sa
spring.datasource.password=password
```

You can also change configuration for Cron job (which imports [weather conditions](https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php)) by adding your own cron expression:

```
cron.expression=0 15 * ? * *
```


## Usage
A delivery fee is calculated according to input parameters from REST interface requests,
weather data from the database, and business rules. The total delivery fee consists of a regional
base fee for a specific vehicle types and extra fees for some weather conditions.

REST interface (endpoint), which enables other parts of the application to request delivery fees
according to the following input parameters:
- City: Tallinn / Tartu / Pärnu
- Vehicle type: Car / Scooter / Bike

example for Json request:
```
{
    "city":"Tallinn",
    "vehicleType":"car"
}
```

In response to the request, the total delivery fee or an error message should be given.

You can add additional datetime parameter to the REST interface request. This parameter
is not mandatory, but if it’s valued, delivery fee calculations are done based on
business rules and weather conditions, which were valid at the specific time.

example for Json request:
```
{
    "city":"PäRNu",
    "vehicleType":"SCOOTER",
    "dateTime":"2022-08-08 16:16:07"
}
```


When the project is running REST interface documentation is available on:
http://localhost:8080/swagger-ui.html

## Bonus
CRUD functionaly for business rules, such as "base fees" and "extra fees" are also implemented.


## Tests
Tests for "Delivery fee calculator" are also implemented and can be found in `src.test.java.delivery.fee.calculation.api_tests`.
You should only run them when application is running.



