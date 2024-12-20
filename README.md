# Car Management System Spring Boot 3.0  
This project demonstrates the implementation of Car Management System using Spring Boot 3.0. It includes the following features:

##  CRUD Operations for Car Management
* POST endpoint to seed data
* http://localhost:8089/api/v1/car/upload-data
* Required fields: form-data{file: cardata.csv}
* POST endpoint to add new cars
* http://localhost:8089/api/v1/car/add-car
* Required fields:
* {
    "model": "g12hj",
    "name": "ritz",
    "color": "black ",
    "trasmission": "Automatic",
    "year": "1900",
    "price": 12.75,
    "fuelType": "petrol"
 }
* GET endpoint to retrieve all cars
* http://localhost:8089/api/v1/car/get-cars/2014?page=0&size=5&sort=cid,asc&name=swift&model=V60
* Filtering capabilities by:
* name, model -> QueryParams and year -> PathVariable
* PUT endpoint to modify existing car details
* http://localhost:8089/api/v1/car/update-car/2
* Required fields:
* {
    "model": "g12hj",
    "name": "ritz",
    "color": "black ",
    "trasmission": "Automatic",
    "year": "1900",
    "price": 12.75,
    "fuelType": "petrol"
 }
* DELETE endpoint to remove car entries
* http://localhost:8089/api/v1/car/delete-car/1
* GET endpoint to search cars
* http://localhost:8089/api/v1/car/search?page=0&size=5&sort=name,desc&year=0&fuelType=PETROL
* Filtering capabilities by:
* name, model, year, color, fuelType  -> QueryParams 

## Technologies
* Spring Boot 3.0
* PostgresSQL
* Hibernate
* Postman
* Maven
 
## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+

-> The application will be available at http://localhost:8089/api/v1/car/.
