# Microservices with Gateway and Service Discovery

## Payment Service
A dummy service which stores payment information in a Postgresql DB. This runs at port 9191. You can check payment information for a given order id (say 5) through here: [/payment/5](http://localhost:9191/payment/5)

## Order Service
A dummy service which stores order information in a Postgresql DB. This internally calls the payment service to store related payment information. This runs at port 9192. As our mmicroservices are registered with a service discovery, order-service makes REST call to payment-service via URI http://payment-service/payment/doPayment instead of URI http://localhost:9191/payment/doPayment

## Gateway
Reactive (netty) gateway to handle incoming requests to both of these services. This runs at port 8080. As you can see, both microservices can be accessed through the same 8080 port via gateway.

- POST Book an Order with below sample request: [http://localhost:8080/order/bookOrder](http://localhost:8080/order/bookOrder)
```
{
    "order": {
        "id": 100,
        "name": "Book 3",
        "qty": 5,
        "price": 34.3
    },
    "payment": {}
}
```
- GET Find Payment Information by Order Id#100: [http://localhost:8080/payment/100](http://localhost:8080/payment/100)

## Eureka Service Discovery
Service discovery to which all the above components have been registered. It runs at port 8761. You can check the registered services here: http://localhost:8761

## Postgresql DB - Steps to Install via Docker
Refer to this project's wiki on how to install and run postgreSQL via docker.




