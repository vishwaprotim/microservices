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

1. Pull the latest postgres image from docker hub:
```
docker pull postgres
```
3. You can verify the images by:
```
docker images
```
4. Now we will create a container volume for postgres to store its data:
```
docker volume create postgres-data
```
5. Verify that the volume has been created using
```
docker volume ls
```
6. Now we create the docker container using the above image and volume:
```
docker run --name postgres-container -e POSTGRES_PASSWORD=password -p 5432:5432 -v postgres-data:/var/lib/postgresql/data -d postgres
```
- --name = name of the container
- -e = environment
- -p = port, default is 5432
- -v = volume name, along with mount point
- -d ‎ =  run it as a daemon or a background process
7. You can check the container is running by:
```
docker ps
```
8. Connect to your postgres server
```
docker exec -it postgres-container psql -U postgres
```
- exec is used to execute a command inside a container
- Here we are executing psql terminal inside the postgres-container using arguments -U postgres
- As we all know, -U is the username flag, which is the default super user postgres

10. To stop the container
```
docker stop postgres-container
```
11. Remove docker container
```
docker rm postgres-container
```
13. Remove docker image
```
docker rmi postgres
```
14. Remove docker volume
```
docker volume rm postgres-data
```




