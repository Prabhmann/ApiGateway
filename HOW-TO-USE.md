# How to use guide

This projects provides you containerized api-gateway which handles authentication through JWT and provide dynamic routing supporting all HTTP methods.
Scenario Example:

POST https://gateway.public.com:8080/api/service2/test2
{BODY}
--Request coming to gateway will be redirected to the service mentioned in the path with all params and body if any.
POST https://service2/test2
{BODY}



RUN Steps:

1) unzip the project.
2) go to project folder in terminal.
3) run the command -> sudo docker-compose up
