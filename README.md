
# E Commerce Project 

This project is related with developing REST-API for e-commerce application. In this module, I have developed all the operation related to e-commerce. 
For securing the application, I have used JWT authentication.


# ER Diagram

![App Screenshot](https://i.postimg.cc/zX9Q0tGv/ER-Diagram.png)


# Technology Used 
### Java
### Spring
### Springboot
### Hibernate
### JPA
### MySql
### Spring Security(JWT)
### Swagger
# Project Modules
### User Module
### Product Module
### Category Module
### Cart Module
### Order Module
# Contributing

## 🔗  [@shashyabh](https://www.github.com/shashyabh)


# Installation

### Configuration file for project

```bash
  server.port=5000
  spring.datasource.url=jdbc:mysql://localhost:3306/electronic_store?serverTimezone=UTC
  spring.datasource.username=root
  spring.datasource.password=root
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.format_sql=true
  admin.role.id=6789sokjhssyu89
  normal.role.id=iuhsy789sokjhsj
```
# API Reference

## User Module
```
  POST /users/create : To create new Users
```  
```
  Put /users/update/userId : To update existing user
``` 
```
  Get /users/getUsers : To get all users
``` 
```
  Get /users/userId :To get single user
``` 
```
  Get /users/email :To get user by email
``` 
```
  Get /users/search :To search with any keyword
``` 
```
  Delete /users/delete/userId : To delete an user
``` 
```
  POST /users/image : To upload an image
``` 
```
  Get /users/image/userId : To serve an image
``` 


## Category Module
```
  POST /Categories/create : To create a new Category (Only access by admin)
```
```
  POST /CategoryId/product : To create product with CategoryId
```  
```
  Put /Categories/CategoryId/product/productId :To update any product in Category
``` 
```
  Get /Categories/CategoryId : Get all products by CategoryId
``` 
```
  Delete /Categories/CategoryId : Delete a Category
``` 
```
  Get /Categories/getAll : Get all Categories
``` 

## Product Module

```
  POST /products/create : To create new product
```  
```
  Put /product/update/productId : To update existing produc
``` 
```
  Get /products/getAll : To get all products
``` 
```
  Get /products/getSingle/productId :To get single Product
``` 
```
  Get /products/search/query :To search with any keyword
``` 
```
  Delete /products/delete/userId : To delete an product
``` 
## Cart Module

```
  POST /carts/addItem/userId : To add item into cart by userId
```  
```
  Delete /carts/userId/items/itemId : To remove an item from cart
``` 

```
  Delete /carts/clear/userId : To clear cart of an user
``` 
```
  Get /carts/getAll/userId : To get all cart details of user
``` 

## Order Module

```
  POST /orders/create : To place an order from cart
```  
```
  Delete /orders/delete/orderId/ : To remove an order
``` 
```
  Get /orders/users/userId : To get all order details of user
``` 

## Login Module
```
  POST /auth/login : To login with email and password & generation of JWT
```  

## Request and Response from Json

### Login request from json

```bash
  {
    "email":"abc@gmail.com",
    "password":"abc"
    }
```

### Response

```bash
  {
    "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJfygvghvvvjQGdtYWlsLmNvbSIsImV4cCI6MTY4ODY2NzU4fhhvhghchfNjg4NjQ5NTg2fQ.cg5uy1C8gFKzcf_jLJzo4eaxC2ycJN6McLlle7qWPv91fGFXXTMk-iH0yL_Ra4AINwJkDlQwwKZ0kfZQNs1BPg",
    "user": {
        "userId": "db657374-c11f-4a86-8572-ad12c333a3aa",
        "name": "abc",
        "email": "abc@gmail.com",
        "password": "$263bbdiid03bkkdUz9VajNilO1fwEwds9XxlFluim8wrT5Aq52ai/Ksi",
        "gender": "male",
        "about": "I am Java Developer",
        "imageName": "abc.jpg",
        "roles": [
            {
                "roleId": "hejnei394n4ii49ennj4",
                "roleName": "ROLE_ADMIN"
            }
        ]
    }
}
```

## Feedback

If you have any feedback, please reach out to me at shashyabhray06061997@gmail.com

