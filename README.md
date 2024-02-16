# Billing Project
The project consists of developing an application that allows us to manage the sales of a business. 
To do this we need to focus on 3 actors, the customer who buys the products, which are the elements
that make up the sales of the business.

## Client table UML

 ![image](https://github.com/monick96/billingCoderHouse/assets/98364643/fc770371-78c4-45a4-872c-30b9e0acbce8)

 ## Task 1 Client
Starting from the structure provided by Coder House (jpa, lombok, mysql server, spring web) of a Spring Boot project with JPA,
the Client class that represents the table generated in the previous class must be developed in it.
Methods will be generated to recover all clients, one client by name and delete one client by name. 
Everything must be tested via the main method.

## Task 2 part 1
Create the customer table with the following attributes: first name, last name, date of birth. Additionally, create a restcontroller that returns a json with the following structure:
    { 
    name: XXXXXX,
    last name: YYYYYY,
    years: #####
    }
In the service layer, the age of the person consulted must be calculated and the JSON returned.

## Task 2 part 2

- Use a jpa library to connect to the database.
- Each entity defined in the first delivery must use the 3-layer architecture to be manipulated.
- Modifications are expected to be carried out in cascade.
- Generate data initialization scripts for the tables created in the first delivery


