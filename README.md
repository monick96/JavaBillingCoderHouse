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

## Task Final
### Specific objectives
- Understanding of business changes
- Add validations
- Consumption of external services
- Validation of the system in the face of new requirements

#### Add the following specifications:
- Modify the POST service of the Receipt so that the request has a body with the following structure:
{
"customer": {
"clientid":1 },
"lines" : [
{
"Quantity 1,
"product" : {
"productid":1 } } 
]}

- The class that represents the service of the Voucher entity must have the following validations:
  - Existing customer
  - Existing products
  - Quantity of products requested is less than or equal to the stock of the product
  - Reduce stock by the number of units sold

- In the event of a change in the price of a product, the receipts already generated that contain this product should not be modified.

- The response from the receipt creation service must comply with the following guidelines
  - The date of the receipt must be obtained from the REST service http://worldclockapi.com/api/json/utc/now
    - In case the service fails, calculate the date using the Java Date class
  - Calculate the total sale price
  - Calculate the number of products sold
  - If any validation(s) is not met, it must be reported in the response
      - 200: when the operation can be performed, whether query or update. 
        For example: the client exists, the client is returned and the status code 200
      - 409: when the operation cannot be performed, whether query or update.
  

### The final UML of this apllication billing:
![billing.png](..%2Fbilling.png)


